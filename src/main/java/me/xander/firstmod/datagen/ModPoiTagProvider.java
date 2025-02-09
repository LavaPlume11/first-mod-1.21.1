package me.xander.firstmod.datagen;

import me.xander.firstmod.villager.ModVillagers;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.data.DataOutput;
import net.minecraft.data.server.tag.TagProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.PointOfInterestTypeTags;

import java.util.concurrent.CompletableFuture;

public class ModPoiTagProvider extends FabricTagProvider {
    public ModPoiTagProvider(DataOutput output, CompletableFuture registryLookupFuture) {
        super((FabricDataOutput) output, RegistryKeys.POINT_OF_INTEREST_TYPE,registryLookupFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup lookup) {

            this.getOrCreateTagBuilder(PointOfInterestTypeTags.ACQUIRABLE_JOB_SITE)
                    .add(ModVillagers.MAGIC_POI_KEY);

    }
}
