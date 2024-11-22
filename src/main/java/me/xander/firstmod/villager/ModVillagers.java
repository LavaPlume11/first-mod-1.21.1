package me.xander.firstmod.villager;

import com.google.common.collect.ImmutableSet;
import me.xander.first_mod;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;

public class ModVillagers {
    public static final RegistryKey<PointOfInterestType> MAGIC_POI_KEY = registerPoiKey("magic_poi");
    public static final PointOfInterestType MAGIC_POI = registerPOI("magic_poi", Blocks.BEACON);

    public static final VillagerProfession CULTIST = registerProfession("cultist", MAGIC_POI_KEY);

    private static VillagerProfession registerProfession(String name, RegistryKey<PointOfInterestType> type) {
        return Registry.register(Registries.VILLAGER_PROFESSION, Identifier.of(first_mod.MOD_ID, name),
                new VillagerProfession(name, entry -> entry.matchesKey(type), entry -> entry.matchesKey(type),
                ImmutableSet.of(), ImmutableSet.of(), SoundEvents.ENTITY_VILLAGER_WORK_LIBRARIAN));
    }

    private static PointOfInterestType registerPOI(String name, Block block) {
        return PointOfInterestHelper.register(Identifier.of(first_mod.MOD_ID, name),
                1,1, block);
    }

private static RegistryKey<PointOfInterestType> registerPoiKey(String name) {
    return RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, Identifier.of(first_mod.MOD_ID, name));
}

    public static void registerVillagers() {
        first_mod.LOGGER.info("Registering Villagers for"+ first_mod.MOD_ID);
    }
}
