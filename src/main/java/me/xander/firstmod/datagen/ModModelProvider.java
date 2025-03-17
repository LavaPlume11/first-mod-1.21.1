package me.xander.firstmod.datagen;

import me.xander.firstmod.block.ModBlocks;
import me.xander.firstmod.block.custom.BananaBushBlock;
import me.xander.firstmod.fluid.ModFluids;
import me.xander.firstmod.item.custom.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.item.ArmorItem;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RUBY_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RUBY_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SOUND_BLOCK);
        BlockStateModelGenerator.BlockTexturePool mithrilPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.MITHRIL_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.MITHRIL_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.BLACKWOOD_PLANKS);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.DEEPSLATE_MITHRIL_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.NETHER_MITHRIL_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.MITHRIL_DISPLAY_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.ECHO_GENERATOR);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.TANK);

        blockStateModelGenerator.registerCooker(ModBlocks.CRYSTALLIZER, TexturedModel.ORIENTABLE);
        blockStateModelGenerator.registerCooker(ModBlocks.COMPRESSOR, TexturedModel.ORIENTABLE);
        


        mithrilPool.stairs(ModBlocks.MITHRIL_STAIRS);
        mithrilPool.slab(ModBlocks.MITHRIL_SLAB);
        mithrilPool.wall(ModBlocks.MITHRIL_WALL);
        mithrilPool.fence(ModBlocks.MITHRIL_FENCE);
        mithrilPool.button(ModBlocks.MITHRIL_BUTTON);
        mithrilPool.pressurePlate(ModBlocks.MITHRIL_PRESSURE_PLATE);
        mithrilPool.fenceGate(ModBlocks.MITHRIL_FENCE_GATE);

        blockStateModelGenerator.registerLog(ModBlocks.BLACKWOOD_LOG).log(ModBlocks.BLACKWOOD_LOG).wood(ModBlocks.BLACKWOOD_WOOD);
        blockStateModelGenerator.registerLog(ModBlocks.STRIPPED_BLACKWOOD_LOG).log(ModBlocks.STRIPPED_BLACKWOOD_LOG).wood(ModBlocks.STRIPPED_BLACKWOOD_WOOD);

        blockStateModelGenerator.registerTintableCrossBlockState(ModBlocks.BLACKWOOD_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);

        blockStateModelGenerator.registerSingleton(ModBlocks.BLACKWOOD_LEAVES, TexturedModel.LEAVES);
        blockStateModelGenerator.registerSingleton(ModBlocks.BANANA_LEAVES, TexturedModel.LEAVES);

        blockStateModelGenerator.registerCrop(ModBlocks.BANANA_BUSH, BananaBushBlock.AGE,0,1,2,3,4,5);

        blockStateModelGenerator.registerDoor(ModBlocks.MITHRIL_DOOR);
        blockStateModelGenerator.registerTrapdoor(ModBlocks.MITHRIL_TRAPDOOR);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.BURNT_BANANA, Models.GENERATED);
        itemModelGenerator.register(ModItems.SPEAR, Models.HANDHELD);
        itemModelGenerator.register(ModItems.RE_DEAD_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.GUITAR, Models.HANDHELD);
        itemModelGenerator.register(ModItems.WHIP, Models.HANDHELD);
        itemModelGenerator.register(ModItems.DAMAGED_MITHRIL_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.REFINED_MITHRIL_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.MITHRIL_SWORD_SHARD, Models.GENERATED);
        itemModelGenerator.register(ModItems.IRON_WARTURTLE_ARMOR, Models.GENERATED);
        itemModelGenerator.register(ModItems.GOLD_WARTURTLE_ARMOR, Models.GENERATED);
        itemModelGenerator.register(ModItems.DIAMOND_WARTURTLE_ARMOR, Models.GENERATED);
        itemModelGenerator.register(ModItems.NETHERITE_WARTURTLE_ARMOR, Models.GENERATED);
        itemModelGenerator.register(ModItems.MITHRIL_WARTURTLE_ARMOR, Models.GENERATED);
        itemModelGenerator.register(ModItems.DYNAMITE, Models.GENERATED);
        itemModelGenerator.register(ModItems.MITHRIL, Models.GENERATED);
        itemModelGenerator.register(ModItems.MITHRIL_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModFluids.MITHRIL_WATER_BUCKET, Models.GENERATED);
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.MITHRIL_HELMET));
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.MITHRIL_CHESTPLATE));
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.MITHRIL_LEGGINGS));
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.MITHRIL_BOOTS));
        itemModelGenerator.register(ModItems.MITHRIL_TEMPLATE, Models.GENERATED);
        itemModelGenerator.register(ModItems.TRUE_BLADE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.LIGHT_KNIFE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.RAW_MITHRIL, Models.GENERATED);
        itemModelGenerator.register(ModItems.MITHRIL_HORSE_ARMOR, Models.GENERATED);
        itemModelGenerator.register(ModItems.CATALYST, Models.GENERATED);
        itemModelGenerator.register(ModItems.XMAS_STICK, Models.HANDHELD);
        itemModelGenerator.register(ModItems.DICE, Models.GENERATED);
        itemModelGenerator.register(ModItems.LION_SPAWN_EGG,
                new Model(Optional.of(Identifier.of("item/template_spawn_egg")),Optional.empty()));
        itemModelGenerator.register(ModItems.LEMMING_SPAWN_EGG,
                new Model(Optional.of(Identifier.of("item/template_spawn_egg")),Optional.empty()));
        itemModelGenerator.register(ModItems.WHISPERER_SPAWN_EGG,
                new Model(Optional.of(Identifier.of("item/template_spawn_egg")),Optional.empty()));
        itemModelGenerator.register(ModItems.WARTURTLE_SPAWN_EGG,
                new Model(Optional.of(Identifier.of("item/template_spawn_egg")),Optional.empty()));


    }

}
