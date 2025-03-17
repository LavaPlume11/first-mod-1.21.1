package me.xander.firstmod.block.entity.custom;

import me.xander.firstmod.block.custom.CrystallizerBlock;
import me.xander.firstmod.block.entity.ImplementedInventory;
import me.xander.firstmod.block.entity.ModBlockEntities;
import me.xander.firstmod.item.custom.ModItems;
import me.xander.firstmod.recipe.CrystallizerRecipe;
import me.xander.firstmod.recipe.CrystallizerRecipeInput;
import me.xander.firstmod.recipe.ModRecipes;
import me.xander.firstmod.screen.custom.CrystallizerScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
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
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.base.SimpleEnergyStorage;

import java.util.Optional;

public class CrystallizerBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);
    private static final int FLUID_ITEM_SLOT = 0;
    private static final int INPUT_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;
    private static final int ENERGY_ITEM_SLOT = 3;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;
    private final int DEFAULT_MAX_PROGRESS = 72;

    private static final int ENERGY_TRANSFER_AMOUNT = 220;

    private static final int ENERGY_CRAFTING_AMOUNT = 20000;

    public final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(97800, ENERGY_TRANSFER_AMOUNT, ENERGY_TRANSFER_AMOUNT) {
        @Override
        protected void onFinalCommit() {
            markDirty();
            getWorld().updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
    };
    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<FluidVariant>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(FluidVariant fluidVariant) {
            return (FluidConstants.BUCKET / 81) * 16; // 1 Bucket = 81000 Droplets = 1000mb || *16 ==> 16,000mb = 16 Buckets
        }

        @Override
        protected void onFinalCommit() {
            markDirty();
            getWorld().updateListeners(pos,getCachedState(),getCachedState(),3);
        }
    };

    public CrystallizerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CRYSTALLIZER_BE, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> CrystallizerBlockEntity.this.progress;
                    case 1 -> CrystallizerBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0: CrystallizerBlockEntity.this.progress = value;
                    case 1: CrystallizerBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int size() {
                return 2;
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
        return Text.translatable("gui.firstmod.crystallizer");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new CrystallizerScreenHandler(syncId, playerInventory, this,propertyDelegate);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
        nbt.putInt("crystallizer.progress",progress);
        nbt.putInt("crystallizer.max_progress",maxProgress);
        nbt.putLong("crystallizer.energy", energyStorage.amount);
        SingleVariantStorage.writeNbt(this.fluidStorage, FluidVariant.CODEC, nbt, registryLookup);

    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        Inventories.readNbt(nbt,inventory,registryLookup);
        progress = nbt.getInt("crystallizer.progress");
        maxProgress = nbt.getInt("crystallizer.max_progress");
        energyStorage.amount = nbt.getLong("crystallizer.energy");
        SingleVariantStorage.readNbt(fluidStorage, FluidVariant.CODEC, FluidVariant::blank ,nbt, registryLookup);
        super.readNbt(nbt, registryLookup);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if(world.isClient()) {
            return;
        }
        if (hasEnergyItem()) {
            consumeEnergyItem();
        }
        if(hasRecipe() && canInsertIntoOutputSlot()) {
            increaseCraftingProgress();
            useEnergyForCrafting();
            world.setBlockState(pos, state.with(CrystallizerBlock.LIT, true));
            markDirty(world, pos, state);

            if(hasCraftingFinished()) {
                craftItem();
                useFluidForCrafting();
                resetProgress();
            }
        } else {
            world.setBlockState(pos, state.with(CrystallizerBlock.LIT, false));
            resetProgress();
        }
        if (hasBucketInFluidSlot()) {
            fillFluidTank();
        }
    }

    private void consumeEnergyItem() {
        if (this.getStack(ENERGY_ITEM_SLOT).isOf(ModItems.TRUE_BLADE)) {
            for (int i = 0; i < 100; i++) {
                try (Transaction transaction = Transaction.openOuter()) {
                    this.energyStorage.insert(this.energyStorage.maxInsert, transaction);
                    inventory.set(ENERGY_ITEM_SLOT, new ItemStack(ModItems.REFINED_MITHRIL_SWORD));
                    transaction.commit();
                }
            }

        } else {
            try (Transaction transaction = Transaction.openOuter()) {
                this.energyStorage.insert(7000, transaction);
                inventory.set(ENERGY_ITEM_SLOT, ItemStack.EMPTY);
                transaction.commit();
            }
        }
    }

    private boolean hasEnergyItem() {
        return this.getStack(ENERGY_ITEM_SLOT).isOf(Items.SCULK_CATALYST) || this.getStack(ENERGY_ITEM_SLOT).isOf(ModItems.TRUE_BLADE);
    }

    private void fillFluidTank() {
        if(inventory.get(0).isOf(Items.LAVA_BUCKET) && (fluidStorage.variant.isOf(Fluids.LAVA) || fluidStorage.isResourceBlank())) {
            try(Transaction transaction = Transaction.openOuter()) {
                this.fluidStorage.insert(FluidVariant.of(Fluids.LAVA), 1000, transaction);
                inventory.set(0, new ItemStack(Items.BUCKET));
                transaction.commit();
            }
        } else if(inventory.get(0).isOf(Items.WATER_BUCKET) && (fluidStorage.variant.isOf(Fluids.WATER) || fluidStorage.isResourceBlank())) {
            try(Transaction transaction = Transaction.openOuter()) {
                this.fluidStorage.insert(FluidVariant.of(Fluids.WATER), 1000, transaction);
                inventory.set(0, new ItemStack(Items.BUCKET));
                transaction.commit();
            }
        }
    }

    private boolean hasBucketInFluidSlot() {
        return inventory.get(FLUID_ITEM_SLOT).isOf(Items.LAVA_BUCKET) || inventory.get(FLUID_ITEM_SLOT).isOf(Items.WATER_BUCKET);
    }

    private void useFluidForCrafting() {
        try(Transaction transaction = Transaction.openOuter()) {
            this.fluidStorage.extract(this.fluidStorage.variant, 10000, transaction);
            transaction.commit();
        }
    }

    private void useEnergyForCrafting() {
        try(Transaction transaction = Transaction.openOuter()) {
            this.energyStorage.extract(ENERGY_CRAFTING_AMOUNT, transaction);
            transaction.commit();
        }
    }

    private void resetProgress() {
        this.progress = 0;
        this.maxProgress = DEFAULT_MAX_PROGRESS;
    }

    private void craftItem() {
        Optional<RecipeEntry<CrystallizerRecipe>> recipe = getCurrentRecipe();

        this.removeStack(INPUT_SLOT, 1);
        this.setStack(OUTPUT_SLOT, new ItemStack(recipe.get().value().output().getItem(),
                this.getStack(OUTPUT_SLOT).getCount() + recipe.get().value().output().getCount()));
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
        Optional<RecipeEntry<CrystallizerRecipe>> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) {
            return false;
        }
        ItemStack output = recipe.get().value().getResult(null);
        return canInsertAmountIntoOutputSlot(output.getCount()) && canInsertItemIntoOutputSlot(output)
                && hasEnoughEnergyToCraft() && hasEnoughFluidToCraft();
    }

    private boolean hasEnoughFluidToCraft() {
        return this.fluidStorage.getAmount() >= 10000;
    }

    private boolean hasEnoughEnergyToCraft() {
        return this.energyStorage.amount >= (long) ENERGY_CRAFTING_AMOUNT;
    }

    private Optional<RecipeEntry<CrystallizerRecipe>> getCurrentRecipe() {
        return this.getWorld().getRecipeManager().getFirstMatch(ModRecipes.CRYSTALLIZER_TYPE, new CrystallizerRecipeInput(inventory.get(INPUT_SLOT)), this.getWorld());
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getItem() == output.getItem();
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.getStack(OUTPUT_SLOT).getMaxCount() >= this.getStack(OUTPUT_SLOT).getCount() + count;
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
        Direction localDir = this.getWorld().getBlockState(pos).get(CrystallizerBlock.FACING);

        if(side == null) {
            return false;
        }

        if(side == Direction.DOWN) {
            return false;
        }

        if(side == Direction.UP) {
            return slot == INPUT_SLOT;
        }

        return switch (localDir) {
            default -> //NORTH
                    side == Direction.NORTH && slot == FLUID_ITEM_SLOT ||
                            side == Direction.EAST && slot == FLUID_ITEM_SLOT ||
                            side == Direction.WEST && slot == FLUID_ITEM_SLOT;
            case EAST ->
                    side.rotateYCounterclockwise() == Direction.NORTH && slot == FLUID_ITEM_SLOT ||
                            side.rotateYCounterclockwise() == Direction.EAST && slot == FLUID_ITEM_SLOT ||
                            side.rotateYCounterclockwise() == Direction.WEST && slot == FLUID_ITEM_SLOT;
            case SOUTH ->
                    side.getOpposite() == Direction.NORTH && slot == FLUID_ITEM_SLOT ||
                            side.getOpposite()  == Direction.EAST && slot == FLUID_ITEM_SLOT ||
                            side.getOpposite()  == Direction.WEST && slot == FLUID_ITEM_SLOT;
            case WEST ->
                    side.rotateYClockwise() == Direction.NORTH && slot == FLUID_ITEM_SLOT ||
                            side.rotateYClockwise() == Direction.EAST && slot == FLUID_ITEM_SLOT ||
                            side.rotateYClockwise() == Direction.WEST && slot == FLUID_ITEM_SLOT;
        };
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        Direction localDir = this.getWorld().getBlockState(this.pos).get(CrystallizerBlock.FACING);

        if(side == Direction.UP) {
            return false;
        }

        // Down extract
        if(side == Direction.DOWN) {
            return slot == OUTPUT_SLOT;
        }

        // backside extract
        // right extract
        return switch (localDir) {
            default ->  side == Direction.SOUTH && slot == OUTPUT_SLOT ||
                    side == Direction.EAST && slot == OUTPUT_SLOT;

            case EAST -> side.rotateYCounterclockwise() == Direction.SOUTH && slot == OUTPUT_SLOT ||
                    side.rotateYCounterclockwise() == Direction.EAST && slot == OUTPUT_SLOT;

            case SOUTH ->  side.getOpposite() == Direction.SOUTH && slot == FLUID_ITEM_SLOT ||
                    side.getOpposite() == Direction.EAST && slot == FLUID_ITEM_SLOT;

            case WEST -> side.rotateYClockwise() == Direction.SOUTH && slot == OUTPUT_SLOT ||
                    side.rotateYClockwise() == Direction.EAST && slot == OUTPUT_SLOT;
        };
    }
}
