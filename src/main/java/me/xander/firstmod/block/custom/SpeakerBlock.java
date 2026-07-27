package me.xander.firstmod.block.custom;

import me.xander.firstmod.sound.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpeakerBlock extends Block {
    public SpeakerBlock(Settings settings) {
        super(settings);
    }
    public static int delayTicks = 100;
    protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (oldState.getBlock() != state.getBlock() && world instanceof ServerWorld serverWorld) {
            this.update(state, serverWorld, pos);
        }

    }

    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (world instanceof ServerWorld serverWorld) {
            this.update(state, serverWorld, pos);
        }

    }

    public void update(BlockState state, ServerWorld serverWorld, BlockPos pos) {
       if(serverWorld.isReceivingRedstonePower(pos)) {
           serverWorld.playSound(null, pos.getX(), pos.getY(), pos.getZ(), ModSounds.WELCOME_MESSAGE, SoundCategory.PLAYERS, 0.5f, 1f);
       }
    }
}
