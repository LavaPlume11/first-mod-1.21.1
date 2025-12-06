package me.xander.firstmod.block.custom;

import com.mojang.serialization.MapCodec;
import me.xander.firstmod.block.entity.ModBlockEntities;
import me.xander.firstmod.block.entity.custom.BridgeBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BridgeBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final MapCodec<BridgeBlock> CODEC = createCodec(BridgeBlock::new);
    int remaining = 0;
    Direction facing;
    public BridgeBlock(Settings settings) {
        super(settings);
    }
    public BridgeBlock(Settings settings, Direction direction, int count) {
        super(settings);
        facing = direction;
        remaining = count;
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BridgeBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.BRIDGE_BLOCK_BE, (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1, facing, remaining));
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
