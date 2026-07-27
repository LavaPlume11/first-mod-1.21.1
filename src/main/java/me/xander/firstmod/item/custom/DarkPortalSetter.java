package me.xander.firstmod.item.custom;

import me.xander.first_mod;
import me.xander.firstmod.components.ModDataComponentTypes;
import me.xander.firstmod.entity.ModEntities;
import me.xander.firstmod.sound.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DarkPortalSetter extends Item {
    public DarkPortalSetter(Settings settings) {
        super(settings);
    }
    private boolean returned = false;
    private int returnTicks = 100;
    private BlockPos oldPos;
    private final int USE_TIMER = 60;
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if(!world.isClient() && !user.isSneaking() ) {
            if (!stack.get(ModDataComponentTypes.USED)) {
                for (int i = 0; i < 5; i++) {
                    ((ServerWorld) world).spawnParticles(first_mod.DARK_PORTAL_PARTICLE, user.getX(), user.getY() + 0.5, user.getZ(), 50, 0.01, 0.01, 0.01, 0.1);
                }
                world.playSound(null, user.getBlockPos(), ModSounds.PORTAL_BOOM, SoundCategory.AMBIENT);
                ModEntities.DARK_PORTAL.spawn(((ServerWorld) world), user.getBlockPos(), SpawnReason.TRIGGERED);
                stack.set(ModDataComponentTypes.PORTAL_POS, user.getBlockPos());
                stack.set(ModDataComponentTypes.USED, true);
            } else {
                double x = stack.get(ModDataComponentTypes.PORTAL_POS).getX();
                double y = stack.get(ModDataComponentTypes.PORTAL_POS).getY();
                double z = stack.get(ModDataComponentTypes.PORTAL_POS).getZ();
                ((ServerWorld) world).spawnParticles(first_mod.DARK_PORTAL_PARTICLE, user.getX(), user.getY() + 0.5, user.getZ(), 100, 0.0, 0.15, 0.0, 0.2);
                oldPos = user.getBlockPos();
                returned = true;
                world.playSound(null, user.getBlockPos(), ModSounds.PORTAL_BOOM, SoundCategory.AMBIENT);
                user.teleport(x + 0.5, y, z + 0.5, false);
                ((ServerWorld) world).spawnParticles(first_mod.DARK_PORTAL_PARTICLE, user.getX(), user.getY() + 0.5, user.getZ(), 100, 0.01, 0.01, 0.01, 0.2);
            }
        }
        return super.use(world, user, hand);
    }


    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(stack.get(ModDataComponentTypes.USED) == null) {
            stack.set(ModDataComponentTypes.USED, false);
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }
/*
    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
            if (remainingUseTicks <= 0) {
                if(!world.isClient() && !user.isSneaking() ) {
                    if (!stack.get(ModDataComponentTypes.USED)) {
                        for (int i = 0; i < 5; i++) {
                            ((ServerWorld) world).spawnParticles(first_mod.DARK_PORTAL_PARTICLE, user.getX(), user.getY() + 0.5, user.getZ(), 50, 0.01, 0.01, 0.01, 0.1);
                        }
                        world.playSound(null, user.getBlockPos(), ModSounds.PORTAL_BOOM, SoundCategory.AMBIENT);
                        ModEntities.DARK_PORTAL.spawn(((ServerWorld) world), user.getBlockPos(), SpawnReason.TRIGGERED);
                        stack.set(ModDataComponentTypes.PORTAL_POS, user.getBlockPos());
                        stack.set(ModDataComponentTypes.USED, true);
                    } else {
                        double x = stack.get(ModDataComponentTypes.PORTAL_POS).getX();
                        double y = stack.get(ModDataComponentTypes.PORTAL_POS).getY();
                        double z = stack.get(ModDataComponentTypes.PORTAL_POS).getZ();
                        ((ServerWorld) world).spawnParticles(first_mod.DARK_PORTAL_PARTICLE, user.getX(), user.getY() + 0.5, user.getZ(), 100, 0.0, 0.15, 0.0, 0.2);
                        oldPos = user.getBlockPos();
                        returned = true;
                        world.playSound(null, user.getBlockPos(), ModSounds.PORTAL_BOOM, SoundCategory.AMBIENT);
                        user.teleport(x + 0.5, y, z + 0.5, false);
                        ((ServerWorld) world).spawnParticles(first_mod.DARK_PORTAL_PARTICLE, user.getX(), user.getY() + 0.5, user.getZ(), 100, 0.01, 0.01, 0.01, 0.2);
                    }
                }
            }
        super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }
*/
    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        user.sendMessage(Text.of((remainingUseTicks / this.getMaxUseTime(stack, user)) * 100 + "%"));
        super.usageTick(world, user, stack, remainingUseTicks);
    }

    @Override
    public boolean isUsedOnRelease(ItemStack stack) {
        return true;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return USE_TIMER;
    }
}
