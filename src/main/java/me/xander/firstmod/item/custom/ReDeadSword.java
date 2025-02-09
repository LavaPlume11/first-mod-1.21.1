package me.xander.firstmod.item.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ReDeadSword extends SwordItem {


    public ReDeadSword(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.getType().isIn(EntityTypeTags.UNDEAD)) {
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1, 1), attacker);
            World world = attacker.getWorld();
            if (!world.isClient) {
                BlockPos pos = target.getBlockPos();
                EntityType.LIGHTNING_BOLT.spawn((ServerWorld) world, pos, SpawnReason.TRIGGERED);
            }

        }

    }
}





