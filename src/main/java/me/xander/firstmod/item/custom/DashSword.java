package me.xander.firstmod.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class DashSword extends SwordItem {
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        Vec3d vec3d = user.getRotationVec(1.0F);
        double x = user.getX() - (user.getX() - vec3d.x * (double)4.0F);
        double y = user.getY() - (user.getY() - vec3d.y * (double)4.0F);
        double z = user.getZ() - (user.getZ() - vec3d.z * (double)4.0F);
        Vec3d vec3d2 = new Vec3d(x, y, z);
        user.addVelocity(vec3d2);
        user.getItemCooldownManager().set(this, 60);
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    public DashSword(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }
}
