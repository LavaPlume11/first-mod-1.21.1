package me.xander.firstmod.item.custom;

import me.xander.first_mod;
import me.xander.firstmod.components.ModDataComponentTypes;
import me.xander.firstmod.entity.custom.FireBlastProjectileEntity;
import me.xander.firstmod.entity.custom.TomahawkProjectileEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class LavaSword extends SwordItem {
    private int extendCount ;
    private int retractCount;
    public LavaSword(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
        extendCount = 0;
        retractCount = 0;
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return 16711680;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (itemStack.get(ModDataComponentTypes.LAVA_STATE) != null) {
            if (user.isSneaking() && itemStack.get(ModDataComponentTypes.LAVA_STATE) == 4f) {
                retractCount = 0;
                startRetracting(itemStack);
                return TypedActionResult.success(itemStack, world.isClient());
            } else if (itemStack.get(ModDataComponentTypes.LAVA_STATE) == 4f) {
                if (!user.isOnFire() && !user.isTouchingWaterOrRain()) {
                    world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_FIRECHARGE_USE,
                            SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
                    if (!world.isClient) {
                        FireBlastProjectileEntity diceProjectile = new FireBlastProjectileEntity(world, user);
                        diceProjectile.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 3.0f, 0f);
                        world.spawnEntity(diceProjectile);
                        user.setOnFireFor(3.0f);
                    }

                    user.incrementStat(Stats.USED.getOrCreateStat(this));
                }
            } else
            if (extendCount < 18 && (itemStack.get(ModDataComponentTypes.LAVA_STATE) == 1f || itemStack.get(ModDataComponentTypes.LAVA_STATE) == null)) {
                extendCount = 0;
                startExtending(itemStack);
            }

        }
        return TypedActionResult.success(itemStack,world.isClient());
    }

    private void startExtending(ItemStack stack) {
        stack.set(ModDataComponentTypes.LAVA_STATE, 2f);

    }
    private void stopExtending(ItemStack stack) {
        stack.set(ModDataComponentTypes.LAVA_STATE, 4f);
    }
    private void startRetracting(ItemStack stack) {
        stack.set(ModDataComponentTypes.LAVA_STATE, 3f);
    }
    private void stopRetracting(ItemStack stack) {
        stack.set(ModDataComponentTypes.LAVA_STATE, 1f);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (stack.get(ModDataComponentTypes.LAVA_STATE) == null) {
            stack.set(ModDataComponentTypes.LAVA_STATE, 1f);
        }
            if (stack.get(ModDataComponentTypes.LAVA_STATE) == 2f) {
                extendCount++;
            }
            if (stack.get(ModDataComponentTypes.LAVA_STATE) == 3f) {
                retractCount++;
            }
            if (extendCount >= 18) {
                stopExtending(stack);
                extendCount = 0;
            }
            if (retractCount >= 18) {
                stopRetracting(stack);
                retractCount = 0;
            }

        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
