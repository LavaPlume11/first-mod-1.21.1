package me.xander.firstmod.block.custom;

import me.xander.firstmod.block.GolemSpawnBlock;
import me.xander.firstmod.entity.ModEntities;
import me.xander.firstmod.entity.custom.SteamGolemEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.EntityType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class SteamVent extends Block implements GolemSpawnBlock<SteamGolemEntity> {
    public SteamVent(Settings settings) {
        super(settings);
    }


    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        Block block = world.getBlockState(pos.down()).getBlock();
        if (block == Blocks.MAGMA_BLOCK || block == Blocks.LAVA || block == Blocks.FIRE || block == Blocks.CAMPFIRE || block == Blocks.SOUL_FIRE || block == Blocks.SOUL_CAMPFIRE) {
            double xPos = (double) pos.getX();
            double yPos = pos.getY() + 0.8;
            double zPos = (double) pos.getZ();
            double yOffset = random.nextDouble() * 6.0 / 8.0;
            double xOffset = random.nextDouble() * 6.0 / 8.0;
            double zOffset = random.nextDouble() * 6.0 / 8.0;

            world.addParticle(ParticleTypes.CLOUD, xPos + xOffset, yPos + yOffset, zPos + zOffset, 0.01, 0.1, 0.01);
        }
    }

    //For Golem
    @Override
    public int offsetLeft() {
        return 1;
    }

    @Override
    public EntityType<SteamGolemEntity> entityType() {
        return ModEntities.STEAM_GOLEM;
    }

    @Override
    public int blocksDown() {
        return 2;
    }

    @Override
    public BlockPattern pattern() {
        return BlockPatternBuilder.start().aisle(
                        "~^~",
                                "###",
                                "~#~")
                .where('^', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(this)))
                .where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.MAGMA_BLOCK)))
                .where('~', pos -> {
                    return pos.getBlockState().isAir();
                })
                .build();
    }
    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!oldState.isOf(state.getBlock())) {
            this.trySpawnEntity(world, pos);
        }
    }
}
