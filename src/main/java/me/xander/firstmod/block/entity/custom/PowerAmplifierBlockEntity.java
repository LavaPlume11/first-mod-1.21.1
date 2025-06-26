package me.xander.firstmod.block.entity.custom;

import me.xander.first_mod;
import me.xander.firstmod.block.entity.ImplementedInventory;
import me.xander.firstmod.block.entity.ModBlockEntities;
import me.xander.firstmod.item.custom.ModItems;
import me.xander.firstmod.screen.custom.PowerAmplifierScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ToolComponent;
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

import java.util.List;

public class PowerAmplifierBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
    public static final int CHARGE_SLOT_1= 0;
    public static final int CRAFT_SLOT = 1;
    public static final int CHARGE_SLOT_2 = 2;

    private final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 1000;
    private int charge1 = 0;
    private int charge2 = 0;
    private int maxCharge = 1000;
    private boolean charged = false;
    private final int DEFAULT_MAX_CHARGE = 1000;
    private final int DEFAULT_MAX_PROGRESS = 1000;
    private int chargeAmount1 = 250;
    public PowerAmplifierBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.POWER_AMPLIFIER_BE, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> PowerAmplifierBlockEntity.this.progress;
                    case 1 -> PowerAmplifierBlockEntity.this.maxProgress;
                    case 2 -> PowerAmplifierBlockEntity.this.charge1;
                    case 3 -> PowerAmplifierBlockEntity.this.charge2;
                    case 4 -> PowerAmplifierBlockEntity.this.maxCharge;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> PowerAmplifierBlockEntity.this.progress = value;
                    case 1 -> PowerAmplifierBlockEntity.this.maxProgress = value;
                    case 2 -> PowerAmplifierBlockEntity.this.charge1 = value;
                    case 3 -> PowerAmplifierBlockEntity.this.charge2 = value;
                    case 4 -> PowerAmplifierBlockEntity.this.maxCharge = value;
                }
            }

            @Override
            public int size() {
                return 5;
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
        return Text.literal("Power Amplifier");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new PowerAmplifierScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
        nbt.putInt("power_amplifier.progress",progress);
        nbt.putInt("power_amplifier.max_progress",maxProgress);
        nbt.putInt("power_amplifier.charge1",charge1);
        nbt.putInt("power_amplifier.charge2",charge2);
        nbt.putInt("power_amplifier.max_charge",maxCharge);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        Inventories.readNbt(nbt, inventory, registryLookup);
        progress = nbt.getInt("power_amplifier.progress");
        maxProgress = nbt.getInt("power_amplifier.max_progress");
        charge1 = nbt.getInt("power_amplifier.charge1");
        charge2 = nbt.getInt("power_amplifier.charge2");
        maxCharge = nbt.getInt("power_amplifier.max_charge");
        super.readNbt(nbt, registryLookup);
    }
    public void tick(World world, BlockPos pos, BlockState state) {
        if(world.isClient()) {
            return;
        }
        if (hasEnergyItem1()) {
            consumeEnergyItem1();
        }
        if (hasEnergyItem2()) {
            consumeEnergyItem2();
        }
        if(hasRecipe()) {
            increaseCraftingProgress();
            useEnergyForCrafting();
            markDirty(world, pos, state);
            if(hasCraftingFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void resetProgress() {
        this.progress = 0;
        this.maxProgress = DEFAULT_MAX_PROGRESS;
        this.charged = false;
    }

    private void craftItem() {
        this.setStack(CRAFT_SLOT, new ItemStack(ModItems.TRUE_BLADE));
    }

    private boolean hasCraftingFinished() {
        return this.progress >= this.maxProgress;
    }

    private void useEnergyForCrafting() {
        this.charge1--;
        this.charge2--;
    }

    private void increaseCraftingProgress() {
        this.progress++;
    }


    private boolean hasRecipe() {
        if (charge1 > maxCharge)
            charge1 = maxCharge;
        if (charge2 > maxCharge)
            charge2 = maxCharge;
        if (this.charge1 >= maxCharge && this.charge2 >= maxCharge) {
            this.charged = true;
        }

        ItemStack input = new ItemStack(ModItems.REFINED_MITHRIL_SWORD);
        return this.getStack(CRAFT_SLOT).getItem() == input.getItem() && charged;
    }

    private void consumeEnergyItem1() {
        ItemStack stack = inventory.get(CHARGE_SLOT_1);

        if (charge1 < maxCharge) {
            charge1++;
            stack.setDamage(stack.getDamage() + 1);

            if (stack.getDamage() == stack.getMaxDamage()) {
                inventory.set(CHARGE_SLOT_1, ItemStack.EMPTY);
            }
        }
    }

    private boolean hasEnergyItem1() {
        return this.getStack(CHARGE_SLOT_1).getItem() == ModItems.POWER_CELL;
    }

    private void consumeEnergyItem2() {
        ItemStack stack = inventory.get(CHARGE_SLOT_2);

        if (charge2 < maxCharge) {
            charge2++;
            stack.setDamage(stack.getDamage() + 1);

            if (stack.getDamage() == stack.getMaxDamage()) {
                inventory.set(CHARGE_SLOT_2, ItemStack.EMPTY);
            }
        }
    }
    public List<Text> getTooltips() {
        return List.of(Text.literal(((progress / maxProgress) * 100) + "%"));
    }

    private boolean hasEnergyItem2() {
        return this.getStack(CHARGE_SLOT_2).getItem() == ModItems.POWER_CELL;
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }


}
