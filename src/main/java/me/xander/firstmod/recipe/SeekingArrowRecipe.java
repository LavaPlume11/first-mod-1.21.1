package me.xander.firstmod.recipe;

import me.xander.first_mod;
import me.xander.firstmod.components.ModDataComponentTypes;
import me.xander.firstmod.item.custom.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class SeekingArrowRecipe extends SpecialCraftingRecipe {
    public SeekingArrowRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        boolean bl = false;
        int arrows = 0;
        for(int i = 0; i < input.getSize(); ++i) {
            ItemStack itemStack = input.getStackInSlot(i);
            if (!itemStack.isEmpty()) {
                if (itemStack.isOf(Items.ARROW)) {
                    arrows += itemStack.getCount();
                } else if (itemStack.isOf(ModItems.RESONANT_ALLOY)) {
                    if (bl) {
                        return false;
                    }
                    bl = true;
                } else {
                    return false;
                }
            }
        }
       return bl && arrows > 0 && arrows <= 64;
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        int arrows = 0;
        for(int i = 0; i < input.getSize(); ++i) {
            ItemStack itemStack = input.getStackInSlot(i);
                if (itemStack.isOf(Items.ARROW)) {
                    arrows += itemStack.getCount();
                }
        }
        return new ItemStack(ModItems.SEEKING_ARROW, arrows);
    }

    @Override
    public DefaultedList<ItemStack> getRemainder(CraftingRecipeInput input) {
        return DefaultedList.of();
    }

    @Override
    public boolean fits(int width, int height) {
        return width >= 2 && height >= 1;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.SEEKING_ARROW;
    }

}
