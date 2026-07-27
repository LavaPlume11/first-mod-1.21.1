package me.xander.firstmod.datagen;

import me.xander.firstmod.block.ModBlocks;
import me.xander.firstmod.item.custom.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public static final List<ItemConvertible> BANANA_SMELTABLE = List.of(ModItems.BANANA);
    public static final List<ItemConvertible> MITHRIL_SMELTABLE = List.of(ModItems.RAW_MITHRIL);
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);

    }

    @Override

    public void generate(RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.SPEAR)
                .pattern(" CO")
                .pattern(" SC")
                .pattern("S  ")
                .input('S', Items.STICK)
                .input('C', Items.COBBLESTONE)
                .input('O', ModBlocks.SOUND_BLOCK)
                .criterion(hasItem(Items.COBBLESTONE), conditionsFromItem(Items.COBBLESTONE))
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.SPEAR)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.SOUND_BLOCK)
                .pattern("CCC")
                .pattern("CSC")
                .pattern("CCC")
                .input('S', Items.NOTE_BLOCK)
                .input('C', Items.QUARTZ_BLOCK)
                .criterion(hasItem(Items.NOTE_BLOCK), conditionsFromItem(Items.NOTE_BLOCK))
                .offerTo(exporter, Identifier.of(getRecipeName(ModBlocks.SOUND_BLOCK)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.POWER_CELL)
                .pattern("CNC")
                .pattern("CSC")
                .pattern("CCC")
                .input('S', ModItems.POWER_GEM)
                .input('C', Items.GLASS_PANE)
                .input('N', Items.NETHER_STAR)
                .criterion(hasItem(ModItems.POWER_GEM), conditionsFromItem(ModItems.POWER_GEM))
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.POWER_CELL)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TRANSPORTATION, ModItems.ICARUS_WINGS)
                .pattern("SIS")
                .pattern("SBS")
                .pattern("SIS")
                .input('B', Items.IRON_BLOCK)
                .input('I', Items.IRON_INGOT)
                .input('S', ModItems.STICKY_FEATHER)
                .criterion(hasItem(ModItems.STICKY_FEATHER), conditionsFromItem(ModItems.STICKY_FEATHER))
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.ICARUS_WINGS)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.TRAP_REMOTE)
                .pattern("SSI")
                .pattern("SMS")
                .pattern("ISS")
                .input('M', Items.BREEZE_ROD)
                .input('I', Items.REDSTONE)
                .input('S', Items.IRON_INGOT)
                .criterion(hasItem(Items.BREEZE_ROD), conditionsFromItem(Items.BREEZE_ROD))
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.TRAP_REMOTE)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLOCK_OF_THE_SEA)
                .pattern(" P ")
                .pattern("PHP")
                .pattern(" P ")
                .input('P', Items.PRISMARINE_SHARD)
                .input('H', Items.HEART_OF_THE_SEA)
                .criterion(hasItem(Items.HEART_OF_THE_SEA), conditionsFromItem(Items.HEART_OF_THE_SEA))
                .offerTo(exporter, Identifier.of(getRecipeName(ModBlocks.BLOCK_OF_THE_SEA)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModBlocks.BASIC_COMPUTER)
                .pattern("CNC")
                .pattern("CSC")
                .pattern("CCC")
                .input('S', Items.REDSTONE)
                .input('C', Items.TINTED_GLASS)
                .input('N', Items.COPPER_INGOT)
                .criterion(hasItem(Items.REDSTONE), conditionsFromItem(Items.REDSTONE))
                .offerTo(exporter, Identifier.of(getRecipeName(ModBlocks.BASIC_COMPUTER)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.COMPRESSOR)
                .pattern("CNC")
                .pattern("CSC")
                .pattern("CCC")
                .input('S', Items.COMPARATOR)
                .input('C', ModBlocks.MITHRIL_BLOCK)
                .input('N', ModBlocks.MITHRIL_DISPLAY_BLOCK)
                .criterion(hasItem(Items.COMPARATOR), conditionsFromItem(Items.COMPARATOR))
                .offerTo(exporter, Identifier.of(getRecipeName(ModBlocks.COMPRESSOR)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.MELTER)
                .pattern("CNC")
                .pattern("CSC")
                .pattern("CNC")
                .input('S', Items.MAGMA_BLOCK)
                .input('C', ModBlocks.MITHRIL_BLOCK)
                .input('N', Blocks.GLASS)
                .criterion(hasItem(ModBlocks.MITHRIL_BLOCK), conditionsFromItem(ModBlocks.MITHRIL_BLOCK))
                .offerTo(exporter, Identifier.of(getRecipeName(ModBlocks.MELTER)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.POWER_AMPLIFIER)
                .pattern("CCC")
                .pattern("CSC")
                .pattern("CCC")
                .input('S', Items.NETHER_STAR)
                .input('C', Blocks.OXIDIZED_COPPER)
                .criterion(hasItem(Items.NETHER_STAR), conditionsFromItem(Items.NETHER_STAR))
                .offerTo(exporter, Identifier.of(getRecipeName(ModBlocks.POWER_AMPLIFIER)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.STEAM_VENT)
                .pattern(" C ")
                .pattern("CSC")
                .pattern(" C ")
                .input('S', Items.REDSTONE)
                .input('C', Blocks.IRON_BARS)
                .criterion(hasItem(Items.REDSTONE), conditionsFromItem(Items.REDSTONE))
                .offerTo(exporter, Identifier.of(getRecipeName(ModBlocks.STEAM_VENT)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.STONE_OF_SWORD)
                .pattern(" C ")
                .pattern("CSC")
                .pattern("CCC")
                .input('S', Items.REDSTONE)
                .input('C', Blocks.STONE)
                .criterion(hasItem(Items.REDSTONE), conditionsFromItem(Items.REDSTONE))
                .offerTo(exporter, Identifier.of(getRecipeName(ModBlocks.STONE_OF_SWORD)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.TANK)
                .pattern("CCC")
                .pattern("CSC")
                .pattern("CCC")
                .input('S', Items.BUCKET)
                .input('C', Blocks.GLASS)
                .criterion(hasItem(Items.BUCKET), conditionsFromItem(Items.BUCKET))
                .offerTo(exporter, Identifier.of(getRecipeName(ModBlocks.TANK)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.DARK_PORTAL_SETTER)
                .pattern(" SI")
                .pattern("SMS")
                .pattern("IS ")
                .input('M', Items.BLAZE_ROD)
                .input('I', Blocks.SCULK_VEIN)
                .input('S', Items.GOLD_INGOT)
                .criterion(hasItem(Items.BLAZE_ROD), conditionsFromItem(Items.BLAZE_ROD))
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.DARK_PORTAL_SETTER)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.GUITAR)
                .pattern(" SI")
                .pattern("SIS")
                .pattern("IS ")
                .input('I', Blocks.RED_WOOL)
                .input('S', Items.STRING)
                .criterion(hasItem(Items.STRING), conditionsFromItem(Items.STRING))
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.GUITAR)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.LIGHT_KNIFE)
                .pattern(" I ")
                .pattern(" I ")
                .pattern(" S ")
                .input('I', Blocks.LIGHTNING_ROD)
                .input('S', Items.STICK)
                .criterion(hasItem(Blocks.LIGHTNING_ROD), conditionsFromItem(Blocks.LIGHTNING_ROD))
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.LIGHT_KNIFE)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.NETHER_BOW)
                .pattern(" SE")
                .pattern("S E")
                .pattern(" SE")
                .input('E', Items.TWISTING_VINES)
                .input('S', Items.BLAZE_ROD)
                .criterion(hasItem(Items.BLAZE_ROD), conditionsFromItem(Items.BLAZE_ROD))
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.NETHER_BOW)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.BIG_SWORD)
                .pattern("  E")
                .pattern(" ER")
                .pattern("S  ")
                .input('E', Items.RED_NETHER_BRICKS)
                .input('R', Items.GOLD_INGOT)
                .input('S', Items.BLAZE_ROD)
                .criterion(hasItem(Items.BLAZE_ROD), conditionsFromItem(Items.BLAZE_ROD))
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.BIG_SWORD)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.POCKET_STORAGE)
                .pattern("GGG")
                .pattern("RER")
                .pattern("SSS")
                .input('E', Items.ENDER_CHEST)
                .input('R', Items.SHULKER_SHELL)
                .input('S', Items.ENDER_EYE)
                .input('G', Items.END_CRYSTAL)
                .criterion(hasItem(Items.ENDER_CHEST), conditionsFromItem(Items.ENDER_CHEST))
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.POCKET_STORAGE)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.RE_DEAD_SWORD)
                .pattern("GEG")
                .pattern("GEG")
                .pattern("GSG")
                .input('E', Items.ROTTEN_FLESH)
                .input('S', Items.STICK)
                .input('G', Items.GOLD_INGOT)
                .criterion(hasItem(Items.ROTTEN_FLESH), conditionsFromItem(Items.ROTTEN_FLESH))
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.RE_DEAD_SWORD)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModItems.THANK_HAT)
                .pattern("GEG")
                .pattern("G G")
                .pattern("   ")
                .input('E', Items.EGG)
                .input('G', Items.LEATHER)
                .criterion(hasItem(Items.EGG), conditionsFromItem(Items.EGG))
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.THANK_HAT)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModItems.XMAS_HAT)
                .pattern("GEG")
                .pattern("G G")
                .pattern("   ")
                .input('E', ModItems.XMAS_STICK)
                .input('G', Items.RED_WOOL)
                .criterion(hasItem(ModItems.XMAS_STICK), conditionsFromItem(ModItems.XMAS_STICK))
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.XMAS_HAT)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.WARDEN_PENDENT)
                .pattern(" G ")
                .pattern("GEG")
                .pattern(" G ")
                .input('E', Blocks.SCULK_CATALYST)
                .input('G', Items.ECHO_SHARD)
                .criterion(hasItem(Items.ECHO_SHARD), conditionsFromItem(Items.ECHO_SHARD))
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.WARDEN_PENDENT)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.DIAMOND_WARDEN_PENDENT)
                .pattern("ABC")
                .pattern("DEF")
                .pattern("GHI")
                .input('A', Items.DIAMOND)
                .input('B', Items.IRON_INGOT)
                .input('C', Items.EMERALD)
                .input('D', Items.ENDER_EYE)
                .input('E', ModItems.WARDEN_PENDENT)
                .input('F', Items.END_CRYSTAL)
                .input('G', Items.GOLD_INGOT)
                .input('H', Items.REDSTONE)
                .input('I', Items.NETHERITE_INGOT)
                .criterion(hasItem(ModItems.WARDEN_PENDENT), conditionsFromItem(ModItems.WARDEN_PENDENT))
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.DIAMOND_WARDEN_PENDENT)));


        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.CATALYST)
                .pattern(" G ")
                .pattern("GEG")
                .pattern(" G ")
                .input('E', Items.GLOWSTONE_DUST)
                .input('G', Items.AMETHYST_SHARD)
                .criterion(hasItem(Items.AMETHYST_SHARD), conditionsFromItem(Items.AMETHYST_SHARD))
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.CATALYST)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.CLONE_CREATOR)
                .pattern(" C ")
                .pattern("FEF")
                .pattern(" G ")
                .input('E', Items.GLOWSTONE_DUST)
                .input('G', Items.CRYING_OBSIDIAN)
                .input('C', Items.TINTED_GLASS)
                .input('F', Items.IRON_INGOT)
                .criterion(hasItem(Items.CRYING_OBSIDIAN), conditionsFromItem(Items.CRYING_OBSIDIAN))
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.CLONE_CREATOR)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.TYRINITE)
                .pattern(" G ")
                .pattern("GEG")
                .pattern(" G ")
                .input('E', Items.DIAMOND)
                .input('G', Items.EMERALD)
                .criterion(hasItem(Items.DIAMOND), conditionsFromItem(Items.DIAMOND))
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.TYRINITE)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.TYRINITE_GEM)
                .pattern(" G ")
                .pattern("GGG")
                .pattern(" G ")
                .input('G', ModItems.TYRINITE)
                .criterion(hasItem(ModItems.TYRINITE), conditionsFromItem(ModItems.TYRINITE))
                .offerTo(exporter, Identifier.of(getRecipeName(ModBlocks.TYRINITE_GEM)));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.MITHRIL_SWORD).input(ModItems.MITHRIL_SWORD_SHARD).input(ModItems.MITHRIL_SWORD_SHARD).input(ModItems.MITHRIL_SWORD_SHARD).input(ModItems.DAMAGED_MITHRIL_SWORD)
                .criterion("has_shard", conditionsFromItem(ModItems.MITHRIL_SWORD_SHARD)).offerTo(exporter, Identifier.of(getRecipeName(ModItems.MITHRIL_SWORD)));
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.STICKY_FEATHER).input(Items.FEATHER).input(Items.HONEYCOMB).input(Items.HONEYCOMB).input(Items.HONEYCOMB)
                .criterion("has_feather", conditionsFromItem(Items.FEATHER)).offerTo(exporter, Identifier.of(getRecipeName(ModItems.STICKY_FEATHER)));
        ShapelessRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.REFINED_MITHRIL_SWORD).input(ModItems.MITHRIL_SWORD).input(ModItems.POWER_GEM)
                .criterion("has_mithril_sword", conditionsFromItem(ModItems.MITHRIL_SWORD)).offerTo(exporter, Identifier.of(getRecipeName(ModItems.REFINED_MITHRIL_SWORD)));
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.TYRINITE_STEW).input(ModItems.TYRINITE).input(ModItems.TYRINITE).input(Items.BOWL).input(Items.OXEYE_DAISY)
                .criterion("has_tyrinite", conditionsFromItem(ModItems.TYRINITE)).offerTo(exporter, Identifier.of(getRecipeName(ModItems.TYRINITE_STEW)));
        offerBlasting(exporter, BANANA_SMELTABLE,RecipeCategory.MISC, ModItems.BURNT_BANANA,
                0.7f, 1000, "banana");
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.MITHRIL, RecipeCategory.DECORATIONS,
                ModBlocks.MITHRIL_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.GEMS, RecipeCategory.MISC,
                ModItems.GEM_CLUSTER);
        offerBlasting(exporter,MITHRIL_SMELTABLE,RecipeCategory.MISC, ModItems.MITHRIL,
                1.2f, 100, "mithril");
        offerSmelting(exporter, MITHRIL_SMELTABLE,RecipeCategory.MISC, ModItems.MITHRIL,
                1.7f, 500, "mithril");
        offerShapelessRecipe(exporter, ModItems.DRAGON_SCALE, Items.DRAGON_EGG, "dragon", 16);

        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModBlocks.CORRUPTION_VINES, RecipeCategory.MISC,
                ModBlocks.CORRUPTION_BLOCK);

        createStairsRecipe(ModBlocks.MITHRIL_STAIRS, Ingredient.ofItems(ModBlocks.MITHRIL_BLOCK));
        createDoorRecipe(ModBlocks.MITHRIL_DOOR, Ingredient.ofItems(ModBlocks.MITHRIL_BLOCK));
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModBlocks.MITHRIL_DOOR, 3).input('#', ModBlocks.MITHRIL_BLOCK).pattern("##").pattern("##").pattern("##");
        createFenceRecipe(ModBlocks.MITHRIL_FENCE, Ingredient.ofItems(ModBlocks.MITHRIL_BLOCK));
        createFenceGateRecipe(ModBlocks.MITHRIL_FENCE_GATE, Ingredient.ofItems(ModBlocks.MITHRIL_BLOCK));
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS,ModBlocks.MITHRIL_SLAB, Ingredient.ofItems(ModBlocks.MITHRIL_BLOCK));
        createTrapdoorRecipe(ModBlocks.MITHRIL_TRAPDOOR, Ingredient.ofItems(ModBlocks.MITHRIL_BLOCK));
        createPressurePlateRecipe(RecipeCategory.BUILDING_BLOCKS ,ModBlocks.MITHRIL_PRESSURE_PLATE, Ingredient.ofItems(ModBlocks.MITHRIL_BLOCK));
        offerWallRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.MITHRIL_STAIRS, ModBlocks.MITHRIL_BLOCK);
        offerShapelessRecipe(exporter, ModBlocks.MITHRIL_BUTTON, ModBlocks.MITHRIL_BLOCK, "mithril", 1);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.MITHRIL_DISPLAY_BLOCK)
                .pattern(" O ")
                .pattern("CSC")
                .pattern(" C ")
                .input('S', ModBlocks.MITHRIL_BLOCK)
                .input('C', Items.REDSTONE)
                .input('O', Items.GLASS_PANE)
                .criterion(hasItem(ModBlocks.MITHRIL_BLOCK), conditionsFromItem(ModBlocks.MITHRIL_BLOCK))
                .offerTo(exporter, Identifier.of(getRecipeName(ModBlocks.MITHRIL_DISPLAY_BLOCK)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.LIGHTNING_HELMET)
                .pattern("CLC")
                .pattern("C C")
                .pattern("   ")
                .input('C', Items.COPPER_INGOT)
                .input('L', Blocks.LIGHTNING_ROD)
            .criterion(hasItem(Blocks.LIGHTNING_ROD), conditionsFromItem(Blocks.LIGHTNING_ROD))
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.LIGHTNING_HELMET)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.POWER_GEM)
                .pattern("OOO")
                .pattern("OOO")
                .pattern("OOO")
                .input('O', ModItems.GEM_CLUSTER)
                .criterion(hasItem(ModItems.GEM_CLUSTER), conditionsFromItem(ModItems.GEM_CLUSTER))
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.POWER_GEM)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRYSTALLIZER)
                .pattern("COC")
                .pattern("ESE")
                .pattern("CYC")
                .input('S', Items.AMETHYST_SHARD)
                .input('C', Blocks.COBBLESTONE)
                .input('E',Items.ECHO_SHARD)
                .input('O', Items.IRON_INGOT)
                .input('Y',Items.END_CRYSTAL)
                .criterion(hasItem(ModItems.RAW_MITHRIL), conditionsFromItem(ModItems.RAW_MITHRIL))
                .offerTo(exporter, Identifier.of(getRecipeName(ModBlocks.CRYSTALLIZER)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.MITHRIL_HORSE_ARMOR)
                .pattern("  C")
                .pattern("CCC")
                .pattern("C C")
                .input('C', ModItems.MITHRIL_SWORD_SHARD)
                .criterion(hasItem(ModItems.MITHRIL_SWORD_SHARD), conditionsFromItem(ModItems.MITHRIL_SWORD_SHARD))
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.MITHRIL_HORSE_ARMOR)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TRANSPORTATION, ModItems.DRAGONSCALE_WINGS)
                .pattern("OOO")
                .pattern("OEO")
                .pattern("OOO")
                .input('O', ModItems.DRAGON_SCALE)
                .input('E', Items.ELYTRA)
                .criterion(hasItem(ModItems.DRAGON_SCALE), conditionsFromItem(ModItems.DRAGON_SCALE))
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.DRAGONSCALE_WINGS)));

    }
}
