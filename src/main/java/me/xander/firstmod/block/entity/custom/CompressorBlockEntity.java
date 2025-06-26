package me.xander.firstmod.block.entity.custom;

import me.xander.first_mod;
import me.xander.firstmod.block.custom.CompressorBlock;
import me.xander.firstmod.block.entity.ImplementedInventory;
import me.xander.firstmod.block.entity.ModBlockEntities;
import me.xander.firstmod.recipe.CompressorRecipe;
import me.xander.firstmod.recipe.CompressorRecipeInput;
import me.xander.firstmod.recipe.ModRecipes;
import me.xander.firstmod.screen.custom.CompressorScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
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
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.base.SimpleEnergyStorage;

import java.util.List;
import java.util.Optional;

public class CompressorBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, ImplementedInventory{
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);

    private static final int INSPECT_SLOT = 0;
    private static final int INPUT_SLOT = 1;
    private static final int SECONDARY_INPUT_SLOT = 2;
    private static final int OUTPUT_SLOT = 3;
    private static final int ENERGY_TRANSFER_AMOUNT = 320;
    private static final int ENERGY_CRAFTING_AMOUNT = 5000;

    public final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(1000000, ENERGY_TRANSFER_AMOUNT, ENERGY_TRANSFER_AMOUNT) {
        @Override
        protected void onFinalCommit() {
            markDirty();
            getWorld().updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
    };

    private final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;
    private final int DEFAULT_MAX_PROGRESS = 72;
    private int inspectionProgress = 0;
    private int maxInspectionProgress = 1000;
    private final int DEFAULT_MAX_INSPECTION_PROGRESS = 1000;
    public ItemStack currentInspectedItem = Blocks.LIGHT_GRAY_STAINED_GLASS_PANE.asItem().getDefaultStack();



    public CompressorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.COMPRESSOR_BE, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> CompressorBlockEntity.this.progress;
                    case 1 -> CompressorBlockEntity.this.maxProgress;
                    case 2 -> CompressorBlockEntity.this.inspectionProgress;
                    case 3 -> CompressorBlockEntity.this.maxInspectionProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> CompressorBlockEntity.this.progress = value;
                    case 1 -> CompressorBlockEntity.this.maxProgress = value;
                    case 2 -> CompressorBlockEntity.this.inspectionProgress = value;
                    case 3 -> CompressorBlockEntity.this.maxInspectionProgress = value;
                }
            }

            @Override
            public int size() {
                return 4;
            }
        };
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
        return Text.literal("Compressor");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new CompressorScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }
    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
        nbt.putInt("compressor.progress",progress);
        nbt.putInt("compressor.max_progress",maxProgress);
        nbt.putInt("compressor.inspection_progress",inspectionProgress);
        nbt.putInt("compressor.max_inspection_progress",maxInspectionProgress);
        nbt.putLong("compressor.energy",energyStorage.amount);
        nbt.put("compressor.inspected_item", currentInspectedItem.encode(registryLookup));

    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        Inventories.readNbt(nbt,inventory,registryLookup);
        progress = nbt.getInt("compressor.progress");
        maxProgress = nbt.getInt("compressor.max_progress");
        inspectionProgress = nbt.getInt("compressor.inspection_progress");
        maxInspectionProgress = nbt.getInt("compressor.max_inspection_progress");
        energyStorage.amount = nbt.getLong("compressor.energy");
        currentInspectedItem = ItemStack.fromNbt(registryLookup, nbt.get("compressor.inspected_item")).get();
        super.readNbt(nbt, registryLookup);
    }
    public List<Text> getTooltips() {
            return List.of(Text.literal(inspectionProgress+" / " + maxInspectionProgress + "q"));
    }
    public void tick(World world, BlockPos pos, BlockState state) {
        if(world.isClient()) {
            return;
        }
        if (hasItemInInspectionSlot()) {
            if (hasEnoughEnergyToCraft()) {
                increaseInspectionProgress();
                useEnergyForCrafting();
                markDirty(world, pos, state);
                if (hasInspectionFinished()) {
                    inspectItem();
                    resetInspectionProgress();
                }
            }
        } else {
            resetInspectionProgress();
        }
        if(hasRecipe() && canInsertIntoOutputSlot()) {
            increaseCraftingProgress();
            world.setBlockState(pos, state.with(CompressorBlock.LIT, true));
            useEnergyForCrafting();
            useEnergyForCrafting();
            markDirty(world, pos, state);

            if(hasCraftingFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            world.setBlockState(pos, state.with(CompressorBlock.LIT, false));
            resetProgress();
        }
    }

    private boolean hasItemInInspectionSlot() {
        return !this.inventory.get(INSPECT_SLOT).isEmpty();
    }

    private void increaseInspectionProgress() {
        this.inspectionProgress++;
    }

    private boolean hasInspectionFinished() {
        return this.inspectionProgress >= this.maxInspectionProgress;
    }

    private void inspectItem() {
        currentInspectedItem = this.inventory.get(INSPECT_SLOT).getItem().getDefaultStack();
        first_mod.LOGGER.info(this.currentInspectedItem.getItem().getDefaultStack().getRegistryEntry().getIdAsString());
       this.removeStack(INSPECT_SLOT, 1);
    }

    private void resetProgress() {
        this.progress = 0;
        this.maxProgress = DEFAULT_MAX_PROGRESS;
    }
    private void resetInspectionProgress() {
        this.inspectionProgress = 0;
        this.maxInspectionProgress = DEFAULT_MAX_INSPECTION_PROGRESS;
    }

    private void craftItem() {
        Optional<RecipeEntry<CompressorRecipe>> recipe = getCurrentRecipe();

            this.removeStack(INPUT_SLOT, 1);
            this.removeStack(SECONDARY_INPUT_SLOT, 1);
        this.setStack(OUTPUT_SLOT, new ItemStack(recipe.get().value().output().getItem(), this.getStack(OUTPUT_SLOT).getCount() +
                recipe.get().value().output().getCount()));
    }

    private boolean hasCraftingFinished() {
        return this.progress >= this.maxProgress;
    }

    private void useEnergyForCrafting() {
        try(Transaction transaction = Transaction.openOuter()) {
            this.energyStorage.extract(ENERGY_CRAFTING_AMOUNT, transaction);
            transaction.commit();
        }
    }

    private void increaseCraftingProgress() {
        this.progress++;
    }

    private boolean canInsertIntoOutputSlot() {
        return this.getStack(OUTPUT_SLOT).isEmpty() ||
                this.getStack(OUTPUT_SLOT).getCount() < this.getStack(OUTPUT_SLOT).getMaxCount();
    }

    private boolean hasRecipe() {
        Optional<RecipeEntry<CompressorRecipe>> recipe = getCurrentRecipe();
        if (recipe.isEmpty())
            return false;
        ItemStack output = recipe.get().value().getResult(null);


        return canInsertAmountIntoOutputSlot(output.getCount()) && canInsertItemIntoOutputSlot(output) && hasInspection() && hasEnoughEnergyToCraft();
    }
    private boolean hasEnoughEnergyToCraft() {
        return this.energyStorage.amount >= (long) ENERGY_CRAFTING_AMOUNT;
    }
    private boolean hasInspection() {
        Optional<RecipeEntry<CompressorRecipe>> recipe = getCurrentRecipe();
        ItemStack output = recipe.get().value().getResult(null);
        return currentInspectedItem.getItem() == output.getItem();
    }

    private Optional<RecipeEntry<CompressorRecipe>> getCurrentRecipe() {
        return this.getWorld().getRecipeManager().getFirstMatch(ModRecipes.COMPRESSOR_TYPE, new CompressorRecipeInput(inventory.get(INPUT_SLOT),inventory.get(SECONDARY_INPUT_SLOT)), this.getWorld());
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getItem() == output.getItem();
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        int maxCount = this.getStack(OUTPUT_SLOT).isEmpty() ? 64 : this.getStack(OUTPUT_SLOT).getMaxCount();
        int currentCount = this.getStack(OUTPUT_SLOT).getCount();
        return maxCount >= currentCount + count;
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }
}
