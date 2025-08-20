package me.xander.firstmod.item.custom;

import me.xander.first_mod;
import me.xander.firstmod.components.ModDataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class StaffItem extends Item {
    public StaffItem(Settings settings) {
        super(settings);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            ItemStack thisStack = user.getStackInHand(hand);
            ItemStack stack = user.getStackInHand(Hand.OFF_HAND);
            if (stack.isOf(Items.FIRE_CHARGE)) {
                thisStack.set(ModDataComponentTypes.STAFF_STATE, 1f);
            } else if (stack.isOf(Items.BLUE_ICE)) {
                thisStack.set(ModDataComponentTypes.STAFF_STATE, 2f);
            } else if (stack.isOf(Items.FEATHER)) {
                thisStack.set(ModDataComponentTypes.STAFF_STATE, 3f);
            } else {
                doEffectForState(world, user);
            }
            return TypedActionResult.success(user.getStackInHand(hand));
        } else {
            return TypedActionResult.pass(user.getStackInHand(hand));
        }
    }

    private void doEffectForState(World world, PlayerEntity user) {
        BlockPos playerPos = user.getBlockPos();
        ItemStack thisStack = user.getStackInHand(Hand.MAIN_HAND);
        if (user.getStackInHand(Hand.MAIN_HAND).get(ModDataComponentTypes.STAFF_STATE)!= null) {
            if (thisStack.get(ModDataComponentTypes.STAFF_STATE) == 1f) {
                Vec3d vec3d = user.getRotationVec(1.0F);
                double x = user.getX() - (user.getX() - vec3d.x * (double) 4.0F);
                double y = user.getY() - (user.getY() - vec3d.y * (double) 4.0F);
                double z = user.getZ() - (user.getZ() - vec3d.z * (double) 4.0F);
                Vec3d vec3d2 = new Vec3d(x, y, z);
                FireballEntity fireballEntity = new FireballEntity(world, user, vec3d2.normalize(), 5);
                world.spawnEntity(fireballEntity);

            } else if (thisStack.get(ModDataComponentTypes.STAFF_STATE) == 2f) {
                    user.sendMessage(Text.of("loser"));
            } else if (thisStack.get(ModDataComponentTypes.STAFF_STATE) == 3f) {
                user.spawnSweepAttackParticles();
            }
        }
    }

}
