package me.xander;

import me.xander.firstmod.block.ModBlocks;
import me.xander.firstmod.block.entity.ModBlockEntities;
import me.xander.firstmod.command.ReturnHomeCommand;
import me.xander.firstmod.command.SetHomeCommand;
import me.xander.firstmod.components.ModDataComponentTypes;
import me.xander.firstmod.effect.ModEffects;
import me.xander.firstmod.enchantment.ModEnchantmentEffects;
import me.xander.firstmod.entity.ModEntities;
import me.xander.firstmod.entity.custom.*;
import me.xander.firstmod.events.AttackEntityHandler;
import me.xander.firstmod.events.PlayerCopyHandler;
import me.xander.firstmod.fluid.ModFluids;
import me.xander.firstmod.item.custom.ModItemGroups;
import me.xander.firstmod.potion.ModPotions;
import me.xander.firstmod.recipe.ModRecipes;
import me.xander.firstmod.sound.ModSounds;
import me.xander.firstmod.util.ModLootTableModifiers;
import me.xander.firstmod.villager.ModVillagers;
import me.xander.firstmod.world.gen.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.item.*;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Optional;

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
        ModVillagers.registerVillagers();
        ModFluids.registerFluids();
        registerCustomTrades();
        registerStrippables();
        ModRecipes.registerRecipes();
        ModSounds.registerSounds();
        ModDataComponentTypes.registerDataComponentTypes();
        ModEntities.registerModEntities();
        ModLootTableModifiers.modifyLootTables();
        ModEnchantmentEffects.registerEnchantmentEffects();
        ModBlockEntities.registerBlockEntities();
        ModWorldGeneration.generateModWorldGeneration();

        FabricDefaultAttributeRegistry.register(ModEntities.LION, LionEntity.createLionAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.LEMMING, LemmingEntity.createLemmingAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.WHISPERER, WhispererEntity.createWhispererAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.WARTURTLE, WarturtleEntity.createWarturtleAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.SLEIGH, SleighEntity.createSleighAttributes());

        AttackEntityCallback.EVENT.register(new AttackEntityHandler());
        CommandRegistrationCallback.EVENT.register(SetHomeCommand::register);
        CommandRegistrationCallback.EVENT.register(ReturnHomeCommand::register);
        ServerPlayerEvents.COPY_FROM.register(new PlayerCopyHandler());
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries ->
                entries.addAfter(Items.GOLDEN_CARROT, BANANA));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries ->
                entries.addAfter(Items.TRIDENT, SPEAR));

        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> builder.registerPotionRecipe(Potions.WEAVING, Items.COBWEB, ModPotions.STICKY_POTION));
        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> builder.registerPotionRecipe(ModPotions.STICKY_POTION, Items.REDSTONE, ModPotions.LONG_STICKY_POTION));

        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MOD_ID, "blood_particle"), BLOOD_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MOD_ID, "sticky_feather_particle"), STICKY_FEATHER_PARTICLE);


    }
    private static void registerStrippables() {
        StrippableBlockRegistry.register(ModBlocks.BLACKWOOD_LOG, ModBlocks.STRIPPED_BLACKWOOD_LOG);
        StrippableBlockRegistry.register(ModBlocks.BLACKWOOD_WOOD, ModBlocks.STRIPPED_BLACKWOOD_WOOD);
    }
    private static void registerCustomTrades() {
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER,3, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD,5),
                    Optional.of(new TradedItem(Items.ALLIUM, 1)),
                    new ItemStack(BANANA,1),1,6,0.55f
            ));

        });
        TradeOfferHelper.registerVillagerOffers(ModVillagers.CULTIST,1, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 64),
                    new ItemStack(BIG_SWORD, 1), 1, 4, 0.5f

            ));
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 64),
                    new ItemStack(THANK_HAT, 1), 1, 1, 0.1f
            ));
        });


    }
    public static final SimpleParticleType BLOOD_PARTICLE = FabricParticleTypes.simple();
    public static final SimpleParticleType STICKY_FEATHER_PARTICLE = FabricParticleTypes.simple();



    }




