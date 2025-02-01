package me.xander.firstmod.block.custom;

import me.xander.firstmod.block.entity.custom.DisplayBlockEntity;
import me.xander.firstmod.entity.ModEntities;
import me.xander.firstmod.entity.custom.DisplayBlockEntityEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class SoundBlock extends Block {
    public SoundBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        world.playSound(player, pos, SoundEvents.BLOCK_NOTE_BLOCK_DIDGERIDOO.value(), SoundCategory.BLOCKS);
        if (world.getBlockEntity(pos) instanceof DisplayBlockEntity displayBlockEntity) {
            if (displayBlockEntity.isEmpty() && player.isSneaking() && !world.isClient()) {
                Entity entity = null;
                List<DisplayBlockEntityEntity> entities = world.getEntitiesByType(ModEntities.DISPLAY_ENTITY, new Box(pos), displayBlockEntityEntity -> true);
                if (entities.isEmpty()) {
                    entity = ModEntities.DISPLAY_ENTITY.spawn((ServerWorld) world, pos, SpawnReason.TRIGGERED);
                } else {
                    entity = entities.get(0);
                }
                player.startRiding(entity);
            }

        }
        return ActionResult.SUCCESS;
    }
}