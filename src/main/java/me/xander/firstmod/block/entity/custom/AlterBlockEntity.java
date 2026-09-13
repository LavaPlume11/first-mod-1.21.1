package me.xander.firstmod.block.entity.custom;
import me.xander.first_mod;
import me.xander.firstmod.block.ModBlocks;
import me.xander.firstmod.block.entity.ModBlockEntities;
import me.xander.firstmod.corruption.CorruptionHandler;
import me.xander.firstmod.inventory.ImplementedInventory;
import me.xander.firstmod.item.custom.LightKnife;
import me.xander.firstmod.item.custom.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Rarity;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class AlterBlockEntity extends BlockEntity implements ImplementedInventory {
    private boolean timeout = false;
    private int timer = 30;
    private int uses = 5;
    @Nullable PlayerEntity recentPlayer;
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    public AlterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ALTER_BE, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        markDirty();
        inventory.set(slot, stack.copyWithCount(1));
    }
    public void tick(World world, BlockPos pos, BlockState state) {
            if (timeout) {
                if (timer <= 0) {
                    timer = 30;
                    timeout = false;
                    uses--;
                    if (!world.isClient()) {
                        EntityType.LIGHTNING_BOLT.spawn((ServerWorld) world, pos, SpawnReason.TRIGGERED);
                        if (recentPlayer != null) {
                            ItemStack stack = this.getStack(0);
                            if (stack.isOf(ModItems.TYRINITE) || stack.isOf(ModBlocks.TYRINITE_GEM.asItem()) || stack.isOf(ModItems.TYRINITE_SWORD)){
                                this.clear();
                                world.setBlockState(pos, ModBlocks.STONE_OF_SWORD.getDefaultState().with(Properties.HORIZONTAL_FACING, state.get(Properties.HORIZONTAL_FACING)));
                            } else if(stack.isOf(ModBlocks.CORRUPTION_BLOCK.asItem())) {
                                CorruptionHandler.addCorruption((ServerPlayerEntity) recentPlayer, 5);
                            } else if(stack.isOf(ModBlocks.CORRUPTION_VINES.asItem())) {
                                CorruptionHandler.addCorruption((ServerPlayerEntity) recentPlayer, 2);
                            } else if(stack.getOrDefault(DataComponentTypes.RARITY, Rarity.COMMON) == Rarity.UNCOMMON) {
                                CorruptionHandler.addCorruption((ServerPlayerEntity) recentPlayer, 2);
                            } else if(stack.getOrDefault(DataComponentTypes.RARITY, Rarity.COMMON) == Rarity.RARE) {
                                CorruptionHandler.addCorruption((ServerPlayerEntity) recentPlayer, 3);
                            } else if(stack.getOrDefault(DataComponentTypes.RARITY, Rarity.COMMON) == Rarity.EPIC) {
                                CorruptionHandler.addCorruption((ServerPlayerEntity) recentPlayer, 6);
                            } else {
                                CorruptionHandler.addCorruption((ServerPlayerEntity) recentPlayer, 1);
                            }
                        }
                    }

                    this.clear();
                    this.markDirty();
                    world.updateListeners(pos,state,state,0);
                    if (uses <= 0) {
                        world.setBlockState(pos, Blocks.AIR.getDefaultState());
                    }
                } else {
                    timer--;
                }
            }

    }
    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
        nbt.putInt("sacrifice_timer", timer);
        nbt.putInt("uses", uses);
        nbt.putBoolean("do_timeout", timeout);
        if (recentPlayer != null) {
            nbt.putUuid("recent_player", recentPlayer.getUuid());
        }
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        Inventories.readNbt(nbt, inventory, registryLookup);
        this.timeout = nbt.getBoolean("do_timeout");
        this.timer = nbt.getInt("sacrifice_timer");
        this.uses =  nbt.getInt("uses");
        try {
            recentPlayer = this.getWorld().getPlayerByUuid(nbt.getUuid("recent_player"));
        } catch (NullPointerException ex) {
            recentPlayer = null;
        }
        super.readNbt(nbt, registryLookup);
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    public void startRitual(PlayerEntity player) {
        this.timeout = true;
        this.recentPlayer = player;
    }
}
