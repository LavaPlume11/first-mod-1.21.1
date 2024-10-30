package me.xander.firstmod.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.BowItem;
import org.jetbrains.annotations.Nullable;

public class NetherBow extends BowItem {
    @Override
    protected void shoot(LivingEntity shooter, ProjectileEntity projectile, int index, float speed, float divergence, float yaw, @Nullable LivingEntity target) {
        super.shoot(shooter, projectile, index, speed, divergence, yaw, target);
    }

    public NetherBow(Settings settings) {
        super(settings);
    }
}
