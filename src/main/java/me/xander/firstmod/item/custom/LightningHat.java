package me.xander.firstmod.item.custom;

import me.xander.first_mod;
import me.xander.firstmod.util.ModKeyBindings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;

import java.util.List;

public class LightningHat extends ArmorItem {
    public LightningHat(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);
    }
    private int sparkTimer = 1;

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (ModKeyBindings.ALT_KEY_BINDING.isPressed()) {
            doLightning(stack, world, entity);
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }
    public void doLightning(ItemStack stack, World world, Entity entity) {
        if (!world.isClient) {
            if (entity instanceof PlayerEntity player) {
                BlockPos pos = player.getBlockPos();
                if (player.getInventory().getArmorStack(3) == stack) {
                    if ((world.isThundering( ) || world.isRaining()) && (long)world.random.nextInt(200) <= sparkTimer  && pos.getY() >= world.getTopY(Heightmap.Type.WORLD_SURFACE, pos.getX(), pos.getZ()) - 1) {
                        ((ServerWorld) player.getWorld()).spawnParticles(ParticleTypes.ELECTRIC_SPARK, player.getBlockPos().getX() + 0.5f, player.getBlockPos().getY()
                                + 2.0f,player.getBlockPos().getZ() + 0.5f,15,1.1,1.1,1.1,0.1);
                        sparkTimer++;
                        if (sparkTimer >= 200) {
                            EntityType.LIGHTNING_BOLT.spawn((ServerWorld) world, pos, SpawnReason.TRIGGERED);
                            sparkTimer = 1;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.of("Charge: " + sparkTimer + " / " + 200));
        super.appendTooltip(stack, context, tooltip, type);
    }
}
