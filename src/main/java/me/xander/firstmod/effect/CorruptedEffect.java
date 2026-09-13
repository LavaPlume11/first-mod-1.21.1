package me.xander.firstmod.effect;

import com.google.common.collect.HashMultimap;
import me.xander.first_mod;
import me.xander.firstmod.corruption.CorruptionHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.Iterator;

public class CorruptedEffect extends StatusEffect {
    protected CorruptedEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (!entity.getWorld().isClient()) {
            if (entity instanceof PlayerEntity player) {
                for (Iterator<StatusEffectInstance> iterator = entity.getStatusEffects().iterator(); iterator.hasNext();) {
                    StatusEffectInstance instance = iterator.next();
                    if (instance.getEffectType().value().isBeneficial() && !player.isCreative()) {
                        entity.removeStatusEffect(instance.getEffectType());
                        break;
                    }
                }
                boolean bl = false;
                for (ItemStack stack : player.getArmorItems()) {
                    if (!stack.isEmpty()) {
                        bl = true;
                        break;
                    }
                }
                if (bl) {
                    player.damage(player.getDamageSources().cramming(), 2 * (amplifier + 1));
                }
            }

        }
        return super.applyUpdateEffect(entity, amplifier);
    }
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % (200 / (amplifier + 1)) == 0;
    }


}
