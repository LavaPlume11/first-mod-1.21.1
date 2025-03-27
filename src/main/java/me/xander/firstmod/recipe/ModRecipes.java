package me.xander.firstmod.recipe;

import me.xander.first_mod;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
    public static final RecipeSerializer<CrystallizerRecipe> CRYSTALLIZER_SERIALIZER = Registry.register(
            Registries.RECIPE_SERIALIZER, Identifier.of(first_mod.MOD_ID, "crystallizing"), new CrystallizerRecipe.Serializer());
    public static final RecipeType<CrystallizerRecipe> CRYSTALLIZER_TYPE = Registry.register(
            Registries.RECIPE_TYPE, Identifier.of(first_mod.MOD_ID,"crystallizing"), new RecipeType<>() {
                @Override
                public String toString() {
                    return "crystallizing";
                }
            });
    public static final RecipeSerializer<CompressorRecipe> COMPRESSOR_SERIALIZER = Registry.register(
            Registries.RECIPE_SERIALIZER, Identifier.of(first_mod.MOD_ID, "compressing"), new CompressorRecipe.Serializer());
    public static final RecipeType<CompressorRecipe> COMPRESSOR_TYPE = Registry.register(
            Registries.RECIPE_TYPE, Identifier.of(first_mod.MOD_ID,"compressing"), new RecipeType<>() {
                @Override
                public String toString() {
                    return "compressing";
                }
            });

    public static final RecipeSerializer<MelterRecipe> MELTER_SERIALIZER = Registry.register(
            Registries.RECIPE_SERIALIZER, Identifier.of(first_mod.MOD_ID, "melting"), new MelterRecipe.Serializer());
    public static final RecipeType<MelterRecipe> MELTER_TYPE = Registry.register(
            Registries.RECIPE_TYPE, Identifier.of(first_mod.MOD_ID,"melting"), new RecipeType<>() {
                @Override
                public String toString() {
                    return "melting";
                }
            });



    public static void registerRecipes() {
        first_mod.LOGGER.info("Registering Custom Recipes for " + first_mod.MOD_ID);
    }
}
