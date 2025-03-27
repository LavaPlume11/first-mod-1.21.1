package me.xander.firstmod.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.World;

public record MelterRecipe(Ingredient ingredient1, Ingredient ingredient2, Ingredient ingredient3, ItemStack output, int heat) implements Recipe<MelterRecipeInput> {
    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.of();
        list.add(this.ingredient1);
        list.add(this.ingredient2);
        list.add(this.ingredient3);
        return list;
    }

    @Override
    public boolean matches(MelterRecipeInput input, World world) {
        if (world.isClient)
            return false;

        return ingredient1.test(input.getStackInSlot(0)) && ingredient2.test(input.getStackInSlot(1)) && ingredient3.test(input.getStackInSlot(2));
    }

    @Override
    public ItemStack craft(MelterRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
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
        return ModRecipes.MELTER_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.MELTER_TYPE;
    }
    public static class Serializer implements RecipeSerializer<MelterRecipe> {
        public static final MapCodec<MelterRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient1").forGetter(MelterRecipe::ingredient1),
                Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient2").forGetter(MelterRecipe::ingredient2),
                Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient3").forGetter(MelterRecipe::ingredient3),
                ItemStack.CODEC.fieldOf("result").forGetter(MelterRecipe::output),
                Codecs.POSITIVE_INT.fieldOf("heat").forGetter(MelterRecipe::heat)
        ).apply(inst, MelterRecipe::new));
        public static final PacketCodec<RegistryByteBuf, MelterRecipe> STREAM_CODEC =
                PacketCodec.tuple(
                        Ingredient.PACKET_CODEC, MelterRecipe::ingredient1,
                        Ingredient.PACKET_CODEC, MelterRecipe::ingredient2,
                        Ingredient.PACKET_CODEC, MelterRecipe::ingredient3,
                        ItemStack.PACKET_CODEC, MelterRecipe::output,
                        PacketCodecs.INTEGER, MelterRecipe::heat,
                        MelterRecipe::new);

        @Override
        public MapCodec<MelterRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, MelterRecipe> packetCodec() {
            return STREAM_CODEC;
        }
    }
}
