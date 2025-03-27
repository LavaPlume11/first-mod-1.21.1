package me.xander.firstmod.block.entity.custom;

import me.xander.firstmod.block.entity.ImplementedInventory;
import me.xander.firstmod.block.entity.ModBlockEntities;
import me.xander.firstmod.fluid.ModFluids;
import me.xander.firstmod.item.custom.ModItems;
import me.xander.firstmod.recipe.MelterRecipe;
import me.xander.firstmod.recipe.MelterRecipeInput;
import me.xander.firstmod.recipe.ModRecipes;
import me.xander.firstmod.screen.custom.MelterScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MelterBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(5, ItemStack.EMPTY);

    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<FluidVariant>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(FluidVariant fluidVariant) {
            return (FluidConstants.BUCKET / 81) * 4; // 1 Bucket = 81000 Droplets = 1000mb || * 4 ==> 4,000mb = 4 Buckets
        }

        @Override
        protected void onFinalCommit() {
            markDirty();
            getWorld().updateListeners(pos,getCachedState(),getCachedState(),3);
        }
    };
    private static final int HEAT_SLOT = 0;
    private static final int INPUT_SLOT_1 = 1;
    private static final int INPUT_SLOT_2 = 2;
    private static final int INPUT_SLOT_3 = 3;
    private static final int OUTPUT_SLOT = 4;
    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 400;
    private final int DEFAULT_MAX_PROGRESS = 400;
    private int heatLevel = 0;
    public int recipeHeatLevel = 4;
    public MelterBlockEntity( BlockPos pos, BlockState state) {
        super(ModBlockEntities.MELTER_BE, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> MelterBlockEntity.this.progress;
                    case 1 -> MelterBlockEntity.this.maxProgress;
                    case 2 -> MelterBlockEntity.this.heatLevel;
                    case 3 -> MelterBlockEntity.this.recipeHeatLevel;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 : MelterBlockEntity.this.progress = value;
                    case 1 : MelterBlockEntity.this.maxProgress = value;
                    case 2 : MelterBlockEntity.this.heatLevel = value;
                    case 3 : MelterBlockEntity.this.recipeHeatLevel = value;
                }
            }

            @Override
            public int size() {
                return 4;
            }
        };
    }
    public FluidVariant getFluid() {
        return fluidStorage.variant;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return this.pos;
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Melter");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new MelterScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }
    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
        nbt.putInt("melter.progress",progress);
        nbt.putInt("melter.max_progress",maxProgress);
        nbt.putInt("melter.heat_level",heatLevel);
        SingleVariantStorage.writeNbt(this.fluidStorage, FluidVariant.CODEC, nbt, registryLookup);

    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        Inventories.readNbt(nbt,inventory,registryLookup);
        progress = nbt.getInt("melter.progress");
        maxProgress = nbt.getInt("melter.max_progress");
        heatLevel = nbt.getInt("melter.heat_level");
        SingleVariantStorage.readNbt(fluidStorage, FluidVariant.CODEC, FluidVariant::blank, nbt, registryLookup);
        super.readNbt(nbt, registryLookup);
    }
    public void tick(World world, BlockPos pos, BlockState state) {
        if(world.isClient()) {
            return;
        }
        if(hasRecipe() && canInsertIntoOutputSlot()) {
            increaseCraftingProgress();
            markDirty(world, pos, state);

            if(hasCraftingFinished()) {
                craft();
                resetProgress();
            }
        } else {
            resetProgress();
        }
        if (hasBucketInBucketSlot()) {
            putFluidIntoBucket();
        }
    }

    private void putFluidIntoBucket() {
        try(Transaction transaction = Transaction.openOuter()) {
            this.fluidStorage.extract(this.fluidStorage.variant, 1000, transaction);
            transaction.commit();
        }
        inventory.set(OUTPUT_SLOT, new ItemStack(ModItems.MELTED_METAL));
    }

    private boolean hasBucketInBucketSlot() {
        return inventory.get(OUTPUT_SLOT).isOf(Items.BUCKET) && inventory.get(OUTPUT_SLOT).getCount() == 1;
    }

    private void resetProgress() {
        this.progress = 0;
        this.maxProgress = DEFAULT_MAX_PROGRESS;
    }

    private void craft() {
        Optional<RecipeEntry<MelterRecipe>> recipe = getCurrentRecipe();
        this.removeStack(INPUT_SLOT_1,1);
        this.removeStack(INPUT_SLOT_2,1);
        this.removeStack(INPUT_SLOT_3,1);
        this.removeStack(HEAT_SLOT,1);
        this.setStack(OUTPUT_SLOT, new ItemStack(recipe.get().value().output().getItem(), this.getStack(OUTPUT_SLOT).getCount() +
                recipe.get().value().output().getCount()));
        try(Transaction transaction = Transaction.openOuter()) {
            this.fluidStorage.insert(FluidVariant.of(Fluids.LAVA), 1000, transaction);
            transaction.commit();
        }
    }

    private boolean hasCraftingFinished() {
        return this.progress >= this.maxProgress;
    }

    private void increaseCraftingProgress() {
        this.progress++;
    }

    private boolean canInsertIntoOutputSlot() {
        return this.getStack(OUTPUT_SLOT).isEmpty() ||
                this.getStack(OUTPUT_SLOT).getCount() < this.getStack(OUTPUT_SLOT).getMaxCount();
    }

    private boolean hasRecipe() {

        Optional<RecipeEntry<MelterRecipe>> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) {

            return false;
        }
        ItemStack output = recipe.get().value().getResult(null);
        int requiredHeat = recipe.get().value().heat();
         if (inventory.get(HEAT_SLOT).getItem() == Items.FIRE_CHARGE) {
            this.heatLevel = 1;
        } else if (inventory.get(HEAT_SLOT).getItem() == Items.BLAZE_ROD || inventory.get(HEAT_SLOT).getItem() == Items.BLAZE_POWDER) {
            this.heatLevel = 2;
        } else if (inventory.get(HEAT_SLOT).getItem() == Items.LAVA_BUCKET) {
            this.heatLevel = 3;
        } else {
            this.heatLevel = 0;
        }

        recipeHeatLevel = recipe.get().value().heat();
        return canInsertAmountIntoOutputSlot(output.getCount()) && canInsertItemIntoOutputSlot(output) && heatLevel == requiredHeat && fluidStorage.getAmount() != fluidStorage.getCapacity();
    }

    private Optional<RecipeEntry<MelterRecipe>> getCurrentRecipe() {
        return this.getWorld().getRecipeManager().getFirstMatch(ModRecipes.MELTER_TYPE, new MelterRecipeInput(
                inventory.get(INPUT_SLOT_1),inventory.get(INPUT_SLOT_2),inventory.get(INPUT_SLOT_3), heatLevel), this.getWorld());
    }




    private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getItem() == output.getItem();
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        int maxCount = this.getStack(OUTPUT_SLOT).isEmpty() ? 64 : this.getStack(OUTPUT_SLOT).getMaxCount();
        int currentCount = this.getStack(OUTPUT_SLOT).getCount();
        return maxCount >= currentCount + count;
    }



    @Nullable
    @Override
    public  Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }
}
