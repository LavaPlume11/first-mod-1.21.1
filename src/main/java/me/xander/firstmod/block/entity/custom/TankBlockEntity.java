package me.xander.firstmod.block.entity.custom;

import me.xander.firstmod.block.custom.CrystallizerBlock;
import me.xander.firstmod.block.entity.ImplementedInventory;
import me.xander.firstmod.block.entity.ModBlockEntities;
import me.xander.firstmod.block.entity.fluid.FluidUtils;
import me.xander.firstmod.screen.custom.TankScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.SingleFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;


public class TankBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);

    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<FluidVariant>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(FluidVariant fluidVariant) {
            return (FluidConstants.BUCKET / 81) * 64; // 1 Bucket = 81000 Droplets = 1000mb || *64 ==> 64,000mb = 64 Buckets
        }

        @Override
        protected void onFinalCommit() {
            markDirty();
            getWorld().updateListeners(pos,getCachedState(),getCachedState(),3);
        }
    };
    public TankBlockEntity( BlockPos pos, BlockState state) {
        super(ModBlockEntities.TANK_BE, pos, state);
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
        return Text.literal("Tank");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new TankScreenHandler(syncId, playerInventory, this);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (hasFluidStackInFirstSlot()) {
            transferFluidToTank();
        }

        if (hasFluidHandlerInSecondSlot()) {
            transferFluidFromTankToHandler();
        }
    }

    private boolean hasFluidHandlerInSecondSlot() {
        return !inventory.get(1).isEmpty() && FluidUtils.isContainer(inventory.get(1))
                && (FluidUtils.isContainerEmpty(inventory.get(1)) || FluidUtils.doesContainerStillHaveSpace(inventory.get(1))) && !fluidStorage.isResourceBlank();
    }

    private void transferFluidFromTankToHandler() {
        if(inventory.get(1).isOf(Items.BUCKET) && !(inventory.get(1).getCount() > 1)) {
            try(Transaction transaction = Transaction.openOuter()) {
                FluidVariant variant = fluidStorage.variant;
                this.fluidStorage.extract(fluidStorage.variant, 1000, transaction);
                if(variant.isOf(Fluids.LAVA)) {
                    inventory.set(1, new ItemStack(Items.LAVA_BUCKET));
                } else if(variant.isOf(Fluids.WATER)) {
                    inventory.set(1, new ItemStack(Items.WATER_BUCKET));
                }
                transaction.commit();
            }
        }
    }

    private void transferFluidToTank() {
        if (inventory.get(0).isOf(Items.LAVA_BUCKET) && (fluidStorage.variant.isOf(Fluids.LAVA) || fluidStorage.isResourceBlank())) {
            try (Transaction transaction = Transaction.openOuter()) {
                this.fluidStorage.insert(FluidVariant.of(Fluids.LAVA), 1000, transaction);
                inventory.set(0, new ItemStack(Items.BUCKET));
                transaction.commit();
            }
        }
        else if (inventory.get(0).isOf(Items.WATER_BUCKET) && (fluidStorage.variant.isOf(Fluids.WATER) || fluidStorage.isResourceBlank())) {
                try (Transaction transaction = Transaction.openOuter()) {
                    this.fluidStorage.insert(FluidVariant.of(Fluids.WATER), 1000, transaction);
                    inventory.set(0, new ItemStack(Items.BUCKET));
                    transaction.commit();


                }
            }
    }

    private boolean hasFluidStackInFirstSlot() {
        return !inventory.get(0).isEmpty() && FluidUtils.isContainer(inventory.get(0))
                && !FluidUtils.isContainerEmpty(inventory.get(0));
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
        SingleVariantStorage.writeNbt(this.fluidStorage, FluidVariant.CODEC, nbt, registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, inventory, registryLookup);
        SingleVariantStorage.readNbt(fluidStorage, FluidVariant.CODEC, FluidVariant::blank ,nbt, registryLookup);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }
    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {

        if(side == null) {
            return false;
        }

        if(side == Direction.DOWN) {
            return false;
        } else if(side == Direction.UP) {
            return slot == 0;
        } else return false;


    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {

        if(side == Direction.UP) {
            return false;
        } else
        // Down extract
        if(side == Direction.DOWN) {
            return slot == 0;
        }else {
            return slot == 1;
        }




    }
}
