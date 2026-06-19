package me.xander.firstmod.entity.custom;

import me.xander.firstmod.entity.ai.goal.WhispererTargetGoal;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class GrazeEntity extends HostileEntity implements Monster {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState digAnimationState = new AnimationState();
    public final AnimationState emergeAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private int digTimer = 0;
    public boolean digging = false;
    public GrazeEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }
    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.goalSelector.add(4, new MeleeAttackGoal(this, (double) 1.0F, false));
        this.targetSelector.add(2, new WhispererTargetGoal<PlayerEntity>(this, PlayerEntity.class, true));

    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0 && !digging) {
            this.idleAnimationTimeout = 40;
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient()) {
            this.setupAnimationStates();
        }
        if (digTimer >= 440) {
            endDig();
        }
        if (this.age % 200 == 0 && digTimer == 0) {
            startDig();
        }
        if (digging) {
            digTimer++;
            this.getHitbox().offset(0, -2, 0);
            if (!this.getWorld().isClient()) {
                ((ServerWorld) this.getWorld()).spawnParticles(ParticleTypes.LAVA, this.getX(), this.getY(), this.getZ(), 1, 0.1, 0.1, 0.1, 0.5);
            }
        }

    }



    public static DefaultAttributeContainer.Builder createGrazeAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 15)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 9)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32);
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return SoundEvents.BLOCK_GRASS_STEP;
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.BLOCK_STONE_BREAK;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.BLOCK_GRASS_BREAK;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source.isIn(DamageTypeTags.IS_EXPLOSION))
            return super.damage(source, amount * 2);
        if (source.isOf(DamageTypes.IN_WALL)) {
            return false;
        }

        return super.damage(source, amount);
    }
    public void startDig() {
        if (this.getWorld().isClient()) {
            idleAnimationState.stop();
            digAnimationState.start(this.age);
        }
        digging = true;

    }
    private void endDig() {
        if (this.getWorld().isClient())
            emergeAnimationState.start(this.age);
        digging = false;
        digTimer = 0;
    }
}
