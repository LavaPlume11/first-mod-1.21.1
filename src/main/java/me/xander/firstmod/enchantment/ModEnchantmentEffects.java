package me.xander.firstmod.enchantment;

import com.mojang.serialization.MapCodec;
import me.xander.first_mod;
import me.xander.firstmod.enchantment.custom.ImprovedSmiteEnchantmentEffect;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEnchantmentEffects {

    public static final MapCodec<? extends EnchantmentEntityEffect> IMPROVED_SMITE =
            registerEntityEffect("improved_smite", ImprovedSmiteEnchantmentEffect.CODEC);

    private static MapCodec<? extends EnchantmentEntityEffect> registerEntityEffect(String name,
                                                                                    MapCodec<? extends EnchantmentEntityEffect> codec){
        return Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.of(first_mod.MOD_ID,name), codec);
    }




    public static void registerEnchantmentEffects() {
        first_mod.LOGGER.info("Registering Mod Enchantment Effects for" + first_mod.MOD_ID);
    }
}
