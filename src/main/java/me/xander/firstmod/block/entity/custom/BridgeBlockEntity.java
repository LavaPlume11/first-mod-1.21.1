package me.xander.firstmod.block.entity.custom;

import me.xander.firstmod.block.ModBlocks;
import me.xander.firstmod.block.custom.BridgeBlock;
import me.xander.firstmod.block.entity.ModBlockEntities;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class BridgeBlockEntity extends BlockEntity {
    private int age = 0;
    private int timer = 200;
    public BridgeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BRIDGE_BLOCK_BE, pos, state);
    }
    public void tick(World world, BlockPos pos, BlockState state, Direction direction, int count) {
        age++;
        if(age >= timer) {
            world.removeBlock(pos, false);
            world.removeBlockEntity(pos);
        }
        if (count != 0) {
            if (direction != null) {
                BlockPos newPos;
                switch (direction) {
                    case NORTH -> newPos = pos.north();
                    case SOUTH -> newPos = pos.south();
                    case EAST -> newPos = pos.east();
                    case WEST -> newPos = pos.west();
                    default -> newPos = pos.down();
                }
                if (!world.getBlockState(newPos).isOpaque()) {
                    BridgeBlock block = new BridgeBlock(AbstractBlock.Settings.copy(ModBlocks.BRIDGE_BLOCK), direction, count - 1);
                    world.setBlockState(newPos, block.getDefaultState());
                }
            }
        }
    }
    public void setTimer(int time) {
        timer = time;
    }
}
