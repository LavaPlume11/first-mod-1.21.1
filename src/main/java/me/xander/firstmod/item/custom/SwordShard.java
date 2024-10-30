package me.xander.firstmod.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.List;

public class SwordShard extends Item {
    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
     tooltip.add(Text.of("A dusty old shard that looks like it was once part of something."));
        tooltip.add(Text.of(""));
        tooltip.add(Text.of("It feels lighter than air"));
    }

    public SwordShard(Settings settings) {
        super(settings);
    }
}
