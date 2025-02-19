package me.xander.firstmod.block.custom;

import com.mojang.serialization.MapCodec;
import me.xander.firstmod.block.entity.custom.DisplayBlockEntity;
import me.xander.firstmod.entity.ModEntities;
import me.xander.firstmod.entity.custom.DisplayBlockEntityEntity;
import me.xander.firstmod.item.custom.ModItems;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DisplayBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final MapCodec<DisplayBlock> CODEC = DisplayBlock.createCodec(DisplayBlock::new);

    public DisplayBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }


    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DisplayBlockEntity(pos, state);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof DisplayBlockEntity) {
                ItemScatterer.spawn(world, pos, ((DisplayBlockEntity) blockEntity));
                world.updateComparators(pos, this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }

    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos,
                                             PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.getBlockEntity(pos) instanceof DisplayBlockEntity displayBlockEntity) {
            if (displayBlockEntity.isEmpty() && !stack.isEmpty()) {
                displayBlockEntity.setStack(0,stack);
                world.playSound(player,pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS,1f,2f);
                stack.decrement(1);
                displayBlockEntity.markDirty();
                world.updateListeners(pos,state,state,0);
            } else if (stack.isEmpty() && !player.isSneaking()) {
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

}

