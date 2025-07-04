package me.xander.firstmod.block.custom;

import com.mojang.serialization.MapCodec;
import me.xander.firstmod.block.entity.custom.StoneOfSwordBlockEntity;
import me.xander.firstmod.block.renderer.StoneOfSwordBlockEntityRenderer;
import me.xander.firstmod.item.custom.ModItems;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class StoneOfSwordBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = Properties.LIT;
    public static BooleanProperty POWERED = Properties.POWERED;
    public static final MapCodec<StoneOfSwordBlock> CODEC = StoneOfSwordBlock.createCodec(StoneOfSwordBlock::new);

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
        builder.add(FACING,LIT,POWERED);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }


    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new StoneOfSwordBlockEntity(pos, state);
    }
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(POWERED)) {
            // only client
            double xPos = pos.getX() + 0.5f;
            double yPos = pos.getY() + 0.25f;
            double zPos = pos.getZ() + 0.5f;
            double offset = random.nextDouble() * 0.6 - 0.3;

            world.addParticle(ParticleTypes.ELECTRIC_SPARK, xPos + offset, yPos + offset, zPos + offset, 0.0, 0.5, 0.0);
        }


    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof StoneOfSwordBlockEntity) {
                ItemScatterer.spawn(world, pos, ((StoneOfSwordBlockEntity) blockEntity));
                world.updateComparators(pos, this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }

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
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos,
                                             PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.getBlockEntity(pos) instanceof StoneOfSwordBlockEntity displayBlockEntity) {
            if (displayBlockEntity.isEmpty() && stack.getItem() instanceof SwordItem) {
                if (displayBlockEntity.isEmpty()){
                    setPowered(world,pos,state,true);
                    displayBlockEntity.setStack(0, stack);
                    world.playSound(player, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1f, 2f);
                    stack.decrement(1);
                    displayBlockEntity.markDirty();
                    world.updateListeners(pos, state, state, 0);
                }
            } else if (stack.isEmpty() && !player.isSneaking()) {
                setPowered(world, pos, state,false);
                ItemStack stackOnDisplay = displayBlockEntity.getStack(0);
                player.setStackInHand(Hand.MAIN_HAND, stackOnDisplay);
                world.playSound(player,pos,SoundEvents.ENTITY_ITEM_PICKUP,SoundCategory.BLOCKS,1f,1f);
                displayBlockEntity.clear();
                displayBlockEntity.markDirty();
                world.updateListeners(pos,state,state,0);
            }
        }
        
        return ItemActionResult.SUCCESS;
    }
    public StoneOfSwordBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(POWERED, false));
    }
    private static void setPowered(World world, BlockPos pos, BlockState state, boolean powered) {
        world.setBlockState(pos, state.with(POWERED, powered), 3);
    }
    private static final VoxelShape SHAPE = Block.createCuboidShape(0,0,0,16,8,16);

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
