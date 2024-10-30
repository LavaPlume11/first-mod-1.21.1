package me.xander.firstmod.datagen;

import me.xander.firstmod.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.RUBY_BLOCK)
                .add(ModBlocks.RUBY_ORE)
                .add(ModBlocks.MITHRIL_BLOCK)
                .add(ModBlocks.MITHRIL_ORE)
                .add(ModBlocks.DEEPSLATE_MITHRIL_ORE)
                .add(ModBlocks.NETHER_MITHRIL_ORE)
                .add(ModBlocks.SOUND_BLOCK)
                .add(ModBlocks.MITHRIL_FENCE)
                .add(ModBlocks.MITHRIL_FENCE_GATE)
                .add(ModBlocks.MITHRIL_BUTTON)
                .add(ModBlocks.MITHRIL_WALL)
                .add(ModBlocks.MITHRIL_DOOR)
                .add(ModBlocks.MITHRIL_SLAB)
                .add(ModBlocks.MITHRIL_STAIRS)
                .add(ModBlocks.MITHRIL_TRAPDOOR)
                .add(ModBlocks.MITHRIL_PRESSURE_PLATE)
                .add(ModBlocks.MITHRIL_DISPLAY_BLOCK);

        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.RUBY_BLOCK)
                .add(ModBlocks.RUBY_ORE)
                .add(ModBlocks.NETHER_MITHRIL_ORE);

        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.MITHRIL_BLOCK)
                .add(ModBlocks.MITHRIL_DISPLAY_BLOCK)
                .add(ModBlocks.MITHRIL_ORE)
                .add(ModBlocks.DEEPSLATE_MITHRIL_ORE)
                .add(ModBlocks.MITHRIL_FENCE)
                .add(ModBlocks.MITHRIL_FENCE_GATE)
                .add(ModBlocks.MITHRIL_BUTTON)
                .add(ModBlocks.MITHRIL_WALL)
                .add(ModBlocks.MITHRIL_DOOR)
                .add(ModBlocks.MITHRIL_SLAB)
                .add(ModBlocks.MITHRIL_STAIRS)
                .add(ModBlocks.MITHRIL_TRAPDOOR)
                .add(ModBlocks.MITHRIL_PRESSURE_PLATE);
        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.SOUND_BLOCK);
        getOrCreateTagBuilder(BlockTags.FENCES)
                .add(ModBlocks.MITHRIL_FENCE);
        getOrCreateTagBuilder(BlockTags.FENCE_GATES)
                .add(ModBlocks.MITHRIL_FENCE_GATE);
        getOrCreateTagBuilder(BlockTags.WALLS)
                .add(ModBlocks.MITHRIL_WALL);

        getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.BLACKWOOD_LOG,ModBlocks.BLACKWOOD_WOOD,ModBlocks.STRIPPED_BLACKWOOD_LOG,ModBlocks.STRIPPED_BLACKWOOD_WOOD);
    }
}
