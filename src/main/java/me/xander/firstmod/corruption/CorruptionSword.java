package me.xander.firstmod.corruption;

import me.xander.first_mod;
import me.xander.firstmod.components.ModDataComponentTypes;
import me.xander.firstmod.util.mixin.PlayerEntityAccess;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class CorruptionSword extends SwordItem {
    public CorruptionSword(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity player) {
            int damage = calculateCorruptionDamage(stack, player, target);
            target.damage(target.getDamageSources().wither(), damage);
        }
        return super.postHit(stack, target, attacker);
    }

    private int calculateCorruptionDamage(ItemStack stack, PlayerEntity player, LivingEntity target) {
        if (target instanceof PlayerEntity targetPlayer && CorruptionHandler.getCorruption(targetPlayer) > 1) {
            return 0;
        }
        return CorruptionHandler.getCorruption(player) / 3;
    }

    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
            if (attacker instanceof PlayerEntity player && !attacker.getWorld().isClient()) {
                if (target.isDead()) {
                    List<String> names = ((PlayerEntityAccess) player).first_mod_template_1_21_1$getCorruptedKills();
                    if (!names.contains(target.getName().getString())) {
                        names.add(target.getName().getString());
                        ((PlayerEntityAccess) player).first_mod_template_1_21_1$setCorruptedKills(names);
                        stack.set(ModDataComponentTypes.DEFAULT_INT, names.size());
                        CorruptionHandler.addCorruption((ServerPlayerEntity) player, 3);
                    }
                }

        }
        super.postDamageEntity(stack, target, attacker);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal("Unique Kills: " + stack.getOrDefault(ModDataComponentTypes.DEFAULT_INT, 0)).formatted(Formatting.RED));
        super.appendTooltip(stack, context, tooltip, type);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
