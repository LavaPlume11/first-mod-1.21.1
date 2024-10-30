package me.xander.firstmod.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

public class MasterSword extends SwordItem {


    public MasterSword(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if(target.isAlive()) {
            target.dropItem(ModItems.CATALYST);
        }
        super.postDamageEntity(stack, target, attacker);
    }


}




