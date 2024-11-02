package me.xander;

import me.xander.firstmod.block.ModBlocks;
import me.xander.firstmod.block.entity.ModBlockEntities;
import me.xander.firstmod.components.ModDataComponentTypes;
import me.xander.firstmod.effect.ModEffects;
import me.xander.firstmod.enchantment.ModEnchantmentEffects;
import me.xander.firstmod.entity.ModEntities;
import me.xander.firstmod.entity.custom.LionEntity;
import me.xander.firstmod.events.AttackEntityHandler;
import me.xander.firstmod.item.custom.ModItemGroups;
import me.xander.firstmod.potion.ModPotions;
import me.xander.firstmod.recipe.ModRecipes;
import me.xander.firstmod.world.gen.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.item.*;
import net.minecraft.potion.Potions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static me.xander.firstmod.VilagerTrades.registerCustomTrades;
import static me.xander.firstmod.item.custom.ModItems.*;

public class first_mod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("firstmod");
    public static final String MOD_ID = "firstmod";


    @Override
    public void onInitialize() {
        ModItemGroups.registerItemGroups();
        FuelRegistry.INSTANCE.add(DYNAMITE, 100);
        LOGGER.info("Initializing FirstMod");
        ModBlocks.registerModBlocks();
        registerModItems();
        ModEffects.registerEffects();
        ModPotions.registerPotions();
        registerStrippables();
        ModRecipes.registerRecipes();
        ModDataComponentTypes.registerDataComponentTypes();
        ModEntities.registerModEntities();
        registerCustomTrades();
        ModEnchantmentEffects.registerEnchantmentEffects();
        ModBlockEntities.registerBlockEntities();
        FabricDefaultAttributeRegistry.register(ModEntities.LION, LionEntity.createLionAttributes());

        ModWorldGeneration.generateModWorldGeneration();
        AttackEntityCallback.EVENT.register(new AttackEntityHandler());


        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries ->
                entries.addAfter(Items.GOLDEN_CARROT, BANANA));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries ->
                entries.addAfter(Items.TRIDENT, SPEAR));

        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> builder.registerPotionRecipe(Potions.WEAVING, Items.COBWEB, ModPotions.STICKY_POTION));

        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> builder.registerPotionRecipe(ModPotions.STICKY_POTION, Items.REDSTONE, ModPotions.LONG_STICKY_POTION));



    }
    private static void registerStrippables(){
        StrippableBlockRegistry.register(ModBlocks.BLACKWOOD_LOG, ModBlocks.STRIPPED_BLACKWOOD_LOG);
        StrippableBlockRegistry.register(ModBlocks.BLACKWOOD_WOOD, ModBlocks.STRIPPED_BLACKWOOD_WOOD);
    }


    }




