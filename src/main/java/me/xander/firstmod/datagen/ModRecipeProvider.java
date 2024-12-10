package me.xander.firstmod.datagen;

import me.xander.firstmod.block.ModBlocks;
import me.xander.firstmod.item.custom.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
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
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.TRUE_BLADE)
                .pattern("ENM")
                .pattern("NSN")
                .pattern("GNE")
                .input('S', ModItems.MITHRIL_SWORD)
                .input('N', Items.NETHER_STAR)
                .input('M', ModItems.MITHRIL_SWORD_SHARD)
                .input('G', Items.REDSTONE_BLOCK)
                .input('E', Items.ECHO_SHARD)
                .criterion(hasItem(Items.NETHER_STAR), conditionsFromItem(Items.NETHER_STAR))
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.TRUE_BLADE)));
        ShapelessRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.MITHRIL_SWORD).input(ModItems.MITHRIL_SWORD_SHARD).input(ModItems.MITHRIL_SWORD_SHARD).input(ModItems.DAMAGED_MITHRIL_SWORD)
                        .criterion("has_shard", conditionsFromItem(ModItems.MITHRIL_SWORD_SHARD)).offerTo(exporter, Identifier.of(getRecipeName(ModItems.MITHRIL_SWORD)));

        offerBlasting(exporter, BANANA_SMELTABLE,RecipeCategory.MISC, ModItems.BURNT_BANANA,
                0.7f, 1000, "banana");
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.MITHRIL, RecipeCategory.DECORATIONS,
                ModBlocks.MITHRIL_BLOCK);
        offerBlasting(exporter,MITHRIL_SMELTABLE,RecipeCategory.MISC, ModItems.MITHRIL,
                1.2f, 100, "mithril");
        offerSmelting(exporter, MITHRIL_SMELTABLE,RecipeCategory.MISC, ModItems.MITHRIL,
                1.7f, 500, "mithril");
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.MITHRIL_DISPLAY_BLOCK)
                .pattern(" O ")
                .pattern("CSC")
                .pattern(" C ")
                .input('S', ModBlocks.MITHRIL_BLOCK)
                .input('C', Items.REDSTONE)
                .input('O', Items.GLASS_PANE)
                .criterion(hasItem(ModBlocks.MITHRIL_BLOCK), conditionsFromItem(ModBlocks.MITHRIL_BLOCK))
                .offerTo(exporter, Identifier.of(getRecipeName(ModBlocks.MITHRIL_DISPLAY_BLOCK)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRYSTALLIZER)
                .pattern("COC")
                .pattern("CSC")
                .pattern("COC")
                .input('S', Items.AMETHYST_SHARD)
                .input('C', Blocks.COBBLESTONE)
                .input('O', Items.IRON_INGOT)
                .criterion(hasItem(ModItems.RAW_MITHRIL), conditionsFromItem(ModItems.RAW_MITHRIL))
                .offerTo(exporter, Identifier.of(getRecipeName(ModBlocks.CRYSTALLIZER)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.MITHRIL_HORSE_ARMOR)
                .pattern("  C")
                .pattern("CCC")
                .pattern("C C")
                .input('C', ModItems.MITHRIL_SWORD_SHARD)
                .criterion(hasItem(ModItems.MITHRIL_SWORD_SHARD), conditionsFromItem(ModItems.MITHRIL_SWORD_SHARD))
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.MITHRIL_HORSE_ARMOR)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.TOMAHAWK)
                .pattern("CCC")
                .pattern("CSC")
                .pattern(" S ")
                .input('S', Items.STICK)
                .input('C', Items.IRON_INGOT)

                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.TOMAHAWK)));

    }
}
