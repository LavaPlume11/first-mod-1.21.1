package me.xander.firstmod.block;

import me.xander.first_mod;
import me.xander.firstmod.block.custom.*;
import me.xander.firstmod.world.tree.ModSaplingGenerators;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block RUBY_BLOCK = registerBlock("ruby_block",
            new Block(AbstractBlock.Settings.create()
                    .mapColor(MapColor.RED)
                    .instrument(NoteBlockInstrument.BIT)
                    .requiresTool()
                    .strength(5.0F, 5.0F)
                    .sounds(BlockSoundGroup.METAL)));
    public static final Block MITHRIL_BLOCK = registerBlock("mithril_block",
            new Block(AbstractBlock.Settings.create()
                    .mapColor(MapColor.WHITE_GRAY)
                    .instrument(NoteBlockInstrument.BIT)
                    .requiresTool()
                    .strength(7.0f, 5.0f)
                    .sounds(BlockSoundGroup.METAL)));
    public static final Block SOUND_BLOCK = registerBlock("sound_block",
            new SoundBlock(AbstractBlock.Settings.copy(Blocks.NOTE_BLOCK)));

    public static final Block MITHRIL_STAIRS = registerBlock("mithril_stairs",
            new StairsBlock(ModBlocks.MITHRIL_BLOCK.getDefaultState(), AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)));
    public static final Block MITHRIL_SLAB = registerBlock("mithril_slab",
            new SlabBlock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)));

    public static final Block MITHRIL_BUTTON = registerBlock("mithril_button",
            new ButtonBlock( BlockSetType.IRON, 10, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)));
    public static final Block MITHRIL_PRESSURE_PLATE = registerBlock("mithril_pressure_plate",
            new PressurePlateBlock(BlockSetType.IRON, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)));

    public static final Block MITHRIL_FENCE = registerBlock("mithril_fence",
            new FenceBlock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)));
    public static final Block MITHRIL_FENCE_GATE = registerBlock("mithril_fence_gate",
            new FenceGateBlock(WoodType.ACACIA, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)));
    public static final Block MITHRIL_WALL = registerBlock("mithril_wall",
            new WallBlock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)));

    public static final Block MITHRIL_DOOR = registerBlock("mithril_door",
            new DoorBlock(BlockSetType.IRON,AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)));
    public static final Block MITHRIL_TRAPDOOR = registerBlock("mithril_trapdoor",
            new TrapdoorBlock(BlockSetType.IRON, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)));

    public static final Block RUBY_ORE = registerBlock("ruby_ore",
            new Block(AbstractBlock.Settings.create()
                    .mapColor(MapColor.RED)
                    .instrument(NoteBlockInstrument.BIT)
                    .requiresTool()
                    .strength(2.5F, 5.0F)
                    .sounds(BlockSoundGroup.STONE)));
    public static final Block MITHRIL_ORE = registerBlock("mithril_ore",
            new Block(AbstractBlock.Settings.create()
                    .mapColor(MapColor.GRAY)
                    .instrument(NoteBlockInstrument.DIDGERIDOO)
                    .requiresTool()
                    .strength(6.0F, 5.0F)
                    .sounds(BlockSoundGroup.STONE)));
    public static final Block NETHER_MITHRIL_ORE = registerBlock("nether_mithril_ore",
            new Block(AbstractBlock.Settings.create()
                    .mapColor(MapColor.RED)
                    .instrument(NoteBlockInstrument.PIGLIN)
                    .requiresTool()
                    .strength(2.0F, 4.0F)
                    .sounds(BlockSoundGroup.NETHERRACK)));
    public static final Block DEEPSLATE_MITHRIL_ORE = registerBlock("deepslate_mithril_ore",
            new Block(AbstractBlock.Settings.create()
                    .mapColor(MapColor.GRAY)
                    .instrument(NoteBlockInstrument.BIT)
                    .requiresTool()
                    .strength(7.0F, 7.0F)
                    .sounds(BlockSoundGroup.DEEPSLATE)));
    public static final Block MITHRIL_DISPLAY_BLOCK = registerBlock("mithril_display_block",
            new DisplayBlock(AbstractBlock.Settings.copy(ModBlocks.MITHRIL_BLOCK).strength(7f).nonOpaque()));
    public static final Block BLACKWOOD_LOG = registerBlock("blackwood_log",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_LOG).strength(3f)));
    public static final Block BLACKWOOD_WOOD = registerBlock("blackwood_wood",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_WOOD).strength(3f)));
    public static final Block STRIPPED_BLACKWOOD_LOG = registerBlock("stripped_blackwood_log",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_LOG).strength(3f)));
    public static final Block STRIPPED_BLACKWOOD_WOOD = registerBlock("stripped_blackwood_wood",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_WOOD).strength(3f)));

    public static final Block BLACKWOOD_PLANKS = registerBlock("blackwood_planks",
            new Block(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).strength(3f)));
    public static final Block BLACKWOOD_LEAVES = registerBlock("blackwood_leaves",
            new LeavesBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES)));
    public static final Block BANANA_LEAVES = registerBlock("banana_leaves",
            new LeavesBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES)));
    public static final Block STONE_OF_SWORD = registerBlock("stone_of_sword",
            new StoneOfSwordBlock(AbstractBlock.Settings.copy(Blocks.STONE).nonOpaque()));

    public static final Block BLACKWOOD_SAPLING = registerBlock("blackwood_sapling",
            new SaplingBlock(ModSaplingGenerators.BLACKWOOD,AbstractBlock.Settings.copy(Blocks.OAK_SAPLING).strength(3f)));
    public static final Block CRYSTALLIZER = registerBlock("crystallizer",
            new CrystallizerBlock(AbstractBlock.Settings.create().strength(2f).requiresTool()));
    public static final Block COMPRESSOR = registerBlock("compressor",
            new CompressorBlock(AbstractBlock.Settings.create().strength(2f).requiresTool()));
    public static final Block ECHO_GENERATOR = registerBlock("echo_generator",
            new EchoGeneratorBlock(AbstractBlock.Settings.create().strength(2f).requiresTool()));
    public static final Block TANK = registerBlock("tank",
            new TankBlock(AbstractBlock.Settings.create().strength(2f).requiresTool().nonOpaque()));
    public static final Block BANANA_BUSH = registerBlockWithoutBlockItem("banana_bush",
            new BananaBushBlock(AbstractBlock.Settings.copy(Blocks.WHEAT)));



    private static Block registerBlockWithoutBlockItem(String name, Block block) {
        return Registry.register(Registries.BLOCK, Identifier.of(first_mod.MOD_ID, name), block);
    }

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(first_mod.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(first_mod.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks() {
        first_mod.LOGGER.info("Registering ModBlocks for " + first_mod.MOD_ID);
    }
}