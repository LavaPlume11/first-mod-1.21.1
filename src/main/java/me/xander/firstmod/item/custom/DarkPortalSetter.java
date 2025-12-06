package me.xander.firstmod.item.custom;

import me.xander.first_mod;
import me.xander.firstmod.components.ModDataComponentTypes;
import me.xander.firstmod.entity.ModEntities;
import me.xander.firstmod.sound.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
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
       /* if(!world.isClient()) {
            if (returned && returnTicks > 0) {
                double x = oldPos.getX();
                double y = oldPos.getY();
                double z = oldPos.getZ();
                ((ServerWorld) world).spawnParticles(first_mod.DARK_PORTAL_PARTICLE, x, y + 0.5, z, 50, 0.0, 0.15, 0.0, 0.2);
                returnTicks--;
            } else {
                returned = false;
                returnTicks = 100;
            }
        }
        */
        super.inventoryTick(stack, world, entity, slot, selected);
    }

}
