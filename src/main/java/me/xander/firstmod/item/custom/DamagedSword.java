package me.xander.firstmod.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.List;

public class DamagedSword extends SwordItem {


    public DamagedSword(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
       tooltip.add(Text.of("An old damaged sword that looks as if it could snap in half at any moment."));
        tooltip.add(Text.of("Perhaps you can repair it with something"));
    }


}
