package me.xander.firstmod.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BasicComputerBlock extends Block {
    private static int timesUsed;
    public static BooleanProperty POWERED = Properties.POWERED;
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final MapCodec<BasicComputerBlock> CODEC = createCodec(BasicComputerBlock::new);
    public BasicComputerBlock(Settings settings) {
        super(settings);
    }



    @Override
    protected boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    @Override
    protected int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return state.get(POWERED) ? 15 : 0;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        setPowered(world, pos, state, !state.get(POWERED));
        world.updateListeners(pos, state, state, 0);
        return super.onUse(state, world, pos, player, hit);
    }

    // For Facing
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED);
    }

    private static void setPowered(World world, BlockPos pos, BlockState state, boolean powered) {
        world.setBlockState(pos, state.with(POWERED, powered), 3);
    }
    public int getTimesUsed() {
        return timesUsed;
    }
}
