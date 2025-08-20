package me.xander.firstmod.item.custom;

import me.xander.first_mod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAttachmentType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Equipment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Optional;

public class WardenPendent extends Item implements Equipment {
    private int charge;
    private int maxCharge;
    private double range;
    private float damage;
    private double knockback;
    private double particleSpeed;
    private int particleDivider;
    public WardenPendent(Settings settings, int maxChargeTime, double blastRange, float blastDamage, double blastKnockback, double blastParticleSpeed, int particleDividend) {
        super(settings);
         this.maxCharge = maxChargeTime;
         this.range = blastRange;
         this.damage = blastDamage;
         this.knockback = blastKnockback;
         this.particleSpeed = blastParticleSpeed;
         this.particleDivider = particleDividend;
    }



    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

        if (entity instanceof PlayerEntity player) {
            BlockPos pos = player.getBlockPos();
            if (player.getInventory().getArmorStack(2) == stack && player.isSneaking()) {
                charge++;
                if (!world.isClient)
                   ((ServerWorld) world).spawnParticles(ParticleTypes.SCULK_SOUL, player.getX(), player.getY() + 1, player.getZ(),
                            charge / particleDivider, 0.0, 0.0, 0.0, particleSpeed);
            } else {
                charge = 0;
            }
            if (charge >= maxCharge) {
                if (!world.isClient) {
                    ServerWorld serverWorld = (ServerWorld) world;
                    Vec3d vec3d5 = player.getRotationVec(1.0F);
                    double x = player.getX() - (player.getX() - vec3d5.x * (double) 4.0F);
                    double y = player.getY() - (player.getY() - vec3d5.y * (double) 4.0F);
                    double z = player.getZ() - (player.getZ() - vec3d5.z * (double) 4.0F);
                    Vec3d vec3d6 = new Vec3d(x, y, z);

                    Vec3d vec3d = player.getPos().add(player.getAttachments().getPoint(EntityAttachmentType.WARDEN_CHEST, 0, player.getYaw()));
                    Vec3d vec3d3 = vec3d6.normalize();
                    int i = MathHelper.floor(vec3d6.length()) + (int) range;
                    for (int j = 1; j < i; ++j) {
                        Vec3d vec3d4 = vec3d.add(vec3d3.multiply((double) j));
                        serverWorld.spawnParticles(ParticleTypes.SONIC_BOOM, vec3d4.x, vec3d4.y, vec3d4.z, 1, (double) 0.0F, (double) 0.0F, (double) 0.0F, (double) 0.0F);
                        Vec3d direction = player.getRotationVector().normalize();
                        for (LivingEntity entity1 : world.getEntitiesByClass(LivingEntity.class, player.getBoundingBox().expand(range), e -> e != player)) {
                            Vec3d entityPos = entity1.getPos();
                            Vec3d playerToEntity = entityPos.subtract(player.getPos()).normalize();

                            // Check if the entity is roughly in front of the player
                            if (direction.dotProduct(playerToEntity) > 0.99) { // Adjust the threshold
                                applySonicBoomEffects(entity1, direction);
                            }
                        }


                    }
                    world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_WARDEN_SONIC_BOOM, SoundCategory.PLAYERS, 10, 1);
                    charge = 0;
                }
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }


    private void applySonicBoomEffects(LivingEntity entity, Vec3d direction) {
        // Apply knockback in the line direction
        Vec3d knockbackForce = direction.multiply(knockback);
        entity.setVelocity(knockbackForce);

        // Apply damage
        Optional<RegistryEntry.Reference<DamageType>> sonicBoomDamageOptional = entity.getWorld().getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).getEntry(DamageTypes.SONIC_BOOM);
        if (sonicBoomDamageOptional.isPresent()) {
            RegistryEntry<DamageType> sonicBoomDamage = sonicBoomDamageOptional.get();
            entity.damage(new DamageSource(sonicBoomDamage), damage);
        } else {
            first_mod.LOGGER.error("Error: Sonic Boom DamageType not found!");
        }


    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return this.equipAndSwap(this, world, user, hand);
    }

    @Override
    public RegistryEntry<SoundEvent> getEquipSound() {
        return RegistryEntry.of(SoundEvents.ENTITY_WARDEN_AMBIENT);
    }
    @Override
    public EquipmentSlot getSlotType() {
        return EquipmentSlot.CHEST;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double d) {
        range = d;
    }

    public int getMaxCharge() {
        return maxCharge;
    }

    public void setMaxCharge(int maxCharge) {
        this.maxCharge = maxCharge;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }
    public double getKnockback() {
        return knockback;
    }

    public void setKnockback(double knockback) {
        this.knockback = knockback;
    }

    public double getParticleSpeed() {
        return particleSpeed;
    }

    public void setParticleSpeed(double particleSpeed) {
        this.particleSpeed = particleSpeed;
    }

    public int getParticleDivider() {
        return particleDivider;
    }

    public void setParticleDivider(int particleDivider) {
        this.particleDivider = particleDivider;
    }


}
