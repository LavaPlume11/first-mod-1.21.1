package me.xander.firstmod.corruption;

import me.xander.first_mod;
import me.xander.firstmod.block.ModBlocks;
import me.xander.firstmod.data.ModData;
import me.xander.firstmod.sound.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class CorruptionBlock extends Block {
    public CorruptionBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected BlockSoundGroup getSoundGroup(BlockState state) {
        return new BlockSoundGroup(1f,1f, ModSounds.PORTAL_BOOM, SoundEvents.BLOCK_SCULK_STEP,
                ModSounds.STICKING, SoundEvents.BLOCK_SCULK_HIT, SoundEvents.BLOCK_SCULK_FALL);
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        List<ServerPlayerEntity> players = world.getPlayers();
        List<ServerPlayerEntity> closePlayers = new ArrayList<>();
        List<BlockPos> posList = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
             posList.add(i, new BlockPos((int) players.get(i).getX(), (int) players.get(i).getY(), (int) players.get(i).getZ()));
             if (posList.get(i).getX() < pos.east(10).getX() && posList.get(i).getX() > pos.west(10).getX()) {
                 if (posList.get(i).getZ() < pos.south(10).getZ() && posList.get(i).getZ() > pos.north(10).getZ()) {
                     if((posList.get(i).getY() < pos.up(20).getY() && posList.get(i).getY() > pos.down(20).getY())) {
                         if (CorruptionHandler.getCorruption(players.get(i)) <= 5) {
                             if (!players.get(i).isCreative()) {
                                 CorruptionHandler.addCorruption(players.get(i), 1);
                                 players.get(i).playSoundToPlayer(ModSounds.STICKING, SoundCategory.NEUTRAL, 1f, 1f);
                             }
                         }
                     }
                 }
             }
        }
        super.randomTick(state, world, pos, random);
    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return true;
    }

    public void spreadVines(World world, BlockPos pos) {
        if (world.getBlockState(pos.up()).isReplaceable()) {
            world.setBlockState(pos.up(), ModBlocks.CORRUPTION_VINES.getDefaultState());
        }
        if (world.getBlockState(pos.down()).isReplaceable()) {
            world.setBlockState(pos.down(), ModBlocks.CORRUPTION_VINES.getDefaultState());
        }
        if (world.getBlockState(pos.west()).isReplaceable()) {
            world.setBlockState(pos.west(), ModBlocks.CORRUPTION_VINES.getDefaultState());
        }
        if (world.getBlockState(pos.east()).isReplaceable()) {
            world.setBlockState(pos.east(), ModBlocks.CORRUPTION_VINES.getDefaultState());
        }
        if (world.getBlockState(pos.north()).isReplaceable()) {
            world.setBlockState(pos.north(), ModBlocks.CORRUPTION_VINES.getDefaultState());
        }
        if (world.getBlockState(pos.south()).isReplaceable()) {
            world.setBlockState(pos.south(), ModBlocks.CORRUPTION_VINES.getDefaultState());
        }
    }
}
