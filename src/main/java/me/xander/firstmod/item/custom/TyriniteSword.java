package me.xander.firstmod.item.custom;

import me.xander.first_mod;
import me.xander.firstmod.corruption.CorruptionHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class TyriniteSword extends SwordItem {
    public TyriniteSword(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity player && CorruptionHandler.getCorruption(player) > 0 && player.getWeaponStack() == stack) {
           // world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_GENERIC_BURN, SoundCategory.PLAYERS);
           // player.getInventory().removeStack(slot);
            //player.dropItem(stack, true);
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
            int damage = calculatePureDamage(stack, attacker, target);
            if (damage > 0) {
                target.damage(target.getDamageSources().inFire(), damage);
            }
        return super.postHit(stack, target, attacker);
    }

    private int calculatePureDamage(ItemStack stack, LivingEntity attacker, LivingEntity target) {
        if (target instanceof PlayerEntity targetPlayer && CorruptionHandler.getCorruption(targetPlayer) > 0) {
            target.getWorld().playSound(null, target.getBlockPos(), SoundEvents.ENTITY_GENERIC_BURN, SoundCategory.PLAYERS);
            return CorruptionHandler.getCorruption(targetPlayer) / 2;
        }
        return 0;
    }

    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        World world = attacker.getWorld();
        int random = (int) (Math.random() * 100);
        if (!world.isClient() && target instanceof PlayerEntity player) {
            first_mod.LOGGER.info(String.valueOf(random));
            if (random <= CorruptionHandler.getCorruption(player)) {
                BlockPos pos = target.getBlockPos();
                EntityType.LIGHTNING_BOLT.spawn((ServerWorld) world, pos, SpawnReason.TRIGGERED);
            }
        }
        super.postDamageEntity(stack, target, attacker);
    }
}
