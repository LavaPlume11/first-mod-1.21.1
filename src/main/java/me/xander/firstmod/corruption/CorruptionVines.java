package me.xander.firstmod.corruption;


import me.xander.firstmod.block.ModBlocks;
import me.xander.firstmod.item.custom.CorruptionRepeller;
import me.xander.firstmod.sound.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.GlowLichenBlock;
import net.minecraft.block.MultifaceGrowthBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.explosion.Explosion;

public class CorruptionVines extends GlowLichenBlock {
    public CorruptionVines(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean canReplace(BlockState state, ItemPlacementContext context) {
        return context.getStack().isOf(ModBlocks.CORRUPTION_VINES.asItem());
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (stack.getItem() instanceof CorruptionRepeller item) {
            world.removeBlock(pos, false);
            world.playSound(player,pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.PLAYERS, 1f, 1f);
            if (!player.isCreative() && item.isConsumed()) {
                stack.decrement(1);
            }
        } else if (CorruptionHandler.getCorruption(player) >= 2 || player.isCreative()) {
            world.removeBlock(pos, false);
            world.playSound(player,pos, ModSounds.STICKING, SoundCategory.AMBIENT, 1f, 1f);
            if (!world.isClient()) {
                CorruptionHandler.addCorruption((ServerPlayerEntity) player, 1);
            }
        }
        return ItemActionResult.SUCCESS;
    }

    @Override
    public boolean shouldDropItemsOnExplosion(Explosion explosion) {
        return false;
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return false;
    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (hasDirection(state, Direction.DOWN) && world.getBlockState(pos.down()).getBlock() == Blocks.OBSIDIAN) {
            world.setBlockState(pos.down(),Blocks.CRYING_OBSIDIAN.getDefaultState());
        }
        if (hasDirection(state, Direction.UP) && world.getBlockState(pos.up()).getBlock() == Blocks.OBSIDIAN) {
            world.setBlockState(pos.up(),Blocks.CRYING_OBSIDIAN.getDefaultState());
        }
        if (hasDirection(state, Direction.WEST) && world.getBlockState(pos.west()).getBlock() == Blocks.OBSIDIAN) {
            world.setBlockState(pos.west(),Blocks.CRYING_OBSIDIAN.getDefaultState());
        }
        if (hasDirection(state, Direction.EAST) && world.getBlockState(pos.east()).getBlock() == Blocks.OBSIDIAN) {
            world.setBlockState(pos.east(),Blocks.CRYING_OBSIDIAN.getDefaultState());
        }
        if (hasDirection(state, Direction.NORTH) && world.getBlockState(pos.north()).getBlock() == Blocks.OBSIDIAN) {
            world.setBlockState(pos.north(),Blocks.CRYING_OBSIDIAN.getDefaultState());
        }
        if (hasDirection(state, Direction.SOUTH) && world.getBlockState(pos.south()).getBlock() == Blocks.OBSIDIAN) {
            world.setBlockState(pos.south(),Blocks.CRYING_OBSIDIAN.getDefaultState());
        }

        super.onBlockAdded(state, world, pos, oldState, notify);
    }
}
