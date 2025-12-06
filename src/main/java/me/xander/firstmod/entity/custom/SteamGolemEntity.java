package me.xander.firstmod.entity.custom;

import me.xander.first_mod;
import me.xander.firstmod.entity.ai.goal.CustomTargetGoal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class SteamGolemEntity extends IronGolemEntity {
    int particleCount = 0;
    public SteamGolemEntity(EntityType<? extends IronGolemEntity> entityType, World world) {
        super(entityType, world);
    }
    protected void initGoals() {
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.add(2, new WanderNearTargetGoal(this, 0.9, 32.0F));
        this.goalSelector.add(2, new WanderAroundPointOfInterestGoal(this, 0.6, false));
        this.goalSelector.add(4, new IronGolemWanderAroundGoal(this, 0.6));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.targetSelector.add(2, new RevengeGoal(this, new Class[0]));
        this.targetSelector.add(3, new ActiveTargetGoal(this, MobEntity.class, 5, false, false, (entity) -> {
            return entity instanceof Monster && !(entity instanceof CreeperEntity);
        }));
        this.targetSelector.add(2, new CustomTargetGoal<>(this, IronGolemEntity.class, true));
        this.targetSelector.add(4, new UniversalAngerGoal(this, false));
    }
    public static DefaultAttributeContainer.Builder createSteamGolemAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 70)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 12.0)
                .add(EntityAttributes.GENERIC_STEP_HEIGHT, 1.0);
    }


    @Override
    public boolean tryAttack(Entity target) {
        target.setOnFireForTicks(60);
        if (target instanceof IronGolemEntity golem && !(golem instanceof SteamGolemEntity)) {
            target.damage(target.getDamageSources().mobAttack(this), 15);
            target.dropItem(Items.IRON_INGOT);
        }
        return super.tryAttack(target);
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (!itemStack.isOf(Items.MAGMA_CREAM)) {
            return ActionResult.PASS;
        } else {
            float f = this.getHealth();
            this.heal(25.0F);
            if (this.getHealth() == f) {
                return ActionResult.PASS;
            } else {
                float g = 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F;
                this.playSound(SoundEvents.ENTITY_IRON_GOLEM_REPAIR, 1.0F, g);
                itemStack.decrementUnlessCreative(1, player);
                return ActionResult.success(this.getWorld().isClient);
            }
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source.isIn(DamageTypeTags.IS_FIRE)) {
            if (this.isOnFire() && !this.getWorld().isClient() && particleCount >= 10) {
                ((ServerWorld) this.getWorld()).spawnParticles(ParticleTypes.CLOUD,this.getX(), this.getY() + 2.5, this.getZ(),20,0.05,0.2,0.05, 0.1);
                particleCount = 0;
            }
            return false;
        }
        return super.damage(source, amount);
    }

    @Override
    public void tick() {
        particleCount++;
        if (particleCount >= 200) {
            if (!this.getWorld().isClient()) {
                ((ServerWorld) this.getWorld()).spawnParticles(ParticleTypes.CLOUD,this.getX(), this.getY() + 2.5, this.getZ(),10,0.05,0.2,0.05, 0.1);
            }
            particleCount = 0;
        }
        super.tick();
    }
}
