package me.xander.firstmod.item.custom;

import me.xander.first_mod;
import me.xander.firstmod.block.ModBlocks;
import me.xander.firstmod.fluid.ModFluids;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
   public static final ItemGroup MOD_GROUP = Registry.register(Registries.ITEM_GROUP, Identifier.of(first_mod.MOD_ID,"mithril"),
           FabricItemGroup.builder().displayName(Text.translatable("itemgroup.mithril"))
                   .icon(() -> new ItemStack(ModItems.MITHRIL)).entries((displayContext, entries) -> {
                        entries.add(ModItems.MITHRIL);
                       entries.add(ModItems.RAW_MITHRIL);
                       entries.add(ModFluids.MITHRIL_WATER_BUCKET);
                       entries.add(ModItems.MELTED_METAL);
                       entries.add(ModItems.BANANA);
                       entries.add(ModBlocks.BANANA_LEAVES);
                       entries.add(ModItems.BURNT_BANANA);
                       entries.add(ModItems.SPEAR);
                       entries.add(ModItems.MITHRIL_SWORD_SHARD);
                       entries.add(ModItems.CATALYST);
                       entries.add(ModItems.THANK_HAT);
                       entries.add(ModItems.XMAS_HAT);
                       entries.add(ModItems.GUITAR);
                       entries.add(ModItems.LEMMING_SPAWN_EGG);
                       entries.add(ModItems.WHISPERER_SPAWN_EGG);
                       entries.add(ModItems.WARTURTLE_SPAWN_EGG);
                       entries.add(ModBlocks.ECHO_GENERATOR);
                       entries.add(ModBlocks.TANK);
                       entries.add(ModBlocks.CRYSTALLIZER);
                       entries.add(ModBlocks.COMPRESSOR);
                       entries.add(ModBlocks.MELTER);
                       entries.add(ModBlocks.POWER_AMPLIFIER);
                       entries.add(ModBlocks.MITHRIL_BLOCK);
                       entries.add(ModBlocks.MITHRIL_DISPLAY_BLOCK);
                       entries.add(ModBlocks.MITHRIL_ORE);
                       entries.add(ModBlocks.DEEPSLATE_MITHRIL_ORE);
                       entries.add(ModBlocks.NETHER_MITHRIL_ORE);
                       entries.add(ModBlocks.MITHRIL_STAIRS);
                       entries.add(ModBlocks.MITHRIL_BUTTON);
                       entries.add(ModBlocks.MITHRIL_DOOR);
                       entries.add(ModBlocks.MITHRIL_FENCE);
                       entries.add(ModBlocks.MITHRIL_FENCE_GATE);
                       entries.add(ModBlocks.MITHRIL_PRESSURE_PLATE);
                       entries.add(ModBlocks.MITHRIL_TRAPDOOR);
                       entries.add(ModBlocks.MITHRIL_WALL);
                       entries.add(ModBlocks.MITHRIL_SLAB);
                       entries.add(ModItems.WARDEN_PENDENT);
                       entries.add(ModItems.DIAMOND_WARDEN_PENDENT);
                       entries.add(ModItems.GEMS);
                       entries.add(ModItems.GEM_CLUSTER);
                       entries.add(ModItems.POWER_GEM);
                       entries.add(ModItems.POWER_CELL);
                       entries.add(ModBlocks.STONE_OF_SWORD);
                       entries.add(ModItems.DAMAGED_MITHRIL_SWORD);
                       entries.add(ModItems.MITHRIL_SWORD);
                       entries.add(ModItems.REFINED_MITHRIL_SWORD);
                       entries.add(ModItems.TRUE_BLADE);
                       entries.add(ModItems.LIGHT_KNIFE);
                       entries.add(ModItems.RE_DEAD_SWORD);
                       entries.add(ModItems.LIGHTNING_HELMET);
                       entries.add(ModItems.MITHRIL_HELMET);
                       entries.add(ModItems.MITHRIL_CHESTPLATE);
                       entries.add(ModItems.MITHRIL_LEGGINGS);
                       entries.add(ModItems.MITHRIL_BOOTS);
                       entries.add(ModItems.IRON_WARTURTLE_ARMOR);
                       entries.add(ModItems.GOLD_WARTURTLE_ARMOR);
                       entries.add(ModItems.DIAMOND_WARTURTLE_ARMOR);
                       entries.add(ModItems.NETHERITE_WARTURTLE_ARMOR);
                       entries.add(ModItems.MITHRIL_WARTURTLE_ARMOR);
                       entries.add(ModItems.BIG_SWORD);
                       entries.add(ModItems.NETHER_BOW);
                       entries.add(ModItems.TOMAHAWK);
                   }).build());


    public static void registerItemGroups(){
        first_mod.LOGGER.info("Registering Item Groups for" + first_mod.MOD_ID);
    }




}
