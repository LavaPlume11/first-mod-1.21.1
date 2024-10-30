package me.xander.firstmod.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;

public record CrystallizerRecipeInput(ItemStack input) implements RecipeInput {
    @Override
    public ItemStack getStackInSlot(int slot) {
        return input;
    }

    @Override
    public int getSize() {
        return 1;
    }
}
