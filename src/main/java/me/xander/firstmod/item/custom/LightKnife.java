package me.xander.firstmod.item.custom;

import net.minecraft.entity.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundEvents;

public class LightKnife extends SwordItem {
    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        attacker.playSound(SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, 50.0f, 1.0f);
        target.forwardSpeed = 50;

    }

    public LightKnife(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }
}
