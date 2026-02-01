package me.xander.firstmod.item.custom;

import me.xander.first_mod;
import me.xander.firstmod.corruption.CorruptionHandler;
import me.xander.firstmod.sound.ModSounds;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Optional;

public class godStick extends SwordItem {
    private final RegistryEntry<StatusEffect> effect;




    public godStick(ToolMaterial toolMaterial, Settings settings, RegistryEntry<StatusEffect> effect) {
        super(toolMaterial, settings);
        this.effect = effect;
    }

    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        attacker.playSound(SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, 20.0f, 1.0f);
        super.postDamageEntity(stack, target, attacker);
    }

    @Override
    public void onCraftByPlayer(ItemStack stack, World world, PlayerEntity player) {
        super.onCraftByPlayer(stack, world, player);
        BlockPos playerPos = player.getBlockPos();
        EntityType.LIGHTNING_BOLT.spawn((ServerWorld) world, playerPos, SpawnReason.TRIGGERED);
    }




    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
         
            return TypedActionResult.success(user.getStackInHand(hand));
    }
    private void applySonicBoomEffects(LivingEntity entity, Vec3d direction) {
        // Apply knockback in the line direction
        Vec3d knockbackForce = direction.multiply(2.0);
        entity.setVelocity(knockbackForce);

        // Apply damage
        Optional<RegistryEntry.Reference<DamageType>> sonicBoomDamageOptional = entity.getWorld().getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).getEntry(DamageTypes.SONIC_BOOM);
        if (sonicBoomDamageOptional.isPresent()) {
            RegistryEntry<DamageType> sonicBoomDamage = sonicBoomDamageOptional.get();
            entity.damage(new DamageSource(sonicBoomDamage), 10.0F);
        } else {
            first_mod.LOGGER.error("Error: Sonic Boom DamageType not found!");
        }


    }
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (!context.getPlayer().isSneaking()) {
            if (!context.getWorld().isClient())
                    CorruptionHandler.addCorruption(((ServerPlayerEntity) context.getPlayer()), 1);
            context.getPlayer().playSound(ModSounds.STICKING);

        }else {
            if (!context.getWorld().isClient())
                CorruptionHandler.setCorruption(((ServerPlayerEntity) context.getPlayer()), 0);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.addStatusEffect(new StatusEffectInstance(effect, 200, 9), attacker);



        return super.postHit(stack, target, attacker);
    }
}

