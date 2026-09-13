package me.xander.firstmod.block.custom;

import com.mojang.serialization.MapCodec;
import me.xander.firstmod.block.ModBlocks;
import me.xander.firstmod.block.entity.ModBlockEntities;
import me.xander.firstmod.block.entity.custom.AlterBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class AlterBlock extends BlockWithEntity implements BlockEntityProvider {
    public AlterBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final MapCodec<AlterBlock> CODEC = AlterBlock.createCodec(AlterBlock::new);

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
        builder.add(FACING);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    private static final VoxelShape SHAPE = Block.createCuboidShape(0,0,0,16,8,16);
    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AlterBlockEntity(pos,state);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof AlterBlockEntity) {
                ItemScatterer.spawn(world, pos,((AlterBlockEntity) blockEntity));
                world.updateComparators(pos, this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.getBlockEntity(pos) instanceof AlterBlockEntity alterBlockEntity) {
            BlockPattern.Result result = this.pattern().searchAround(world, pos);
            if (alterBlockEntity.isEmpty() && !stack.isEmpty() && result != null) {
                alterBlockEntity.setStack(0,stack);
                world.playSound(player,pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS,1f,2f);
                stack.decrement(1);
                if (!world.isClient()) {
                    summonParticles((ServerWorld) world, pos);
                }
                alterBlockEntity.startRitual(player);
                alterBlockEntity.markDirty();

                world.updateListeners(pos,state,state,0);
            }
        }


        return ItemActionResult.SUCCESS;
    }
    public static void summonParticles(ServerWorld world, BlockPos pos) {
        world.spawnParticles(ParticleTypes.ENCHANT, pos.getX() + 0.5f, pos.getY() + 1.0f, pos.getZ() + 0.5f,
                40, 0.1, 0.1, 0.1, 0.8);
    }
    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.ALTER_BE, (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }
    public BlockPattern pattern() {
        return BlockPatternBuilder.start()
                .aisle("###")
                .aisle("#^#")
                .aisle("###")
                .where('^', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(this)))
                .where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(ModBlocks.CORRUPTION_VINES)))
                .build();
    }
}

