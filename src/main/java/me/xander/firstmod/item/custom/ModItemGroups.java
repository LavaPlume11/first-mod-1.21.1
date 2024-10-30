package me.xander.firstmod.item.custom;

import me.xander.first_mod;
import me.xander.firstmod.block.ModBlocks;
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
                       entries.add(ModItems.BANANA);
                       entries.add(ModItems.BURNT_BANANA);
                       entries.add(ModItems.SPEAR);
                       entries.add(ModBlocks.MITHRIL_BLOCK);
                       entries.add(ModBlocks.SOUND_BLOCK);
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
                       entries.add(ModItems.DAMAGED_MITHRIL_SWORD);
                       entries.add(ModItems.MITHRIL_SWORD_SHARD);
                       entries.add(ModItems.MITHRIL_HELMET);
                       entries.add(ModItems.MITHRIL_CHESTPLATE);
                       entries.add(ModItems.MITHRIL_LEGGINGS);
                       entries.add(ModItems.MITHRIL_BOOTS);
                       entries.add(ModItems.MITHRIL_TEMPLATE);
                       entries.add(ModItems.MITHRIL_SWORD);
                       entries.add(ModItems.TRUE_BLADE);
                       entries.add(ModBlocks.BLACKWOOD_LOG);
                       entries.add(ModBlocks.BLACKWOOD_LEAVES);
                       entries.add(ModBlocks.BLACKWOOD_WOOD);
                       entries.add(ModBlocks.BLACKWOOD_PLANKS);
                       entries.add(ModBlocks.STRIPPED_BLACKWOOD_LOG);
                       entries.add(ModBlocks.STRIPPED_BLACKWOOD_WOOD);
                       entries.add(ModBlocks.BLACKWOOD_SAPLING);

                   }).build());


    public static void registerItemGroups(){
        first_mod.LOGGER.info("Registering Item Groups for" + first_mod.MOD_ID);
    }




}