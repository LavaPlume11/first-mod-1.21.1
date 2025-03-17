package me.xander.firstmod.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;

import java.util.List;

public record CompressorRecipeInput(ItemStack base, ItemStack addition) implements RecipeInput {
    @Override
    public ItemStack getStackInSlot(int slot) {
        ItemStack x;
        switch (slot) {
            case 0 -> x = this.base;
            case 1 -> x = this.addition;
            default -> throw new IllegalArgumentException("Recipe does not contain slot " + slot);
        }

        return x;
    }

    @Override
    public int getSize() {
        return 2;
    }
}
