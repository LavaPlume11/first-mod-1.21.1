package me.xander.firstmod.world.gen;

import me.xander.firstmod.entity.ModEntities;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;

public class ModEntitySpawns {
    public static void addSpawns() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.SAVANNA),
                SpawnGroup.CREATURE, ModEntities.LEMMING,50,2,10);

        SpawnRestriction.register(ModEntities.LEMMING, SpawnLocationTypes.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.OCEAN,BiomeKeys.DEEP_COLD_OCEAN,
                        BiomeKeys.DEEP_OCEAN,BiomeKeys.COLD_OCEAN,BiomeKeys.FROZEN_OCEAN,BiomeKeys.DEEP_FROZEN_OCEAN),
                SpawnGroup.CREATURE, ModEntities.WHISPERER,50,2,10);

        SpawnRestriction.register(ModEntities.WHISPERER, SpawnLocationTypes.IN_WATER,
                Heightmap.Type.OCEAN_FLOOR, AnimalEntity::isValidNaturalSpawn);
    }
}
