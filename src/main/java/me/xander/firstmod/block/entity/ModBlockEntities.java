package me.xander.firstmod.block.entity;

import me.xander.first_mod;
import me.xander.firstmod.block.ModBlocks;
import me.xander.firstmod.block.entity.custom.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import team.reborn.energy.api.EnergyStorage;

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
    public static final BlockEntityType<EchoGeneratorBlockEntity> ECHO_GENERATOR_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(first_mod.MOD_ID, "echo_generator_be"),
                    BlockEntityType.Builder.create(EchoGeneratorBlockEntity::new, ModBlocks.ECHO_GENERATOR).build(null));
    public static final BlockEntityType<TankBlockEntity> TANK_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(first_mod.MOD_ID, "tank_be"),
                    BlockEntityType.Builder.create(TankBlockEntity::new, ModBlocks.TANK).build(null));
    public static final BlockEntityType<CompressorBlockEntity> COMPRESSOR_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(first_mod.MOD_ID, "compressor_be"),
                    BlockEntityType.Builder.create(CompressorBlockEntity::new, ModBlocks.COMPRESSOR).build(null));
    public static final BlockEntityType<MelterBlockEntity> MELTER_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(first_mod.MOD_ID, "melter_be"),
                    BlockEntityType.Builder.create(MelterBlockEntity::new, ModBlocks.MELTER).build(null));


    public static void registerBlockEntities(){
        first_mod.LOGGER.info("Registering Block Entities for" + first_mod.MOD_ID);

        EnergyStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.energyStorage, ECHO_GENERATOR_BE);
        EnergyStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.energyStorage, CRYSTALLIZER_BE);
        EnergyStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.energyStorage, COMPRESSOR_BE);
    }
}
