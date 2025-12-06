package me.xander.firstmod.block.custom;

import me.xander.firstmod.entity.ModEntities;
import me.xander.firstmod.entity.custom.WhispererEntity;
import me.xander.firstmod.block.GolemSpawnBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.EntityType;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WhispererGolemSpawner extends Block implements GolemSpawnBlock<WhispererEntity> {
    public WhispererGolemSpawner(Settings settings) {
        super(settings);
    }

    @Override
    public EntityType<WhispererEntity> entityType() {
        return ModEntities.WHISPERER;
    }


    @Override
    public BlockPattern pattern() {
        return BlockPatternBuilder.start().aisle(
                        "^",
                                "#")
                .where('^', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(this)))
                .where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.PRISMARINE)))
                .build();
    }

    @Override
    public int blocksDown() {
        return 0;
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!oldState.isOf(state.getBlock())) {
            this.trySpawnEntity(world, pos);
        }
    }

}
