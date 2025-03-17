package me.xander.firstmod.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public record CompressorRecipe(Ingredient ingredient, Ingredient secondaryIngredient, ItemStack output) implements Recipe<CompressorRecipeInput> {
    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.of();
        list.add(this.ingredient);
        list.add(this.secondaryIngredient);
        return list;
    }

    @Override
    public boolean matches(CompressorRecipeInput input, World world) {
        if (world.isClient())
            return false;
        return ingredient.test(input.getStackInSlot(0)) && secondaryIngredient.test(input.getStackInSlot(1));
    }

    @Override
    public ItemStack craft(CompressorRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return output.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.COMPRESSOR_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.COMPRESSOR_TYPE;
    }

    public static class Serializer implements RecipeSerializer<CompressorRecipe> {
        public static final MapCodec<CompressorRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient").forGetter(CompressorRecipe::ingredient),
                Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("secondary_ingredient").forGetter(CompressorRecipe::secondaryIngredient),
                ItemStack.CODEC.fieldOf("result").forGetter(CompressorRecipe::output)
        ).apply(inst, CompressorRecipe::new));
        public static final PacketCodec<RegistryByteBuf, CompressorRecipe> STREAM_CODEC =
                PacketCodec.tuple(
                        Ingredient.PACKET_CODEC, CompressorRecipe::ingredient,
                        Ingredient.PACKET_CODEC, CompressorRecipe::secondaryIngredient,
                        ItemStack.PACKET_CODEC, CompressorRecipe::output,
                        CompressorRecipe::new);

        @Override
        public MapCodec<CompressorRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, CompressorRecipe> packetCodec() {
            return STREAM_CODEC;
        }
    }
}
