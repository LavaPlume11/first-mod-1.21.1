package me.xander.firstmod.item.custom;

import me.xander.first_mod;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class TrueBlade extends SwordItem {
    @Nullable
    private UUID owner;

    public TrueBlade(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }
/*
    @Override
    public void onCraftByPlayer(ItemStack stack, World world, PlayerEntity player) {
        owner = player.getUuid();
        first_mod.LOGGER.info("It worked!");
        if (owner != null)
            first_mod.LOGGER.info(owner.toString());
        super.onCraftByPlayer(stack, world, player);
    }
    */

}
