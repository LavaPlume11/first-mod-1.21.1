package me.xander.firstmod.item.custom;

import com.mojang.authlib.GameProfile;
import me.xander.first_mod;
import me.xander.firstmod.components.ModDataComponentTypes;
import me.xander.firstmod.components.PlayerOwnerComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UserCache;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class TrueBlade extends SwordItem {

    public TrueBlade(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
       ItemStack stack = user.getStackInHand(hand);

       if (!world.isClient()) {
           PlayerOwnerComponent ownerComponent = stack.get(ModDataComponentTypes.PLAYER_OWNER);
           if ((ownerComponent == null || !ownerComponent.isOwned()) && user.isSneaking()) {
               String name = getPlayerNameFromCache(Objects.requireNonNull(world.getServer()), user.getUuid());
               stack.set(ModDataComponentTypes.PLAYER_OWNER,PlayerOwnerComponent.owned(user.getUuid(), name));
               assert ownerComponent != null;
               user.sendMessage(Text.literal(name + " is now bound to the Blade"));
           }
       }
        return super.use(world, user, hand);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity player) {
            int damage = calculateTrueBladeDamage(stack, player);
            target.damage(new DamageSource(target.getWorld().getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(DamageTypes.PLAYER_ATTACK)), damage);
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

        PlayerOwnerComponent ownerComponent = stack.get(ModDataComponentTypes.PLAYER_OWNER);
        if (ownerComponent != null && ownerComponent.isOwned()) {
            tooltip.add(Text.literal("Owner: " + ownerComponent.ownerName().get()).formatted(Formatting.RED).formatted(Formatting.ITALIC));
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
    public boolean hasMatchingPlayerName(World world, PlayerEntity player, ItemStack stack) {
        if (!world.isClient()) {
            String name = getPlayerNameFromCache(world.getServer(), player.getUuid());
            if (name != null) {
                PlayerOwnerComponent ownerComponent = stack.get(ModDataComponentTypes.PLAYER_OWNER);
                if (ownerComponent != null) {
                    if (ownerComponent.ownerName().isPresent()) {
                        return ownerComponent.ownerName().get().equals(name);
                    }
                }
            }
        }
        return false;
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
}
