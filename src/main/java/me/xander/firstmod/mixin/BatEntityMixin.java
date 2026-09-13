package me.xander.firstmod.mixin;

import me.xander.firstmod.effect.ModEffects;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BatEntity.class)
public abstract class BatEntityMixin extends AmbientEntity {
    protected BatEntityMixin(EntityType<? extends AmbientEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public @Nullable EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        Random random = world.getRandom();
        if (random.nextInt(100) == 0) {
            this.addStatusEffect(new StatusEffectInstance(ModEffects.VAMPIRIFICATION, -1));
        }
        return super.initialize(world, difficulty, spawnReason, entityData);
    }
    @Inject(method = "damage", at = @At(value = "RETURN"))
    private void retaliate(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (this.hasStatusEffect(ModEffects.VAMPIRIFICATION)) {
            if (source.getAttacker() != null && source.getAttacker() instanceof LivingEntity entity && Math.round(Math.random() * 10) <= 1) {
                entity.damage(entity.getDamageSources().mobAttack(this), 1);
                if (!entity.hasStatusEffect(ModEffects.VAMPIRIFICATION)) {
                    if (entity instanceof PlayerEntity) {
                        entity.addStatusEffect(new StatusEffectInstance(ModEffects.VAMPIRIFICATION, 3200));
                    } else {
                        entity.addStatusEffect(new StatusEffectInstance(ModEffects.VAMPIRIFICATION, -1));
                    }
                }
            }
        }
    }
}
