package me.xander.firstmod.effect;

import me.xander.first_mod;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
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
    public static final  RegistryEntry<StatusEffect> PURIFICATION = registerStatusEffect("purification",
            new PurificationEffect(StatusEffectCategory.BENEFICIAL, 9957419));
    public static final  RegistryEntry<StatusEffect> CORRUPTED = registerStatusEffect("corrupted",
            new CorruptedEffect(StatusEffectCategory.NEUTRAL, 6556260)
                    .addAttributeModifier(EntityAttributes.GENERIC_ARMOR,
                    Identifier.of(first_mod.MOD_ID, "corrupted"),4,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    public static final  RegistryEntry<StatusEffect> STEEL_BLOODED = registerStatusEffect("steel_blooded",
            new SteelBloodedEffect(StatusEffectCategory.BENEFICIAL, 0x3D3D46)
                    .addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,
                            Identifier.of(first_mod.MOD_ID, "steel_blooded"),-0.15,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(EntityAttributes.GENERIC_GRAVITY,
                            Identifier.of(first_mod.MOD_ID, "steel_blooded"),0.1,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(EntityAttributes.GENERIC_ARMOR,
                            Identifier.of(first_mod.MOD_ID, "steel_blooded"),5,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE,
                            Identifier.of(first_mod.MOD_ID, "steel_blooded"),0.4,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(EntityAttributes.GENERIC_EXPLOSION_KNOCKBACK_RESISTANCE,
                            Identifier.of(first_mod.MOD_ID, "steel_blooded"),0.2,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH,
                            Identifier.of(first_mod.MOD_ID, "steel_blooded"),0.2,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE,
                            Identifier.of(first_mod.MOD_ID, "steel_blooded"),0.1,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    public static final  RegistryEntry<StatusEffect> VAMPIRIFICATION = registerStatusEffect("vampirification",
            new VampirificationEffect(StatusEffectCategory.NEUTRAL, 0x700000)
                    .addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,
                            Identifier.of(first_mod.MOD_ID, "vampirification"),0.25,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(EntityAttributes.GENERIC_GRAVITY,
                            Identifier.of(first_mod.MOD_ID, "vampirification"),-0.1,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE,
                            Identifier.of(first_mod.MOD_ID, "vampirification"),0.2,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(EntityAttributes.GENERIC_JUMP_STRENGTH,
                            Identifier.of(first_mod.MOD_ID, "vampirification"),0.1,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(EntityAttributes.GENERIC_SAFE_FALL_DISTANCE,
                            Identifier.of(first_mod.MOD_ID, "vampirification"),2,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    public static final  RegistryEntry<StatusEffect> ACROPHOBIA = registerStatusEffect("acrophobia",
            new AcrophobiaEffect(StatusEffectCategory.HARMFUL, 0xFF1EB5F0));
   private static RegistryEntry<StatusEffect> registerStatusEffect(String name, StatusEffect statusEffect) {
       return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(first_mod.MOD_ID, name), statusEffect);
   }

    public static void registerEffects() {
        first_mod.LOGGER.info("Registering Mod Effects for " + first_mod.MOD_ID);
    }
}
