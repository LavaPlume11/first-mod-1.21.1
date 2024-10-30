package me.xander.firstmod.effect;

import me.xander.first_mod;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModEffects {
    public static final  RegistryEntry<StatusEffect> STICKY = registerStatusEffect("sticky",
            new StickyEffect(StatusEffectCategory.NEUTRAL, 0x36ebab)
                    .addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,
                            Identifier.of(first_mod.MOD_ID, "sticky"),-0.25f,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

   private static RegistryEntry<StatusEffect> registerStatusEffect(String name, StatusEffect statusEffect) {
       return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(first_mod.MOD_ID, name), statusEffect);
   }

    public static void registerEffects() {
        first_mod.LOGGER.info("Registering Mod Effects for " + first_mod.MOD_ID);
    }
}
