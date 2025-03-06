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
         double y = user.getYaw();
         double x = user.getPitch();
          Vec3d eye = user.getEyePos();
        user.addVelocity(eye);
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    public DashSword(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }
}
