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

public class BloodBottleRecipe extends SpecialCraftingRecipe {
    public BloodBottleRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        boolean bl = false;
        boolean bl2 = false;
        first_mod.LOGGER.info("work");
        for(int i = 0; i < input.getSize(); ++i) {
            ItemStack itemStack = input.getStackInSlot(i);
            if (!itemStack.isEmpty()) {
                if (itemStack.isOf(Items.GLASS_BOTTLE) && !bl) {
                    bl = true;
                }  else {
                    if (!itemStack.isOf(ModItems.BIG_SWORD) || bl2) {
                        return false;
                    }

                    bl2 = true;
                }
            }
        }

        return bl && bl2;
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return new ItemStack(ModItems.BLOOD_BOTTLE);
    }

    @Override
    public DefaultedList<ItemStack> getRemainder(CraftingRecipeInput input) {
        DefaultedList<ItemStack> stacks = DefaultedList.ofSize(input.getSize(), ItemStack.EMPTY);
        for(int i = 0; i < input.getSize(); ++i) {
            ItemStack itemStack = input.getStackInSlot(i);
            if (!itemStack.isEmpty()) {
                    if (!itemStack.isOf(ModItems.BIG_SWORD)) {
                        ItemStack newStack = itemStack;
                        newStack.set(ModDataComponentTypes.DEFAULT_INT, itemStack.getOrDefault(ModDataComponentTypes.DEFAULT_INT,0) - 10);
                        stacks.add(newStack);
                    }


            }
        }
        return stacks;
    }

    @Override
    public boolean fits(int width, int height) {
        return width >= 2 && height >= 1;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.BLOOD_BOTTLE;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.BLOOD_BOTTLE_TYPE;
    }
}
