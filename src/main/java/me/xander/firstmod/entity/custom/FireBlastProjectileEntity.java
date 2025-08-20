package me.xander.firstmod.entity.custom;

import me.xander.first_mod;
import me.xander.firstmod.entity.ModEntities;
import me.xander.firstmod.item.custom.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class FireBlastProjectileEntity extends PersistentProjectileEntity {
    public FireBlastProjectileEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
        setNoGravity(true);
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return ModItems.LAVA_SABER.getDefaultStack();
    }
    public FireBlastProjectileEntity(World world, PlayerEntity player) {
        super(ModEntities.FIRE_BLAST, player, world, ModItems.LAVA_SABER.getDefaultStack(), null);
        setNoGravity(true);
    }

    @Override
    public void tick() {
        if (!this.getWorld().isClient()) {
            ServerWorld world = (ServerWorld) this.getWorld();
            world.spawnParticles(ParticleTypes.SMALL_FLAME,this.getX(), this.getY(), this.getZ(),30,0.1,0.1,0.1, 0.1);
        }
        super.tick();
        if (this.age >= 30) {
            this.discard();
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        this.discard();
    }


    @Override
    protected SoundEvent getHitSound() {
        return SoundEvents.ENTITY_DRAGON_FIREBALL_EXPLODE;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        super.onEntityHit(entityHitResult);
        entity.damage(this.getDamageSources().thrown(this,this.getOwner()),17.0f);
        entity.setFireTicks(180);
        this.discard();
    }
}
