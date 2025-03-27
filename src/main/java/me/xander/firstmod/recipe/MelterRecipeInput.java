package me.xander.firstmod.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;

public record MelterRecipeInput(ItemStack input1, ItemStack input2, ItemStack input3, int heat) implements RecipeInput {
    @Override
    public ItemStack getStackInSlot(int slot) {
        ItemStack x;
        switch (slot) {
            case 0 -> x = this.input1;
            case 1 -> x = this.input2;
            case 2 -> x = this.input3;
            default -> throw new IllegalArgumentException("Recipe does not contain slot " + slot);
        }

        return x;
    }

    @Override
    public int getSize() {
        return 3;
    }
}
