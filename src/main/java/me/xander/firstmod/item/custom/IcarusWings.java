package me.xander.firstmod.item.custom;

import me.xander.first_mod;
import me.xander.firstmod.components.ModDataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;

public class IcarusWings extends ElytraItem {
    public IcarusWings(Settings settings) {
        super(settings);
    }

    private int flyingTicks = 0;
    private boolean isBarColored = false;
    private int barColor = super.getItemBarColor(this.getDefaultStack());
    private boolean doParticles = true;

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!isBarColored) {
            barColor = super.getItemBarColor(this.getDefaultStack());
        }
        if (!ElytraItem.isUsable(stack)) {
            stack.set(ModDataComponentTypes.BROKEN, true);
        } else {
            stack.set(ModDataComponentTypes.BROKEN, false);
        }

        if (entity instanceof PlayerEntity player) {
            ItemStack chestStack = player.getEquippedStack(EquipmentSlot.CHEST);
            if (chestStack.equals(stack)) {
                if (!player.isCreative()) {
                    if (player.isInLava()) {
                        chestStack.setDamage(chestStack.getDamage() + 3);
                        isBarColored = true;
                        barColor = 16756224;
                    } else
                    if (player.isOnFire()) {
                        if (!player.isFireImmune()) {
                            chestStack.setDamage(chestStack.getDamage() + 1);
                            isBarColored = true;
                            barColor = 16711680;
                        }
                    } else
                    if (player.isTouchingWaterOrRain()) {
                        chestStack.setDamage(chestStack.getDamage() + 2);
                        isBarColored = true;
                        barColor = 350202;
                    } else {
                        barColor = super.getItemBarColor(this.getDefaultStack());
                        isBarColored = false;

                    }
                } else {
                    barColor = super.getItemBarColor(this.getDefaultStack());
                    isBarColored = false;
                }

                    if (!world.isClient()) {
                        int durability = chestStack.getMaxDamage() - chestStack.getDamage();
                      if ((durability == 180) || (durability == 144) || (durability == 108) || (durability == 72) || (durability == 36) || (durability == 2)) {
                          if (doParticles) {
                              ((ServerWorld) world).spawnParticles(first_mod.STICKY_FEATHER_PARTICLE, player.getX(), player.getY(), player.getZ(), 1, 0, 0.0, 0.0, 0.1);
                                doParticles = false;
                          }
                          }
                    }

                BlockPos pos = player.getBlockPos();
                int yPos = pos.getY();
                if ((yPos > 125 || yPos < 70) && player.isFallFlying() && yPos >= world.getTopY(Heightmap.Type.WORLD_SURFACE, pos.getX(), pos.getZ())) {
                    if (!player.getWorld().isClient) {
                        if (flyingTicks >= 7) {
                            if (!player.isCreative())
                                stack.setDamage(stack.getDamage() + 1);
                            doParticles = true;
                            flyingTicks = 0;
                        }
                    }
                    flyingTicks++;
                    super.inventoryTick(stack, world, entity, slot, selected);
                    return;
                }
                flyingTicks = 0;
            } else {
                barColor = super.getItemBarColor(this.getDefaultStack());
                isBarColored = false;
            }
            if (stack.getDamage() >= 216) {
                stack.setDamage(215);
            }

        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.isOf(ModItems.STICKY_FEATHER);
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        if (isBarColored) {
            return barColor;
        } else {
            return super.getItemBarColor(stack);
        }
    }
}
