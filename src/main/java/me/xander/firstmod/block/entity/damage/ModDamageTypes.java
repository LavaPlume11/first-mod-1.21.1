package me.xander.firstmod.block.entity.damage;

import me.xander.first_mod;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public interface ModDamageTypes {
    RegistryKey<DamageType> FEAR_OF_FAllING = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(first_mod.MOD_ID ,"fear_of_falling"));
}
