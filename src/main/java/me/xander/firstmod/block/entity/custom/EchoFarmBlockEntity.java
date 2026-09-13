package me.xander.firstmod.block.entity.custom;

import me.xander.first_mod;
import me.xander.firstmod.block.custom.EchoFarmBlock;
import me.xander.firstmod.block.entity.ModBlockEntities;
import me.xander.firstmod.inventory.ImplementedInventory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryOps;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.BlockPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.PositionSource;
import net.minecraft.world.event.Vibrations;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;

public class EchoFarmBlockEntity extends BlockEntity implements GameEventListener.Holder<Vibrations.VibrationListener>, Vibrations, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    private ListenerData listenerData;
    private final VibrationListener listener;
    private final Callback callback;
    private int progress;
    private final int MAX_COOLDOWN = 10;
    private int cooldown;
    private final int TIMER_MAX = 160;
    private int timer;
    public EchoFarmBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ECHO_FARM_BE, pos, state);
        this.callback = this.createCallback();
        this.listenerData = new ListenerData();
        this.listener = new VibrationListener(this);
        this.progress = 0;
        this.cooldown = this.MAX_COOLDOWN;
        this.timer = 0;
    }
    public Callback createCallback() {
        return new VibrationCallback(this.getPos());
    }

    @Override
    public ListenerData getVibrationListenerData() {
        return this.listenerData;
    }

    @Override
    public Callback getVibrationCallback() {
        return this.callback;
    }

    @Override
    public VibrationListener getEventListener() {
        return this.listener;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt,inventory, registryLookup);
        nbt.putInt("progress", this.progress);
        nbt.putInt("cooldown", this.cooldown);
        nbt.putInt("timer", this.timer);
        RegistryOps<NbtElement> registryOps = registryLookup.getOps(NbtOps.INSTANCE);
        ListenerData.CODEC.encodeStart(registryOps, this.listenerData).resultOrPartial((string) -> {
            first_mod.LOGGER.error((String)"Failed to encode vibration listener for Echo Farm: '{}'", (Object)string);
        }).ifPresent((listenerNbt) -> {
            nbt.put("listener", listenerNbt);
        });
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt,inventory,registryLookup);
        this.progress = nbt.getInt("progress");
        this.cooldown = nbt.getInt("cooldown");
        this.timer = nbt.getInt("timer");
        RegistryOps<NbtElement> registryOps = registryLookup.getOps(NbtOps.INSTANCE);
        if (nbt.contains("listener", NbtElement.COMPOUND_TYPE)) {
            ListenerData.CODEC.parse(registryOps, nbt.getCompound("listener")).resultOrPartial((string) -> {
                first_mod.LOGGER.error((String)"Failed to parse vibration listener for Echo Farm: '{}'", (Object)string);
            }).ifPresent((listener) -> {
                this.listenerData = listener;
            });
        }
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }
    public void tick(World world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state.with(EchoFarmBlock.PHASE, this.getStack(0).getCount()));
        if (world.isClient()) {
            return;
        }
        if (cooldown > 0) {
            cooldown--;
        }
        if (getPhase() > 0 && getPhase() < 4)
            timer++;
        if (timer >= TIMER_MAX && !this.getStack(0).isEmpty()) {
            timer = 0;
            this.setProgress(this.getProgress() + 1);
            updateProgress();
        }
    }

    public void reset() {
        this.progress = 0;
        this.timer = 0;
    }

    public void updateProgress() {
        if (this.getWorld() != null && !this.getWorld().isClient() && !this.getStack(0).isEmpty()) {
            BlockPos pos = this.getPos();
            this.getWorld().playSound(null, pos, SoundEvents.BLOCK_SCULK_SENSOR_CLICKING, SoundCategory.AMBIENT);
            ((ServerWorld) this.getWorld()).spawnParticles(new ItemStackParticleEffect(ParticleTypes.ITEM, this.getStack(0)),
                    pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 10, 0.1, 0.1, 0.1, 0.1);
        }
        cooldown = MAX_COOLDOWN;
        if (progress >= 100 && this.getPhase() < 4) {
            Block block = this.getWorld().getBlockState(this.pos).getBlock();
            this.setStack(0, this.getStack(0).copyWithCount(this.getStack(0).getCount() + 1));
            progress = 0;
        }
    }
    public int getPhase() {
        return this.getStack(0).getCount();
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    protected class VibrationCallback implements Callback {
        public static final int RANGE = 8;
        protected final BlockPos pos;
        private final PositionSource positionSource;

        public VibrationCallback(final BlockPos pos) {
            this.pos = pos;
            this.positionSource = new BlockPositionSource(pos);
        }

        public int getRange() {
            return RANGE;
        }

        public PositionSource getPositionSource() {
            return this.positionSource;
        }

        public boolean triggersAvoidCriterion() {
            return true;
        }

        public boolean accepts(ServerWorld world, BlockPos pos, RegistryEntry<GameEvent> event, @Nullable GameEvent.Emitter emitter) {
            return (!pos.equals(this.pos) || !event.matches(GameEvent.BLOCK_DESTROY) && !event.matches(GameEvent.BLOCK_PLACE)) &&
                    EchoFarmBlockEntity.this.getStack(0).isOf(Items.ECHO_SHARD) && cooldown <= 0 && getPhase() < 4;
        }

        public void accept(ServerWorld world, BlockPos pos, RegistryEntry<GameEvent> event, @Nullable Entity sourceEntity, @Nullable Entity entity, float distance) {
            EchoFarmBlockEntity.this.setProgress(EchoFarmBlockEntity.this.getProgress() + 1);
            EchoFarmBlockEntity.this.updateProgress();
        }

        public void onListen() {
            EchoFarmBlockEntity.this.markDirty();
        }

        public boolean requiresTickingChunksAround() {
            return true;
        }
    }
}
