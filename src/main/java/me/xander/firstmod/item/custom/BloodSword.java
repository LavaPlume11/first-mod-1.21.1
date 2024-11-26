package me.xander.firstmod.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BloodSword extends SwordItem {
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        BlockPos pos = target.getBlockPos().down();
        World world = attacker.getWorld();
        int offset = (int) (target.getHealth() % 5);
        ((ServerWorld) world).spawnParticles(ParticleTypes.DRIPPING_DRIPSTONE_LAVA, pos.getX() + 0.5f, pos.getY()
                + 2.0f ,pos.getZ()+0.5f,offset * 50,0.1,0.5,0.1,1.1);

        return true;
    }

    public BloodSword(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }
}
