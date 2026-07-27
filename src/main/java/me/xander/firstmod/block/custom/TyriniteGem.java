package me.xander.firstmod.block.custom;

import me.xander.first_mod;
import me.xander.firstmod.block.ModBlocks;
import me.xander.firstmod.corruption.CorruptionHandler;
import me.xander.firstmod.sound.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureSpawns;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class TyriniteGem extends Block {
    public TyriniteGem(Settings settings) {
        super(settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {

            super.randomDisplayTick(state, world, pos, random);
    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!world.isReceivingRedstonePower(pos)) {
            List<ServerPlayerEntity> players = world.getPlayers();
            List<ServerPlayerEntity> closePlayers = new ArrayList<>();
            List<BlockPos> posList = new ArrayList<>();
            for (int i = 0; i < players.size(); i++) {
                posList.add(i, new BlockPos((int) players.get(i).getX(), (int) players.get(i).getY(), (int) players.get(i).getZ()));
                if (posList.get(i).getX() < pos.east(10).getX() && posList.get(i).getX() > pos.west(10).getX()) {
                    if (posList.get(i).getZ() < pos.south(10).getZ() && posList.get(i).getZ() > pos.north(10).getZ()) {
                        if ((posList.get(i).getY() < pos.getY() && posList.get(i).getY() > pos.down(20).getY())) {
                            if (CorruptionHandler.getCorruption(players.get(i)) > 0) {
                                players.get(i).damage(players.get(i).getDamageSources().magic(), 5);
                                players.get(i).move(MovementType.SELF, new Vec3d(players.get(i).getX() - pos.getX(),
                                        (int) players.get(i).getY() - pos.getY(), (int) players.get(i).getZ() - pos.getZ()));
                                players.get(i).playSoundToPlayer(SoundEvents.ENTITY_GENERIC_BURN, SoundCategory.PLAYERS, 1f, 1f);
                            }
                        }
                    }
                }
            }
        }
    }
    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!(CorruptionHandler.getCorruption(player) > 0) && !player.isCreative()) {
            ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), ModBlocks.TYRINITE_GEM.asItem().getDefaultStack());
        }
        return super.onBreak(world, pos, state, player);
    }

}
