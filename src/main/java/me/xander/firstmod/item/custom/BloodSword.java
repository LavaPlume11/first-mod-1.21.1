package me.xander.firstmod.item.custom;

import me.xander.first_mod;
import me.xander.firstmod.components.ModDataComponentTypes;
import me.xander.firstmod.particle.BloodParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BloodSword extends SwordItem {
    public BloodSword(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
        settings.component(ModDataComponentTypes.DEFAULT_INT, 0);
    }
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!target.isAlive()) {
            stack.set(ModDataComponentTypes.DEFAULT_INT, stack.getOrDefault(ModDataComponentTypes.DEFAULT_INT, 0) + 1);
            if (stack.getOrDefault(ModDataComponentTypes.DEFAULT_INT, 0) % 10 == 0 && attacker instanceof PlayerEntity player && player.getInventory().contains(Items.GLASS_BOTTLE.getDefaultStack())) {
                ItemScatterer.spawn(attacker.getWorld(), target.getX(), target.getY(), target.getZ(), new ItemStack(ModItems.BLOOD_BOTTLE));
                player.getInventory().removeStack(player.getInventory().getSlotWithStack(Items.GLASS_BOTTLE.getDefaultStack()), 1);
            }
            BlockPos pos = target.getBlockPos().down();
            World world = attacker.getWorld();
            int count;
            if (target.getMaxHealth() <= 200) {
                count = Math.round(target.getMaxHealth());
            } else {
                count = 200;
            }
            if (target.getMaxHealth() <= 20) {
                ((ServerWorld) world).spawnParticles(first_mod.BLOOD_PARTICLE, pos.getX() + 0.5f, pos.getY()
                        + 2.0f, pos.getZ() + 0.5f, count * 50, 0.1, 0.5, 0.1, 0.5);
            } else {
                ((ServerWorld) world).spawnParticles(first_mod.BLOOD_PARTICLE, pos.getX() + 0.5f, pos.getY()
                        + 2.0f, pos.getZ() + 0.5f, count * 300, 0.3, 1.5, 0.3, 2.1);
            }
            return true;
        } else {
            int count = 20;
            BlockPos pos = target.getBlockPos().down();
            World world = attacker.getWorld();
            ((ServerWorld) world).spawnParticles(first_mod.BLOOD_PARTICLE, pos.getX() + 0.5f, pos.getY()
                    + 2.0f, pos.getZ() + 0.5f, count, 0.1, 0.1, 0.1, 2.1);
        return true;
    }
    }
}
