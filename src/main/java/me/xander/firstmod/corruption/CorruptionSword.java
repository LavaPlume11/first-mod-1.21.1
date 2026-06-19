package me.xander.firstmod.corruption;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;

public class CorruptionSword extends SwordItem {
    public CorruptionSword(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity player) {
            int damage = calculateCorruptionDamage(stack, player, target);
            target.damage(target.getDamageSources().wither(), damage);
        }
        return super.postHit(stack, target, attacker);
    }

    private int calculateCorruptionDamage(ItemStack stack, PlayerEntity player, LivingEntity target) {
        if (target instanceof PlayerEntity targetPlayer && CorruptionHandler.getCorruption(targetPlayer) > 1) {
            return 0;
        }
        return CorruptionHandler.getCorruption(player) * 2;
    }

    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
            if (attacker instanceof PlayerEntity player && !attacker.getWorld().isClient()) {
                if (target.isDead() && CorruptionHandler.getCorruption(player) <= 40) {
                    if (target.isBaby()) {
                        CorruptionHandler.addCorruption((ServerPlayerEntity) player, 1);
                    }
                }

        }
        super.postDamageEntity(stack, target, attacker);
    }
}
