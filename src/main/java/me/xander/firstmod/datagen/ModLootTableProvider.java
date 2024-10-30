package me.xander.firstmod.datagen;

import me.xander.firstmod.block.ModBlocks;
import me.xander.firstmod.item.custom.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {


    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.RUBY_BLOCK);
        addDrop(ModBlocks.SOUND_BLOCK);
        addDrop(ModBlocks.MITHRIL_BLOCK);
        addDrop(ModBlocks.MITHRIL_ORE, copperLikeOreDrops(ModBlocks.MITHRIL_ORE, ModItems.RAW_MITHRIL));
        addDrop(ModBlocks.NETHER_MITHRIL_ORE, copperLikeOreDrops(ModBlocks.NETHER_MITHRIL_ORE, ModItems.RAW_MITHRIL));
        addDrop(ModBlocks.DEEPSLATE_MITHRIL_ORE, copperLikeOreDrops(ModBlocks.DEEPSLATE_MITHRIL_ORE, ModItems.RAW_MITHRIL));
        addDrop(ModBlocks.MITHRIL_BUTTON);
        addDrop(ModBlocks.MITHRIL_FENCE);
        addDrop(ModBlocks.MITHRIL_PRESSURE_PLATE);
        addDrop(ModBlocks.MITHRIL_FENCE_GATE);
        addDrop(ModBlocks.MITHRIL_STAIRS);
        addDrop(ModBlocks.MITHRIL_TRAPDOOR);
        addDrop(ModBlocks.MITHRIL_BUTTON);
        addDrop(ModBlocks.MITHRIL_WALL);
        addDrop(ModBlocks.MITHRIL_DISPLAY_BLOCK);
        addDrop(ModBlocks.CRYSTALLIZER);
        addDrop(ModBlocks.BLACKWOOD_LOG);addDrop(ModBlocks.BLACKWOOD_WOOD);addDrop(ModBlocks.STRIPPED_BLACKWOOD_LOG);
        addDrop(ModBlocks.STRIPPED_BLACKWOOD_WOOD);addDrop(ModBlocks.BLACKWOOD_PLANKS);addDrop
                (ModBlocks.BLACKWOOD_LEAVES, leavesDrops(ModBlocks.BLACKWOOD_LEAVES, ModBlocks.BLACKWOOD_SAPLING,0.0625f))
        ;addDrop(ModBlocks.BLACKWOOD_SAPLING);

        addDrop(ModBlocks.MITHRIL_DOOR,doorDrops(ModBlocks.MITHRIL_DOOR));
        addDrop(ModBlocks.MITHRIL_SLAB,slabDrops(ModBlocks.MITHRIL_SLAB));
    }
    public LootTable.Builder copperLikeOreDrops(Block drop, Item item) {
        RegistryWrapper.Impl<Enchantment> impl = this.registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
        return this.dropsWithSilkTouch(
                drop,
                (LootPoolEntry.Builder<?>)this.applyExplosionDecay(
                        drop,
                        ItemEntry.builder(item)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0F, 5.0F)))
                                .apply(ApplyBonusLootFunction.oreDrops(impl.getOrThrow(Enchantments.FORTUNE)))));
}}
