package me.xander.firstmod.datagen;

import me.xander.firstmod.block.ModBlocks;
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
        blockStateModelGenerator.registerCooker(ModBlocks.CRYSTALLIZER, TexturedModel.ORIENTABLE);

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

        blockStateModelGenerator.registerDoor(ModBlocks.MITHRIL_DOOR);
        blockStateModelGenerator.registerTrapdoor(ModBlocks.MITHRIL_TRAPDOOR);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.BANANA, Models.GENERATED);
        itemModelGenerator.register(ModItems.BURNT_BANANA, Models.GENERATED);
        itemModelGenerator.register(ModItems.SPEAR, Models.HANDHELD);
        itemModelGenerator.register(ModItems.DAMAGED_MITHRIL_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.MITHRIL_SWORD_SHARD, Models.GENERATED);
        itemModelGenerator.register(ModItems.DYNAMITE, Models.GENERATED);
        itemModelGenerator.register(ModItems.MITHRIL, Models.GENERATED);
        itemModelGenerator.register(ModItems.MITHRIL_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.MITHRIL_PICKAXE, Models.HANDHELD);
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.MITHRIL_HELMET));
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.MITHRIL_CHESTPLATE));
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.MITHRIL_LEGGINGS));
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.MITHRIL_BOOTS));
        itemModelGenerator.register(ModItems.MITHRIL_TEMPLATE, Models.GENERATED);
        itemModelGenerator.register(ModItems.TRUE_BLADE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.RAW_MITHRIL, Models.GENERATED);
        itemModelGenerator.register(ModItems.MITHRIL_HORSE_ARMOR, Models.GENERATED);
        itemModelGenerator.register(ModItems.CATALYST, Models.GENERATED);
        itemModelGenerator.register(ModItems.LION_SPAWN_EGG,
                new Model(Optional.of(Identifier.of("item/template_spawn_egg")),Optional.empty()));


    }
}
