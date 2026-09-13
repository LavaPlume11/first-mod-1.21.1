package me.xander.firstmod.effect;

import me.xander.first_mod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class VampirificationEffect extends StatusEffect {
    protected VampirificationEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        World world = entity.getWorld();
        if (!world.isClient()) {
            if (world.isDay() && world.isSkyVisible(entity.getBlockPos()) && !entity.isOnFire()) {
                entity.setOnFireFor((3 * amplifier) + 3);
            }
            if (entity.getStackInHand(Hand.MAIN_HAND).isOf(Items.TOTEM_OF_UNDYING) || entity.getStackInHand(Hand.OFF_HAND).isOf(Items.TOTEM_OF_UNDYING)) {
                entity.damage(entity.getDamageSources().magic(), 20);
                entity.removeStatusEffect(ModEffects.VAMPIRIFICATION);
            }

            StatusEffectInstance instance = entity.getStatusEffect(ModEffects.VAMPIRIFICATION);
            if (instance != null &&instance.isDurationBelow(1)) {
                entity.damage(entity.getDamageSources().starve(), 10);
                entity.addStatusEffect(new StatusEffectInstance(ModEffects.VAMPIRIFICATION, 1200));
            }
        }
        return super.applyUpdateEffect(entity, amplifier);
    }



    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

}
