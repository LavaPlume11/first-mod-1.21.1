package me.xander.firstmod.item.custom;

import me.xander.firstmod.corruption.CorruptionHandler;
import me.xander.firstmod.corruption.CorruptionVines;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class CorruptionRepeller extends Item {
    public CorruptionRepeller(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (!entity.getWorld().isClient() && entity instanceof PlayerEntity player && CorruptionHandler.getCorruption(player) > 0 && CorruptionHandler.getCorruption(user) <= 0) {
            CorruptionHandler.subtractCorruption(((ServerPlayerEntity) player), 1);
            player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.PLAYERS, 1f, 1f);
            player.damage(player.getDamageSources().magic(), 8);
            stack.decrement(1);
        }
        return super.useOnEntity(stack, user, entity, hand);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.isSneaking()) {
            if (!user.getWorld().isClient() && CorruptionHandler.getCorruption(user) > 0) {
                CorruptionHandler.subtractCorruption(((ServerPlayerEntity) user), 1);
                user.getWorld().playSound(null, user.getBlockPos(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.PLAYERS, 1f, 1f);
                user.damage(user.getDamageSources().magic(), 8);
                if(!user.isCreative() && isConsumed())
                    user.getStackInHand(hand).decrement(1);
            }
        }
        return super.use(world, user, hand);
    }
    public boolean isConsumed() {
        return true;
    }
}
