package me.xander.firstmod.entity.custom;

import me.xander.first_mod;
import me.xander.firstmod.entity.ModEntities;
import me.xander.firstmod.item.custom.WarturtleArmorItem;
import me.xander.firstmod.screen.custom.WarturtleScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;


public class WarturtleEntity extends TameableEntity implements InventoryChangedListener, RideableInventory  {
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public final AnimationState sittingTransitionAnimationState = new AnimationState();
    public final AnimationState sittingAnimationState = new AnimationState();
    public final AnimationState standingTransitionAnimationState = new AnimationState();

    public static final TrackedData<Long> LAST_POSE_TICK =
            DataTracker.registerData(WarturtleEntity.class, TrackedDataHandlerRegistry.LONG);

    public static final TrackedData<Boolean> HAS_TIER_1_CHEST =
            DataTracker.registerData(WarturtleEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> HAS_TIER_2_CHEST =
            DataTracker.registerData(WarturtleEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> HAS_TIER_3_CHEST =
            DataTracker.registerData(WarturtleEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


    protected SimpleInventory inventory;

    private final int TIER_1_CHEST_SLOT = 2;
    private final int TIER_2_CHEST_SLOT = 3;
    private final int TIER_3_CHEST_SLOT = 4;


    public WarturtleEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        this.createInventory();
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));

        this.goalSelector.add(1, new SitGoal(this));

        this.goalSelector.add(2, new AnimalMateGoal(this, 1.15D));
        this.goalSelector.add(3, new TemptGoal(this, 1.25D, Ingredient.ofItems(Items.APPLE), false));

        this.goalSelector.add(4, new FollowOwnerGoal(this, 1.1D, 10f, 3f));
        this.goalSelector.add(4, new FollowParentGoal(this,1.1D));

        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PassiveEntity.class, 4.0F));
        this.goalSelector.add(7, new LookAroundGoal(this));



    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 20;
            this.idleAnimationState.start(this.age);
        } else {
           --this.idleAnimationTimeout;
        }

        if (this.shouldUpdateSittingAnimations()) {
            this.standingTransitionAnimationState.stop();
            if (this.shouldPlaySittingTransitionAnimation()) {
                this.sittingTransitionAnimationState.startIfNotRunning(this.age);
                this.sittingAnimationState.stop();
            } else {
                this.sittingTransitionAnimationState.stop();
                this.sittingAnimationState.startIfNotRunning(this.age);
            }
        } else {
            this.sittingTransitionAnimationState.stop();
            this.sittingAnimationState.stop();
            this.standingTransitionAnimationState.setRunning(this.isChangingPose() && this.getLastPoseTickDelta() >= 0L, this.age);
        }
    }

    private boolean isChangingPose() {
        long l = this.getLastPoseTickDelta();
        return l < (long)(this.isSitting() ? 40 : 52);
    }
    public void setLastPoseTick(long lastPoseTick) {
        this.dataTracker.set(LAST_POSE_TICK, lastPoseTick);
    }
    private void initLastPoseTick(long time) {
        this.setLastPoseTick(Math.max(0L, time - 52L - 1L));
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(LAST_POSE_TICK, 0L);

        builder.add(HAS_TIER_1_CHEST, false);
        builder.add(HAS_TIER_2_CHEST, false);
        builder.add(HAS_TIER_3_CHEST, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putLong("LastPoseTick", this.dataTracker.get(LAST_POSE_TICK));

        NbtList listtag = new NbtList();
        for (int x = 0; x < this.inventory.size(); x++) {
            ItemStack itemStack = this.inventory.getStack(x);
            if (!itemStack.isEmpty()) {
                NbtCompound compoundtag = new NbtCompound();
                compoundtag.putByte("Slot", (byte) (x));
                listtag.add(itemStack.encode(this.getRegistryManager(), compoundtag));
            }
        }
        nbt.put("Items", listtag);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        long l = nbt.getLong("LastPoseTick");
        if (l < 0L) {
            this.setPose(EntityPose.SITTING);
        }
        this.setLastPoseTick(l);

        this.createInventory();
        NbtList listtag = nbt.getList("Items", 10);
        for (int x = 0; x < listtag.size(); x++) {
            NbtCompound compoundtag = listtag.getCompound(x);
            int j = compoundtag.getByte("Slot") & 255;
            if (j < this.inventory.size()) {
                this.inventory.setStack(j, ItemStack.fromNbt(this.getRegistryManager(), compoundtag).orElse(ItemStack.EMPTY));
            }
        }
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        this.initLastPoseTick(world.toServerWorld().getTime());
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    private boolean shouldPlaySittingTransitionAnimation() {
        return this.isSitting() && this.getLastPoseTickDelta() < 40L && this.getLastPoseTickDelta() >= 0L;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient()) {
            this.setupAnimationStates();
        }
    }

    private long getLastPoseTickDelta() {
        return this.getWorld().getTime() - Math.abs(this.dataTracker.get(LAST_POSE_TICK));
    }

    private boolean shouldUpdateSittingAnimations() {
        return this.getLastPoseTickDelta() < 0L != this.isSitting();
    }
    public static DefaultAttributeContainer.Builder createWarturtleAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH,45)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.15f)
                .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS,2)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE,1);
    }

    @Override
    public boolean isSitting() {
        return this.dataTracker.get(LAST_POSE_TICK) < 0L;
    }

    public void startStanding() {
        if (!this.isSitting()) {
            return;
        }
        this.playSound(SoundEvents.ENTITY_CAMEL_STAND);
        this.setPose(EntityPose.STANDING);
        this.emitGameEvent(GameEvent.ENTITY_ACTION);
        this.setLastPoseTick(this.getWorld().getTime());
        setInSittingPose(false);
        setSitting(false);
    }

    public void startSitting() {
        if (this.isSitting()) {
            return;
        }
        this.playSound(SoundEvents.ENTITY_CAMEL_SIT);
        this.setPose(EntityPose.SITTING);
        this.emitGameEvent(GameEvent.ENTITY_ACTION);
        this.setLastPoseTick(-this.getWorld().getTime());
        setInSittingPose(true);
        setSitting(true);
    }
    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getStackInHand(hand);
        Item item = itemstack.getItem();

        Item itemForTaming = Items.APPLE;

        if(item == itemForTaming && !isTamed()) {
            if(this.getWorld().isClient()) {
                return ActionResult.CONSUME;
            } else {
                if (!player.getAbilities().creativeMode) {
                    itemstack.decrement(1);
                }

                super.setOwner(player);
                this.navigation.recalculatePath();
                this.setTarget(null);
                this.getWorld().sendEntityStatus(this, (byte)7);
                toggleSitting();

                return ActionResult.SUCCESS;
            }
        }

        if(isTamed() && hand == Hand.MAIN_HAND && item != itemForTaming && !isBreedingItem(itemstack) && !player.shouldCancelInteraction()) {
            toggleSitting();
            return ActionResult.SUCCESS;
        } else if (this.isTamed()) {
            this.openInventory(player);
            return ActionResult.success(this.getWorld().isClient);
        }

        return super.interactMob(player, hand);
    }
    public void toggleSitting() {
        if (this.isSitting()) {
            startStanding();
        } else {
            startSitting();
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.APPLE);
    }

    @Override
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.WARTURTLE.create(world);
    }
    @Override
    public void onInventoryChanged(Inventory sender) {

        if(sender.getStack(TIER_1_CHEST_SLOT).isOf(Items.CHEST) && !hasTier1Chest()) {
            setChest(TIER_1_CHEST_SLOT, true);
        }
        if(sender.getStack(TIER_2_CHEST_SLOT).isOf(Items.CHEST) && !hasTier2Chest()) {
            setChest(TIER_2_CHEST_SLOT, true);
        }
        if(sender.getStack(TIER_3_CHEST_SLOT).isOf(Items.CHEST) && !hasTier3Chest() || sender.getStack(TIER_3_CHEST_SLOT).isOf(Items.ENDER_CHEST))
        {setChest(TIER_3_CHEST_SLOT, true);}


        if(!sender.getStack(TIER_1_CHEST_SLOT).isOf(Items.CHEST) && hasTier1Chest()) {
            setChest(TIER_1_CHEST_SLOT, false);
            dropChestInventory(TIER_1_CHEST_SLOT);
        }
        if(!sender.getStack(TIER_2_CHEST_SLOT).isOf(Items.CHEST) && hasTier2Chest()) {
            setChest(TIER_2_CHEST_SLOT, false);
            dropChestInventory(TIER_2_CHEST_SLOT);
        }
        if (!sender.getStack(TIER_3_CHEST_SLOT).isOf(Items.CHEST) && hasTier3Chest()) {
        if(!sender.getStack(TIER_3_CHEST_SLOT).isOf(Items.ENDER_CHEST) && hasTier3Chest()){
            setChest(TIER_3_CHEST_SLOT, false);
            dropChestInventory(TIER_3_CHEST_SLOT);
        }

        }
        if (sender.getStack(0).getItem() instanceof WarturtleArmorItem) {
            equipBodyArmor(sender.getStack(0));
        }
        if (sender.getStack(0).isEmpty() && isWearingBodyArmor()) {
            equipBodyArmor(ItemStack.EMPTY);
        }
    }
    private void dropChestInventory(int slot) {
        if(slot == TIER_1_CHEST_SLOT) {
            ItemScatterer.spawn(this.getWorld(), this.getX(), this.getY() + 1, this.getZ(), inventory.removeStack(5, 64));
            ItemScatterer.spawn(this.getWorld(), this.getX(), this.getY() + 1, this.getZ(), inventory.removeStack(6, 64));
            ItemScatterer.spawn(this.getWorld(), this.getX(), this.getY() + 1, this.getZ(), inventory.removeStack(7, 64));
            ItemScatterer.spawn(this.getWorld(), this.getX(), this.getY() + 1, this.getZ(), inventory.removeStack(8, 64));
        }

        if(slot == TIER_2_CHEST_SLOT) {
            ItemScatterer.spawn(this.getWorld(), this.getX(), this.getY() + 1, this.getZ(), inventory.removeStack(9, 64));
            ItemScatterer.spawn(this.getWorld(), this.getX(), this.getY() + 1, this.getZ(), inventory.removeStack(10, 64));
            ItemScatterer.spawn(this.getWorld(), this.getX(), this.getY() + 1, this.getZ(), inventory.removeStack(11, 64));
            ItemScatterer.spawn(this.getWorld(), this.getX(), this.getY() + 1, this.getZ(), inventory.removeStack(12, 64));
        }

        if(slot == TIER_3_CHEST_SLOT) {
            ItemScatterer.spawn(this.getWorld(), this.getX(), this.getY() + 1, this.getZ(), inventory.removeStack(13, 64));
            ItemScatterer.spawn(this.getWorld(), this.getX(), this.getY() + 1, this.getZ(), inventory.removeStack(14, 64));
            ItemScatterer.spawn(this.getWorld(), this.getX(), this.getY() + 1, this.getZ(), inventory.removeStack(15, 64));
            ItemScatterer.spawn(this.getWorld(), this.getX(), this.getY() + 1, this.getZ(), inventory.removeStack(16, 64));
        }
    }
    public void setChest(int slot, boolean chested) {
        if(slot == TIER_1_CHEST_SLOT) {
            this.dataTracker.set(HAS_TIER_1_CHEST, chested);
        } else if(slot == TIER_2_CHEST_SLOT) {
            this.dataTracker.set(HAS_TIER_2_CHEST, chested);
        } else if(slot == TIER_3_CHEST_SLOT) {
            this.dataTracker.set(HAS_TIER_3_CHEST, chested);
        } else {
            first_mod.LOGGER.error("Can't give chest to a Slot that doesn't exist!");
        }
    }
    public boolean hasTier1Chest() {
        return this.dataTracker.get(HAS_TIER_1_CHEST);
    }
    public boolean hasTier2Chest() {
        return this.dataTracker.get(HAS_TIER_2_CHEST);
    }
    public boolean hasTier3Chest() {
        return this.dataTracker.get(HAS_TIER_3_CHEST);
    }

    protected void createInventory() {
        SimpleInventory simpleInventory = this.inventory;
        this.inventory = new SimpleInventory(this.getInventorySize());
        if (simpleInventory != null) {
            simpleInventory.removeListener(this);
            int i = Math.min(simpleInventory.size(), this.inventory.size());
            for (int j = 0; j < i; ++j) {
                ItemStack itemStack = simpleInventory.getStack(j);
                if (itemStack.isEmpty())continue;
                this.inventory.setStack(j, itemStack.copy());
            }
        }
        this.inventory.addListener(this);
    }

    public final int getInventorySize() {
        return getInventorySize(4);
    }
    public static int getInventorySize(int columns) {
        return columns * 3 + 5;
    }
    public boolean areInventoriesDifferent(Inventory inventory) {
        return this.inventory != inventory;
    }

    @Override
    public void openInventory(PlayerEntity player) {

        if(!this.getWorld().isClient() && isTamed()) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
            if (serverPlayer.currentScreenHandler != serverPlayer.playerScreenHandler) {
                serverPlayer.closeHandledScreen();
            }

            serverPlayer.openHandledScreen(new ExtendedScreenHandlerFactory<UUID>() {
                @Nullable
                @Override
                public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
                    return new WarturtleScreenHandler(syncId, playerInventory, WarturtleEntity.this.inventory, WarturtleEntity.this, 4);
                }

                @Override
                public Text getDisplayName() {
                    return Text.literal("Warturtle");
                }

                @Override
                public UUID getScreenOpeningData(ServerPlayerEntity player) {
                    return WarturtleEntity.this.getUuid();
                }
            });
        }

    }

    public boolean hasArmorOn() {
        return isWearingBodyArmor();
    }

    @Override
    protected void applyDamage(DamageSource source, float amount) {
        if (!this.canArmorAbsorb(source)) {
            super.applyDamage(source, amount);
        } else {
            ItemStack itemStack = this.getBodyArmor();
            itemStack.damage(MathHelper.ceil(amount), this, EquipmentSlot.BODY);

            if (itemStack.getItem() instanceof WarturtleArmorItem warturtleArmorItem) {
                int damagereduction = warturtleArmorItem.getProtection() / 2;
                super.applyDamage(source, Math.max(0, amount - damagereduction));
            }
        }
    }

    private boolean canArmorAbsorb(DamageSource damageSource) {
        return this.hasArmorOn() && !damageSource.isIn(DamageTypeTags.BYPASSES_WOLF_ARMOR);
    }
}
