package me.xander.firstmod.fluid;

import me.xander.first_mod;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModFluids {
    public static final FlowableFluid STILL_MITHRIL_WATER = Registry.register(Registries.FLUID,
            Identifier.of(first_mod.MOD_ID,"mithril_water"), new MithrilWaterFluid.Still());
    public static final FlowableFluid FLOWING_MITHRIL_WATER = Registry.register(Registries.FLUID,
            Identifier.of(first_mod.MOD_ID,"flowing_mithril_water"), new MithrilWaterFluid.Flowing());

    public static final Block MITHRIL_WATER_BLOCK = Registry.register(Registries.BLOCK,Identifier.of(first_mod.MOD_ID,
            "mithril_water_block"),new FluidBlock(ModFluids.STILL_MITHRIL_WATER, AbstractBlock.Settings.copy(Blocks.WATER)
            .replaceable().liquid()));
    public static final Item MITHRIL_WATER_BUCKET = Registry.register(Registries.ITEM,Identifier.of(first_mod.MOD_ID,
            "mithril_water_bucket"), new BucketItem(ModFluids.STILL_MITHRIL_WATER, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)));

    public static void registerFluids() {
        first_mod.LOGGER.info("Registering Fluids for"+ first_mod.MOD_ID);
    }
}
