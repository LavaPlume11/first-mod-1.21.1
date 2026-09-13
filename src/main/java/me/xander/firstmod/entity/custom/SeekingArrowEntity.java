package me.xander.firstmod.entity.custom;

import me.xander.first_mod;
import me.xander.firstmod.effect.ModEffects;
import me.xander.firstmod.item.custom.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.EntityEffectParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.VibrationParticleEffect;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.EntityPositionSource;
import net.minecraft.world.event.PositionSource;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SeekingArrowEntity extends PersistentProjectileEntity {
    private int duration = 60;

    public SeekingArrowEntity(EntityType<? extends SeekingArrowEntity> entityType, World world) {
        super(entityType, world);
    }

    public SeekingArrowEntity(World world, LivingEntity owner, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(EntityType.ARROW, owner, world, stack, shotFrom);
    }

    public SeekingArrowEntity(World world, double x, double y, double z, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(EntityType.ARROW, x, y, z, world, stack, shotFrom);
    }
    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient) {
            if (this.inGround) {
                if (this.inGroundTime % 5 == 0) {
                    this.spawnParticles(1);
                }
            } else {
                this.spawnParticles(2);
            }
        } else if (this.inGround && this.inGroundTime != 0 && this.inGroundTime >= 600) {
            this.getWorld().sendEntityStatus(this, (byte)0);
            this.setStack(new ItemStack(ModItems.SEEKING_ARROW));
        }
        if (this.age >= 30 && !this.inGround) {
            Entity target;
            List<LivingEntity> list =  this.getWorld().getEntitiesByType(TypeFilter.instanceOf(LivingEntity.class), this.getBoundingBox().expand(20), EntityPredicates.VALID_ENTITY);
            if (!list.isEmpty()) {
               target = this.getWorld().getClosestEntity(list, TargetPredicate.DEFAULT, null, this.getX(), this.getY(), this.getZ());
                if (target != null && this.age % 2 == 0) {
                    if (this.age % 10 == 0 && !this.getWorld().isClient()) {
                        ((ServerWorld) this.getWorld()).spawnParticles(ParticleTypes.SMALL_FLAME, this.getX(), this.getY(), this.getZ(), 5, 0.01, 0.01, 0.1, 0.1);
                        ((ServerWorld) this.getWorld()).spawnParticles(new VibrationParticleEffect(new EntityPositionSource(target, 0), 15),this.getX(), this.getY(), this.getZ(), 1, 0.01, 0.01, 0.1, 0.1);
                    }
                    double d = target.getX() - this.getX();
                    double e = target.getZ() - this.getZ();
                    double c = target.getY() - this.getY();
                    double f = Math.max(d * d + e * e, 0.001);
                    this.addVelocity(d / f * 4.0, c / f * 4.0, e / f * 4.0);
                }
                if(!this.getWorld().isClient()) {
                    ((ServerWorld) this.getWorld()).spawnParticles(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), 1, 0.0, 0.0, 0.0, 0);
                }
            }
        }
    }
    private void spawnParticles(int amount) {
        int i = 9957419;
        if (i != -1 && amount > 0) {
            for(int j = 0; j < amount; ++j) {
                this.getWorld().addParticle(EntityEffectParticleEffect.create(ParticleTypes.ENTITY_EFFECT, i), this.getParticleX(0.5), this.getRandomBodyY(), this.getParticleZ(0.5), 0.0, 0.0, 0.0);
            }

        }
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("Duration")) {
            this.duration = nbt.getInt("Duration");
        }

    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Duration", this.duration);
    }

    protected ItemStack getDefaultItemStack() {
        return new ItemStack(ModItems.SEEKING_ARROW);
    }

    @Override
    public void handleStatus(byte status) {
        if (status == 0) {
            int i = 9957419;
            if (i != -1) {
                float f = (float)(i >> 16 & 255) / 255.0F;
                float g = (float)(i >> 8 & 255) / 255.0F;
                float h = (float)(i >> 0 & 255) / 255.0F;

                for(int j = 0; j < 20; ++j) {
                    this.getWorld().addParticle(EntityEffectParticleEffect.create(ParticleTypes.ENTITY_EFFECT, f, g, h), this.getParticleX(0.5), this.getRandomBodyY(), this.getParticleZ(0.5), 0.0, 0.0, 0.0);
                }
            }
        } else {
            super.handleStatus(status);
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        final double MAX_SPEED = 10.0;
        if (this.getVelocity().length() > MAX_SPEED) {
            this.setVelocity(this.getVelocity().normalize().multiply(MAX_SPEED));
            if (entityHitResult.getEntity() instanceof LivingEntity entity) {
                entity.addStatusEffect(new StatusEffectInstance(ModEffects.ACROPHOBIA, 200), this.getOwner());
            }
        }
        super.onEntityHit(entityHitResult);
    }
}
