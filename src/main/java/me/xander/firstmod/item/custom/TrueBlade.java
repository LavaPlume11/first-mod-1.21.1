package me.xander.firstmod.item.custom;

import com.mojang.authlib.GameProfile;
import me.xander.firstmod.block.ModBlocks;
import me.xander.firstmod.block.entity.custom.BridgeBlockEntity;
import me.xander.firstmod.components.ModDataComponentTypes;
import me.xander.firstmod.components.PlayerOwnerComponent;
import me.xander.firstmod.util.ModKeyBindings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UserCache;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

public class TrueBlade extends SwordItem {

    public TrueBlade(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }
    private boolean defenseMode = true;
    private int radius = 10;
    private int cooldown = 0;
    public final int MAX_COOLDOWN = 400;
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
       ItemStack stack = user.getStackInHand(hand);

       if (!world.isClient()) {
           PlayerOwnerComponent ownerComponent = stack.get(ModDataComponentTypes.PLAYER_OWNER);
           if (user.isSneaking()) {
               if ((ownerComponent == null || !ownerComponent.isOwned())) {
                   String name = getPlayerNameFromCache(Objects.requireNonNull(world.getServer()), user.getUuid());
                   stack.set(ModDataComponentTypes.PLAYER_OWNER, PlayerOwnerComponent.owned(user.getUuid(), name));
                   assert ownerComponent != null;
                   user.sendMessage(Text.literal(name + " is now bound to the Blade"));
               } else {
                   if (!defenseMode)
                       makeWall(world,user, radius,200);

               }
           }
       }
        return super.use(world, user, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (stack.get(ModDataComponentTypes.USED) != null) {
            defenseMode = !Boolean.TRUE.equals(stack.get(ModDataComponentTypes.USED));
        } else  {
            stack.set(ModDataComponentTypes.USED, false);
            defenseMode = true;
        }
        if (entity instanceof PlayerEntity player) {
            if (cooldown > 0) {
                cooldown--;
            }
            if (player.getStackInHand(Hand.MAIN_HAND) == stack) {
                if (player.isSneaking() && defenseMode) {
                    makeShield(world, player, 1);
                }
                if (world.isClient() && ModKeyBindings.C_KEY_BINDING.wasPressed()) {
                    if (cooldown <= 0) {
                        if (defenseMode) {
                            stack.set(ModDataComponentTypes.USED, true);
                            player.getInventory().updateItems();
                            player.sendMessage(Text.literal("Mode: Offensive").formatted(Formatting.GREEN), true);
                        } else {
                            stack.set(ModDataComponentTypes.USED, false);
                            player.getInventory().updateItems();
                            player.sendMessage(Text.literal("Mode: Defensive").formatted(Formatting.BLUE), true);
                        }
                        cooldown = MAX_COOLDOWN;
                    } else {
                        player.sendMessage(Text.literal("Mode Switch on cooldown for " + Math.round((float) (cooldown / 20) / 2) + " seconds")
                                .formatted(Formatting.RED), true);
                    }
                }
            }


        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    private void makeShield(World world, PlayerEntity user, int timer) {
        if (!world.isClient() && defenseMode) {
            BlockPos center = user.getBlockPos();
            placeWall(world,center.east(), timer);
            placeWall(world,center.east().up(), timer);
            placeWall(world,center.west(), timer);
            placeWall(world,center.west().up(), timer);
            placeWall(world,center.north(), timer);
            placeWall(world,center.north().up(), timer);
            placeWall(world,center.south(), timer);
            placeWall(world,center.south().up(), timer);
        }
    }

    private void makeWall(World world, PlayerEntity user, int radius, int timer) {
       if (!world.isClient()) {
           BlockPos center = user.getBlockPos();
           for (int i = 0; i < 360; i++) {
               double angle = Math.toRadians(i);
               double x = center.getX() + radius * Math.cos(angle);
               double z = center.getZ() + radius * Math.sin(angle);
               BlockPos target = new BlockPos((int) x, center.getY(), (int) z);
               placeWall(world,target.up().up(), timer);
               placeWall(world,target.up(), timer);
               placeWall(world,target, timer);
               while (world.getBlockState(target.down()).isReplaceable()) {
                   BlockPos newTarget = target.down();
                   placeWall(world, newTarget, timer);
                   target = newTarget;
               }

           }
       }
    }

    private void placeWall(World world,BlockPos target, int timer) {
        if (world.getBlockState(target).isReplaceable()) {
            world.setBlockState(target, ModBlocks.BRIDGE_BLOCK.getDefaultState());
            BridgeBlockEntity blockEntity = (BridgeBlockEntity) world.getBlockEntity(target);
            blockEntity.setTimer(timer);

        }
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity player) {
            int damage = calculateTrueBladeDamage(stack, player);
            target.damage(target.getRecentDamageSource(), damage);
        }
        return super.postHit(stack, target, attacker);
    }

    /*@Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity player) {
        int damage = calculateTrueBladeDamage(stack, player);
        target.damage(new DamageSource(target.getWorld().getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(DamageTypes.PLAYER_ATTACK)), damage);
        }
        super.postDamageEntity(stack, target, attacker);
    }*/

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        String shield;
        String radiusLine;
        if (defenseMode) {
            shield = "Defensive";
            radiusLine = null;
        } else {
            shield = "Offensive";
            radiusLine = "Arena Radius: " + getRadius();
        }

        PlayerOwnerComponent ownerComponent = stack.get(ModDataComponentTypes.PLAYER_OWNER);
        if (ownerComponent != null && ownerComponent.isOwned()) {
            tooltip.add(Text.literal("Owner: " + ownerComponent.ownerName().get()).formatted(Formatting.RED).formatted(Formatting.ITALIC));
            tooltip.add(Text.literal("Mode: " + shield).formatted(Formatting.GREEN).formatted(Formatting.ITALIC));
            if (radiusLine != null)
                tooltip.add(Text.literal(radiusLine).formatted(Formatting.LIGHT_PURPLE).formatted(Formatting.ITALIC));

        } else {
            tooltip.add(Text.literal("Unbound"));
        }

    }

    public static String getPlayerNameFromCache(MinecraftServer server, UUID uuid) {
        UserCache userCache = server.getUserCache();
        if (userCache != null) {
            Optional<GameProfile> profile = userCache.getByUuid(uuid);
            if (profile.isPresent()) {
                return profile.get().getName();
            }
        }
        return null;
    }
    public int calculateTrueBladeDamage(ItemStack stack, PlayerEntity user) {
        PlayerOwnerComponent ownerComponent = stack.get(ModDataComponentTypes.PLAYER_OWNER);
        if (ownerComponent != null) {
            if (ownerComponent.isOwned()) {
                if (ownerComponent.ownerName().get().equals(user.getName().getLiteralString())) {
                    return 30;
                }

                return 0;
            }
            return 10;
        }
        return 10;
    }
    public void setRadius(int newRadius) {
        radius = newRadius;
    }
    public int getRadius() {
        return radius;
    }
}
