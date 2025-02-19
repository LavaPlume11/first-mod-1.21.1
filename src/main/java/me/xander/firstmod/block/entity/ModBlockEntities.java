package me.xander.firstmod.block.entity;

import me.xander.first_mod;
import me.xander.firstmod.block.ModBlocks;
import me.xander.firstmod.block.entity.custom.CrystallizerBlockEntity;
import me.xander.firstmod.block.entity.custom.DisplayBlockEntity;
import me.xander.firstmod.block.entity.custom.StoneOfSwordBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<DisplayBlockEntity> DISPLAY_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(first_mod.MOD_ID,"display_be"),
                   BlockEntityType.Builder.create(DisplayBlockEntity::new, ModBlocks.MITHRIL_DISPLAY_BLOCK).build(null));
    public static final BlockEntityType<CrystallizerBlockEntity> CRYSTALLIZER_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(first_mod.MOD_ID, "crystallizer_be"),
                    BlockEntityType.Builder.create(CrystallizerBlockEntity::new, ModBlocks.CRYSTALLIZER).build(null));
    public static final BlockEntityType<StoneOfSwordBlockEntity> STONE_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(first_mod.MOD_ID,"stone_be"),
                    BlockEntityType.Builder.create(StoneOfSwordBlockEntity::new, ModBlocks.STONE_OF_SWORD).build(null));


    public static void registerBlockEntities(){
        first_mod.LOGGER.info("Registering Block Entities for" + first_mod.MOD_ID);
    }
}
