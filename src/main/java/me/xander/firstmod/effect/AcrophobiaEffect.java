package me.xander.firstmod.effect;

import me.xander.first_mod;
import me.xander.firstmod.block.entity.damage.ModDamageTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

public class AcrophobiaEffect extends StatusEffect {
    protected AcrophobiaEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity player && player.isFallFlying()) {
            player.stopFallFlying();
        }
        if (entity.getBlockPos().getY() >= 150 && entity.age % 20 == 0) {
            entity.damage(entity.getDamageSources().create(ModDamageTypes.FEAR_OF_FAllING), 4);
        }
        return super.applyUpdateEffect(entity, amplifier);
    }
}
