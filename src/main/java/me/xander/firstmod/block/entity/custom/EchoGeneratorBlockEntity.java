package me.xander.firstmod.block.entity.custom;

import me.xander.firstmod.block.entity.ImplementedInventory;
import me.xander.firstmod.block.entity.ModBlockEntities;
import me.xander.firstmod.screen.custom.EchoGeneratorScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.EnergyStorageUtil;
import team.reborn.energy.api.base.SimpleEnergyStorage;

public class EchoGeneratorBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);

    private static final int INPUT_SLOT = 0;

    protected final PropertyDelegate propertyDelegate;
    private int burnProgress = 260;
    private int maxBurnProgress = 260;
    private boolean isBurning = false;

    private static final int ENERGY_TRANSFER_AMOUNT = 320;

    public final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(228000, ENERGY_TRANSFER_AMOUNT, ENERGY_TRANSFER_AMOUNT) {
        @Override
        protected void onFinalCommit() {
            markDirty();
            getWorld().updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
    };

    public EchoGeneratorBlockEntity( BlockPos pos, BlockState state) {
        super(ModBlockEntities.ECHO_GENERATOR_BE, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> EchoGeneratorBlockEntity.this.burnProgress;
                    case 1 -> EchoGeneratorBlockEntity.this.maxBurnProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0: EchoGeneratorBlockEntity.this.burnProgress = value;
                    case 1: EchoGeneratorBlockEntity.this.maxBurnProgress = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }




    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
        nbt.putLong(("echo_generator.energy"), energyStorage.amount);
        nbt.putInt(("echo_generator.burn_progress"), burnProgress);
        nbt.putInt(("echo_generator.max_burn_progress"), maxBurnProgress);
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        Inventories.readNbt(nbt, inventory, registryLookup);
        energyStorage.amount = nbt.getLong("echo_generator.energy");
        burnProgress = nbt.getInt("echo_generator.burn_progress");
        maxBurnProgress = nbt.getInt("echo_generator.max_burn_progress");

        super.readNbt(nbt, registryLookup);
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return this.pos;
    }
    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Echo Generator");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new EchoGeneratorScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if(world.isClient()) {
            return;
        }

        if(hasFuelItemInSlot()) {
            if(!isBurningFuel() && !isFullOnPower()) {
                startBurning();
            }
        }

        if(isBurningFuel()) {
            increaseBurnTimer();
            if(currentFuelDoneBurning()) {
                resetBurning();
            }
            fillUpOnEnergy();
        }

        pushEnergyToAboveNeighbour();
    }

    private boolean isFullOnPower() {
        return (this.energyStorage.amount) >= 228000;
    }

    private void pushEnergyToAboveNeighbour() {
        EnergyStorageUtil.move(this.energyStorage, EnergyStorage.SIDED.find(world, pos.up(), null), Long.MAX_VALUE, null);
    }

    private void fillUpOnEnergy() {
        try(Transaction transaction = Transaction.openOuter()) {
            this.energyStorage.insert(320, transaction);
            transaction.commit();
        }
    }

    private void resetBurning() {
        isBurning = false;
        this.burnProgress = 260;
    }

    private boolean currentFuelDoneBurning() {
        return this.burnProgress <= 0;
    }

    private void increaseBurnTimer() {
        this.burnProgress--;
    }

    private void startBurning() {
        this.removeStack(INPUT_SLOT, 1);
        isBurning = true;
    }

    private boolean isBurningFuel() {
        return isBurning;
    }

    private boolean hasFuelItemInSlot() {
        return this.getStack(INPUT_SLOT).isOf(Items.ECHO_SHARD);
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
}
