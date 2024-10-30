package me.xander.firstmod.compat;

import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.xander.firstmod.block.ModBlocks;
import me.xander.firstmod.recipe.CrystallizerRecipe;
import me.xander.firstmod.recipe.ModRecipes;
import me.xander.firstmod.screen.custom.CrystallizerScreen;

public class first_modREIClient implements REIClientPlugin {
    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new CrystallizerCatagory());

        registry.addWorkstations(CrystallizerCatagory.CRYSTALLIZER, EntryStacks.of(ModBlocks.CRYSTALLIZER));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(CrystallizerRecipe.class, ModRecipes.CRYSTALLIZER_TYPE,
                CrystallIzerDisplay::new);
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerClickArea(screen -> new Rectangle(((screen.width - 176) / 2) + 78,
                ((screen.height - 166) / 2) + 30, 20, 25),
                CrystallizerScreen.class, CrystallizerCatagory.CRYSTALLIZER);
    }
}
