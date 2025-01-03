package me.xander.firstmod.item.custom;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Equipment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

import java.util.List;

public class ThankHat extends Item implements Equipment {

    public ThankHat(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.of(""));
        tooltip.add(Text.of("\"Spiffy\""));
    }

    @Override
    public RegistryEntry<SoundEvent> getEquipSound() {
        return RegistryEntry.of(SoundEvents.ENTITY_CHICKEN_AMBIENT);
    }

    @Override
    public EquipmentSlot getSlotType() {
        return EquipmentSlot.HEAD;
    }
}
