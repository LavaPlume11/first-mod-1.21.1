package me.xander.firstmod.item.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ShoddyWings extends ElytraItem {
    private int flyingTicks = 0;
    private int randomInt = (int)(Math.random() * 1000);
    public ShoddyWings(Settings settings) {
        super(settings);
    }
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity player) {
            ItemStack chestStack = player.getEquippedStack(EquipmentSlot.CHEST);
            if (chestStack.equals(stack) && player.isFallFlying()) {
                if (!player.isCreative() && flyingTicks >= randomInt) {
                    randomInt = (int)(Math.random() * 1000);
                    flyingTicks = 0;
                    player.stopFallFlying();
                }
                flyingTicks++;
            }
            if(stack.getDamage() == stack.getMaxDamage() - 1) {
                stack.damage(1, player, EquipmentSlot.CHEST);
            }
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return false;
    }
}
