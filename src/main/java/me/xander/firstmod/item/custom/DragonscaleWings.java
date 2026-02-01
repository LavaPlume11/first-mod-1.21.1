package me.xander.firstmod.item.custom;

import me.xander.firstmod.components.ModDataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class DragonscaleWings extends ElytraItem {
    public EndCrystalEntity connectedCrystal;
    public boolean doCrystalFirstPerson = true;

    public DragonscaleWings(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!ElytraItem.isUsable(stack)) {
            stack.set(ModDataComponentTypes.BROKEN, true);
        } else {
            stack.set(ModDataComponentTypes.BROKEN, false);
        }
        tickWithEndCrystals(stack, (LivingEntity) entity, world);



        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return false;
    }

    private void tickWithEndCrystals(ItemStack stack, LivingEntity entity, World world) {
        if (stack == entity.getEquippedStack(EquipmentSlot.CHEST)) {
            if (this.connectedCrystal != null) {
                if (this.connectedCrystal.isRemoved()) {
                    this.connectedCrystal = null;
                } else if (stack.getDamage() > 0) {
                    stack.setDamage(stack.getDamage() - 1);
                }
            }

                List<EndCrystalEntity> list = world.getNonSpectatingEntities(EndCrystalEntity.class, entity.getBoundingBox().expand((double) 32.0F));
                EndCrystalEntity endCrystalEntity = null;
                double d = Double.MAX_VALUE;

                for (EndCrystalEntity endCrystalEntity2 : list) {
                    double e = endCrystalEntity2.squaredDistanceTo(entity);
                    if (e < d) {
                        d = e;
                        endCrystalEntity = endCrystalEntity2;
                    }
                }

                this.connectedCrystal = endCrystalEntity;

        } else if (connectedCrystal != null) {
            doCrystalFirstPerson = false;
        }
    }

    public void setDoCrystalFirstPerson(boolean doCrystalFirstPerson) {
        this.doCrystalFirstPerson = doCrystalFirstPerson;
    }

}
