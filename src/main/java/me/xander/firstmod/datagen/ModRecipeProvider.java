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

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.POWER_CELL)
                .pattern("CNC")
                .pattern("CSC")
                .pattern("CCC")
                .input('S', ModItems.POWER_GEM)
                .input('C', Items.GLASS_PANE)
                .input('N', Items.NETHER_STAR)
                .criterion(hasItem(ModItems.POWER_GEM), conditionsFromItem(ModItems.POWER_GEM))
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.POWER_CELL)));


        ShapelessRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.MITHRIL_SWORD).input(ModItems.MITHRIL_SWORD_SHARD).input(ModItems.MITHRIL_SWORD_SHARD).input(ModItems.MITHRIL_SWORD_SHARD).input(ModItems.DAMAGED_MITHRIL_SWORD)
                        .criterion("has_shard", conditionsFromItem(ModItems.MITHRIL_SWORD_SHARD)).offerTo(exporter, Identifier.of(getRecipeName(ModItems.MITHRIL_SWORD)));
        ShapelessRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.REFINED_MITHRIL_SWORD).input(ModItems.MITHRIL_SWORD).input(ModItems.POWER_GEM)
                .criterion("has_mithril_sword", conditionsFromItem(ModItems.MITHRIL_SWORD)).offerTo(exporter, Identifier.of(getRecipeName(ModItems.REFINED_MITHRIL_SWORD)));
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
                .pattern("COC")
                .pattern("C C")
                .pattern("   ")
                .input('C', Items.COPPER_INGOT)
                .input('O', Blocks.LIGHTNING_ROD)
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
