package me.xander.firstmod.potion;

import me.xander.first_mod;
import me.xander.firstmod.effect.ModEffects;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModPotions {
    public static final RegistryEntry<Potion> STICKY_POTION = registerPotion("sticky_potion",
            new Potion(new StatusEffectInstance(ModEffects.STICKY,3600, 0)));
    public static final RegistryEntry<Potion> LONG_STICKY_POTION = registerPotion("long_sticky_potion",
            new Potion(new StatusEffectInstance(ModEffects.STICKY,9600, 0)));



    private static RegistryEntry<Potion> registerPotion(String name, Potion potion) {
        return Registry.registerReference(Registries.POTION, Identifier.of(first_mod.MOD_ID, name), potion);
    }


    public static void registerPotions() {
        first_mod.LOGGER.info("Registering Mod Potions for" + first_mod.MOD_ID);
    }
}
