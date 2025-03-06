package me.xander.firstmod.entity.custom;

import me.xander.firstmod.entity.ModEntities;
import me.xander.firstmod.sound.ModSounds;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class WhispererEntity extends AnimalEntity implements Angerable {
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private static final UniformIntProvider ANGER_TIME_RANGE;
    private int angerTime;


    @Nullable
    private UUID angryAt;

    public WhispererEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(5, new WanderAroundGoal(this, 0.8));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 10;
            this.idleAnimationState.start(this.age);
        }else{
            --this.idleAnimationTimeout;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient()) {
            this.setupAnimationStates();
        }

    }

    public static DefaultAttributeContainer.Builder createWhispererAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 12)
                .add(EntityAttributes.GENERIC_WATER_MOVEMENT_EFFICIENCY,2)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    @Override
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.WHISPERER.create(world);
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return ModSounds.WHISPERER_IDLE;
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_GHAST_HURT;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return ModSounds.WHISPERER_IDLE;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
            if (source.isIn(DamageTypeTags.IS_DROWNING))
                return false;
        return super.damage(source, amount);
    }
/* ANGER */
static {
    ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);
}
public void chooseRandomAngerTime() {
    this.setAngerTime(ANGER_TIME_RANGE.get(this.random));
}

    public void setAngerTime(int angerTime) {
        this.angerTime = angerTime;
    }

    public int getAngerTime() {
        return this.angerTime;
    }

    public void setAngryAt(@Nullable UUID angryAt) {
        this.angryAt = angryAt;
    }

    @Nullable
    public UUID getAngryAt() {
        return this.angryAt;
    }
}
