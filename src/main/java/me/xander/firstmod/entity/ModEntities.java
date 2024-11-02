package me.xander.firstmod.entity;

import me.xander.first_mod;
import me.xander.firstmod.entity.custom.LionEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<LionEntity> LION = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(first_mod.MOD_ID, "lion"),
            EntityType.Builder.create(LionEntity::new, SpawnGroup.CREATURE).dimensions(2f,2.5f).build());
    public static void registerModEntities() {
        first_mod.LOGGER.info("Registering Mod Entities for" + first_mod.MOD_ID);
    }
}

