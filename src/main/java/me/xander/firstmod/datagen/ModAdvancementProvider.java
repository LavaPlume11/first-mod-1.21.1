package me.xander.firstmod.datagen;

import me.xander.first_mod;
import me.xander.firstmod.block.ModBlocks;
import me.xander.firstmod.item.custom.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.ConsumeItemCriterion;
import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider extends FabricAdvancementProvider {
    public ModAdvancementProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(RegistryWrapper.WrapperLookup wrapperLookup, Consumer<AdvancementEntry> consumer) {
        AdvancementEntry getRawMithril = Advancement.Builder.create()
                .display(
                        ModItems.RAW_MITHRIL,
                        Text.literal("New Advancements in Society"),
                        Text.literal("Mine mithril ore"),
                        Identifier.of("firstmod:textures/block/mithril_block.png"),
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("got_raw_mithril", InventoryChangedCriterion.Conditions.items(ModItems.RAW_MITHRIL))
                .build(consumer, first_mod.MOD_ID + "/get_raw_mithril");

        AdvancementEntry getMithrilArmor = Advancement.Builder.create()
                .parent(getRawMithril)
                .display(
                        ModItems.MITHRIL_CHESTPLATE,
                        Text.literal("Cover Me In Crystal"),
                        Text.literal("Obtain a full set of mithril armor"),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("got_mithril_helmet", InventoryChangedCriterion.Conditions.items(ModItems.MITHRIL_HELMET))
                .criterion("got_mithril_chestplate", InventoryChangedCriterion.Conditions.items(ModItems.MITHRIL_CHESTPLATE))
                .criterion("got_mithril_leggings", InventoryChangedCriterion.Conditions.items(ModItems.MITHRIL_LEGGINGS))
                .criterion("got_mithril_boots", InventoryChangedCriterion.Conditions.items(ModItems.MITHRIL_BOOTS))
                .rewards(AdvancementRewards.Builder.experience(75))
                .build(consumer, first_mod.MOD_ID + "/get_mithril_armor");

        AdvancementEntry getDamagedBlade = Advancement.Builder.create()
                .parent(getRawMithril)
                .display(
                        ModItems.DAMAGED_MITHRIL_SWORD,
                        Text.literal("The Weak Fall"),
                        Text.literal("Find an ancient relic from a time long past."),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("got_damaged_blade", InventoryChangedCriterion.Conditions.items(ModItems.DAMAGED_MITHRIL_SWORD))
                .build(consumer, first_mod.MOD_ID + "/get_damaged_blade");

        AdvancementEntry getAncientBlade = Advancement.Builder.create()
                .parent(getDamagedBlade)
                .display(
                        ModItems.MITHRIL_SWORD,
                        Text.literal("The Strong Rise"),
                        Text.literal("Reforge a great weapon from the ashes of the past."),
                        null,
                        AdvancementFrame.GOAL,
                        true,
                        true,
                        false
                )
                .rewards(AdvancementRewards.Builder.experience(75))
                .criterion("got_ancient_blade", InventoryChangedCriterion.Conditions.items(ModItems.MITHRIL_SWORD))
                .build(consumer, first_mod.MOD_ID + "/get_ancient_blade");

        AdvancementEntry getTrueBlade = Advancement.Builder.create()
                .parent(getAncientBlade)
                .display(
                        ModItems.TRUE_BLADE,
                        Text.literal("The Mighty Rise Higher"),
                        Text.literal("Realize your full potential."),
                        null,
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        true
                )
                .rewards(AdvancementRewards.Builder.experience(500))
                .criterion("got_true_blade", InventoryChangedCriterion.Conditions.items(ModItems.TRUE_BLADE))
                .build(consumer, first_mod.MOD_ID + "/get_true_blade");
        AdvancementEntry getMithrilBlock = Advancement.Builder.create()
                .parent(getRawMithril)
                .display(
                        ModBlocks.MITHRIL_BLOCK,
                        Text.literal("A Whole Block Of This Stuff"),
                        Text.literal("Obtain a mithril block"),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("got_mithril_block", InventoryChangedCriterion.Conditions.items(ModBlocks.MITHRIL_BLOCK.asItem()))
                .build(consumer, first_mod.MOD_ID + "/get_mithril_block");

        AdvancementEntry getMithrilDisplayBlock = Advancement.Builder.create()
                .parent(getMithrilBlock)
                .display(
                        ModBlocks.MITHRIL_DISPLAY_BLOCK,
                        Text.literal("Your Life On Display"),
                        Text.literal("Obtain a display block"),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("got_mithril_display_block", InventoryChangedCriterion.Conditions.items(ModBlocks.MITHRIL_DISPLAY_BLOCK.asItem()))
                .build(consumer, first_mod.MOD_ID + "/get_mithril_display_block");

        AdvancementEntry doDrugs = Advancement.Builder.create()
                .parent(getMithrilArmor)
                .display(
                        ModItems.CATALYST,
                        Text.literal("Don't Do Drugs"),
                        Text.literal("Ignore your parents advice"),
                        null,
                        AdvancementFrame.GOAL,
                        true,
                        true,
                        false
                )
                .criterion("do_drugs", ConsumeItemCriterion.Conditions.item(ModItems.CATALYST))
                .build(consumer, first_mod.MOD_ID + "/do_drugs");

        AdvancementEntry getTomahawk = Advancement.Builder.create()
                .parent(getRawMithril)
                .display(
                        ModItems.TOMAHAWK,
                        Text.literal("A Throwable Axe"),
                        Text.literal("Obtain a Tomahawk"),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("got_tomahawk", InventoryChangedCriterion.Conditions.items(ModItems.TOMAHAWK))
                .build(consumer, first_mod.MOD_ID + "/get_tomahawk");
    }
}

