package me.xander.firstmod.item.custom;

import net.minecraft.entity.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class LightKnife extends SwordItem {
    public LightKnife(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        World world = attacker.getWorld();
        if (!world.isClient) {
            BlockPos pos = target.getBlockPos();
            EntityType.LIGHTNING_BOLT.spawn((ServerWorld) world, pos, SpawnReason.TRIGGERED);

        }



    }


}
