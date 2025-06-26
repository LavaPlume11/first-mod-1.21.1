package me.xander.firstmod.item.custom;

import me.xander.first_mod;
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
        double range = 30.0;
        if (!world.isClient) {
            ServerWorld serverWorld = (ServerWorld) world;
            Vec3d vec3d5 = user.getRotationVec(1.0F);
            double x = user.getX() - (user.getX() - vec3d5.x * (double) 4.0F);
            double y = user.getY() - (user.getY() - vec3d5.y * (double) 4.0F);
            double z = user.getZ() - (user.getZ() - vec3d5.z * (double) 4.0F);
            Vec3d vec3d6 = new Vec3d(x, y, z);

            Vec3d vec3d = user.getPos().add(user.getAttachments().getPoint(EntityAttachmentType.PASSENGER, 0, user.getYaw()));
            Vec3d vec3d2 = /*target.getEyePos().subtract(vec3d)*/vec3d6;
            Vec3d vec3d3 = vec3d2.normalize();
            int i = MathHelper.floor(vec3d2.length()) + (int)range;
            for (int j = 1; j < i; ++j) {
                Vec3d vec3d4 = vec3d.add(vec3d3.multiply((double) j));
                serverWorld.spawnParticles(ParticleTypes.SONIC_BOOM, vec3d4.x, vec3d4.y, vec3d4.z, 1, (double) 0.0F, (double) 0.0F, (double) 0.0F, (double) 0.0F);
                Vec3d direction = user.getRotationVector().normalize();
                for (LivingEntity entity : world.getEntitiesByClass(LivingEntity.class, user.getBoundingBox().expand(range), e -> e != user)) {
                    Vec3d entityPos = entity.getPos();
                    Vec3d playerToEntity = entityPos.subtract(user.getPos()).normalize();

                    // Check if the entity is roughly in front of the player
                    if (direction.dotProduct(playerToEntity) > 0.9) { // Adjust the threshold
                        applySonicBoomEffects(entity, direction);
                    }
                }


            }
            world.playSound(null,user.getBlockPos(),SoundEvents.ENTITY_WARDEN_SONIC_BOOM, SoundCategory.PLAYERS,10,1);
        }

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
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos().up(); // Position above the block
        if (!world.isClient) {
            world.playSound(null,context.getBlockPos(),SoundEvents.ENTITY_DRAGON_FIREBALL_EXPLODE, SoundCategory.HOSTILE,1,1);

            // server particals
            ((ServerWorld) world).spawnParticles(ParticleTypes.SCULK_SOUL, context.getBlockPos().getX() + 0.5f, context.getBlockPos().getY()
            + 1.0f,context.getBlockPos().getZ()+0.5f,500,6.0,4.5,0.01,-0.8);


        }

        return ActionResult.SUCCESS;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.addStatusEffect(new StatusEffectInstance(effect, 200, 9), attacker);



        return super.postHit(stack, target, attacker);
    }
}

