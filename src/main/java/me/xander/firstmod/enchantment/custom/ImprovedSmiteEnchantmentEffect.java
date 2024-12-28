package me.xander.firstmod.enchantment.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.xander.first_mod;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;

public record ImprovedSmiteEnchantmentEffect(int level) implements EnchantmentEntityEffect {
    public static final MapCodec<ImprovedSmiteEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
            .group(Codec.INT.fieldOf("level").forGetter(ImprovedSmiteEnchantmentEffect::level))
            .apply(instance, ImprovedSmiteEnchantmentEffect::new));

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity target, Vec3d pos) {
        int count = 1;
        if (target.isOnFire()) {
            count = 10;
        }
        if (target.isFireImmune()) {
            count = 15;
        }
        if(level == 1) {
            EntityType.LIGHTNING_BOLT.spawn(world, target.getBlockPos(), SpawnReason.TRIGGERED);
        }
        if(level == 2) {
            EntityType.LIGHTNING_BOLT.spawn(world, target.getBlockPos(), SpawnReason.TRIGGERED);
            EntityType.LIGHTNING_BOLT.spawn(world, target.getBlockPos(), SpawnReason.TRIGGERED);
        }
        if(level == 3) {
            EntityType.LIGHTNING_BOLT.spawn(world, target.getBlockPos(), SpawnReason.TRIGGERED);
            EntityType.LIGHTNING_BOLT.spawn(world, target.getBlockPos(), SpawnReason.TRIGGERED);
            EntityType.LIGHTNING_BOLT.spawn(world, target.getBlockPos(), SpawnReason.TRIGGERED);
            if (!target.isAlive()) {
                world.spawnParticles(ParticleTypes.ENCHANT, pos.getX() + 0.5f, pos.getY()
                        + 2.0f, pos.getZ() + 0.5f, count * 300, 0.3, 1.5, 0.3, 2.1);
                world.spawnParticles(ParticleTypes.ENCHANT, pos.getX() + 0.5f, pos.getY()
                        + 2.0f, pos.getZ() + 0.5f, count * 300, 0.2, 1.4, 0.2, 2);
                world.spawnParticles(ParticleTypes.ENCHANT, pos.getX() + 0.5f, pos.getY()
                        + 2.0f, pos.getZ() + 0.5f, count * 300, 0.4, 1.6, 0.4, 2.2);
                if (context.owner() instanceof PlayerEntity){
                world.playSound((PlayerEntity) context.owner(),target.getBlockPos(), SoundEvents.ENTITY_WITHER_AMBIENT, SoundCategory.PLAYERS);
                }
            }
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
