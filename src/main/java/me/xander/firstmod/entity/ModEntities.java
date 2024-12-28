package me.xander.firstmod.entity;

import me.xander.first_mod;
import me.xander.firstmod.entity.custom.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<LionEntity> LION = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(first_mod.MOD_ID, "lion"),
            EntityType.Builder.create(LionEntity::new, SpawnGroup.CREATURE).dimensions(2f,2.5f).build());
    public static final EntityType<LemmingEntity> LEMMING = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(first_mod.MOD_ID, "lemming"),
            EntityType.Builder.create(LemmingEntity::new, SpawnGroup.CREATURE).dimensions(0.5f,0.5f).build());
    public static final EntityType<WhispererEntity> WHISPERER = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(first_mod.MOD_ID, "whisperer"),
            EntityType.Builder.create(WhispererEntity::new, SpawnGroup.CREATURE).dimensions(1f,1f).build());
    public static final EntityType<WarturtleEntity> WARTURTLE = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(first_mod.MOD_ID, "warturtle"),
            EntityType.Builder.create(WarturtleEntity::new, SpawnGroup.CREATURE).dimensions(2.5f,1.5f).build());
    public static final EntityType<TomahawkProjectileEntity> TOMAHAWK = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(first_mod.MOD_ID, "tomahawk"),
            EntityType.Builder.<TomahawkProjectileEntity>create(TomahawkProjectileEntity::new, SpawnGroup.MISC).dimensions(0.5f,1.15f).build());
    public static final EntityType<SleighEntity> SLEIGH = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(first_mod.MOD_ID, "sleigh"),
            EntityType.Builder.create(SleighEntity::new, SpawnGroup.MISC).dimensions(2.5f,1.5f).build());
    public static void registerModEntities() {
        first_mod.LOGGER.info("Registering Mod Entities for" + first_mod.MOD_ID);
    }
}

