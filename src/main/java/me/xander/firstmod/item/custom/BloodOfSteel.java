package me.xander.firstmod.item.custom;

import me.xander.firstmod.effect.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BloodOfSteel extends Item {
    public BloodOfSteel(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.isSneaking()) {
            RegistryEntry<StatusEffect> effect = ModEffects.STEEL_BLOODED;
            user.damage(user.getDamageSources().sting(user), 1);
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 600, 0, false, false, true));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 600, 1, false, false, true));
            if (!user.hasStatusEffect(effect)) {
                user.addStatusEffect(new StatusEffectInstance(effect, 600));
            } else {
                StatusEffectInstance instance = user.getStatusEffect(effect);
                if (instance != null) {
                    int amp = instance.getAmplifier();
                    user.removeStatusEffect(effect);
                    user.addStatusEffect(new StatusEffectInstance(effect, 600, amp + 1));
                }
            }
            user.getStackInHand(hand).decrementUnlessCreative(1, user);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (!user.isSneaking()) {
            RegistryEntry<StatusEffect> effect = ModEffects.STEEL_BLOODED;
            entity.damage(entity.getDamageSources().sting(user), 1);
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 600));
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 600, 1));
            if (!entity.hasStatusEffect(effect)) {
                entity.addStatusEffect(new StatusEffectInstance(effect, 600));
            } else {
                StatusEffectInstance instance = entity.getStatusEffect(effect);
                if (instance != null) {
                    int amp = instance.getAmplifier();
                    entity.removeStatusEffect(effect);
                    entity.addStatusEffect(new StatusEffectInstance(effect, 600, amp + 1));
                }
            }
            user.getStackInHand(hand).decrementUnlessCreative(1, user);
        }
        return super.useOnEntity(stack, user, entity, hand);
    }
}
