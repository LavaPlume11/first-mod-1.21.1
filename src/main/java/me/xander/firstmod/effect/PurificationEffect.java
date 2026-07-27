package me.xander.firstmod.effect;

import me.xander.firstmod.corruption.CorruptionHandler;
import me.xander.firstmod.util.mixin.PlayerEntityAccess;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

import java.util.ArrayList;

public class PurificationEffect extends StatusEffect {
    protected PurificationEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (!entity.getWorld().isClient()) {
            if (entity instanceof PlayerEntity player && CorruptionHandler.getCorruption(player) > 0) {
                CorruptionHandler.subtractCorruption(((ServerPlayerEntity) player), amplifier + 1);
                player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.PLAYERS, 1f, 1f);
                player.damage(player.getDamageSources().magic(), 1);
            } else {
                if (entity instanceof PlayerEntity player) {
                    ((PlayerEntityAccess) player).first_mod_template_1_21_1$setCorruptedKills(new ArrayList<>());
                }
                entity.heal(amplifier + 1);
            }
        }
        return super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % 20 == 0;
    }
}
