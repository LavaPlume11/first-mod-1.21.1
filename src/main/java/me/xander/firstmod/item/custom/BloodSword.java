package me.xander.firstmod.item.custom;

import me.xander.first_mod;
import me.xander.firstmod.particle.BloodParticle;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BloodSword extends SwordItem {
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!target.isAlive()) {
            BlockPos pos = target.getBlockPos().down();
            World world = attacker.getWorld();
            int count;
            if (target.getMaxHealth() <= 20) {
                count = 20;
            } else {
                count = 100;
            }
            ((ServerWorld) world).spawnParticles(first_mod.BLOOD_PARTICLE, pos.getX() + 0.5f, pos.getY()
                    + 2.0f, pos.getZ() + 0.5f, count * 300, 0.3, 1.5, 0.3, 2.1);

            return true;
        } else {
            int count = 20;
            BlockPos pos = target.getBlockPos().down();
            World world = attacker.getWorld();
            ((ServerWorld) world).spawnParticles(first_mod.BLOOD_PARTICLE, pos.getX() + 0.5f, pos.getY()
                    + 2.0f, pos.getZ() + 0.5f, count, 0.1, 0.1, 0.1, 2.1);
        return true;
    }
    }

    public BloodSword(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }
}
