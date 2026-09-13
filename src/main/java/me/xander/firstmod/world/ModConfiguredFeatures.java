package me.xander.firstmod.world;

import me.xander.first_mod;
import me.xander.firstmod.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.CherryFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.List;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?,?>> BLACKWOOD_KEY = registryKey("blackwood");
    public static final RegistryKey<ConfiguredFeature<?,?>> MITHRIL_ORE_KEY = registryKey("mithril_ore");
    public static final RegistryKey<ConfiguredFeature<?,?>> NETHER_MITHRIL_ORE_KEY = registryKey("nether_mithril_ore");
    public static final RegistryKey<ConfiguredFeature<?,?>> MITHRIL_GEODE_KEY = registryKey("mithril_geode");
    public static void bootstrap(Registerable<ConfiguredFeature<?,?>> context) {
        register(context, BLACKWOOD_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(ModBlocks.BLACKWOOD_LOG),
                new StraightTrunkPlacer(5,6,3),
                BlockStateProvider.of(ModBlocks.BLACKWOOD_LEAVES),
                new CherryFoliagePlacer(ConstantIntProvider.create(4), ConstantIntProvider.create(1),ConstantIntProvider.create(5),
                        0.25f,0.5f,0.15f,0.05f),
                new TwoLayersFeatureSize(1,0,2)).build());
        RuleTest stoneReplacebles = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplacebles = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherReplacebles = new TagMatchRuleTest(BlockTags.BASE_STONE_NETHER);

        List<OreFeatureConfig.Target> overworldMithrilOres =
                List.of(OreFeatureConfig.createTarget(stoneReplacebles, ModBlocks.MITHRIL_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplacebles, ModBlocks.DEEPSLATE_MITHRIL_ORE.getDefaultState()));
        List<OreFeatureConfig.Target> netherMithrilOres =
                List.of(OreFeatureConfig.createTarget(netherReplacebles, ModBlocks.NETHER_MITHRIL_ORE.getDefaultState()));
        register(context, MITHRIL_ORE_KEY, Feature.SCATTERED_ORE, new OreFeatureConfig(overworldMithrilOres,2));
        register(context, NETHER_MITHRIL_ORE_KEY, Feature.ORE, new OreFeatureConfig(netherMithrilOres,9));
        register(context,MITHRIL_GEODE_KEY , Feature.GEODE,
                new GeodeFeatureConfig(new GeodeLayerConfig(BlockStateProvider.of(Blocks.AIR),
                        BlockStateProvider.of(ModBlocks.MITHRILIZED_STONE), BlockStateProvider.of(ModBlocks.MITHRIL_ORE),
                        BlockStateProvider.of(Blocks.CALCITE), BlockStateProvider.of(Blocks.SMOOTH_BASALT),
                        List.of(ModBlocks.MITHRIL_CLUSTER.getDefaultState(), ModBlocks.MITHRIL_CLUSTER.getDefaultState(),
                                ModBlocks.MITHRIL_CLUSTER.getDefaultState(), ModBlocks.MITHRIL_CLUSTER.getDefaultState()),
                        BlockTags.FEATURES_CANNOT_REPLACE, BlockTags.GEODE_INVALID_BLOCKS),
                        new GeodeLayerThicknessConfig(1.7, 2.2, 3.2, 4.2),
                        new GeodeCrackConfig(0.25, 2.0, 2), 0.35,
                        0.083, true, UniformIntProvider.create(4, 6), UniformIntProvider.create(3, 4),
                        UniformIntProvider.create(1, 2), -16, 16, 0.05, 1));
    }

        public static RegistryKey<ConfiguredFeature<?,?>> registryKey(String name) {
            return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(first_mod.MOD_ID, name));
        }

        private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?,?>> context,
                                                                                       RegistryKey<ConfiguredFeature<?,?>> key, F feature, FC configuration) {
                context.register(key, new ConfiguredFeature<>(feature,configuration));
        }

}
