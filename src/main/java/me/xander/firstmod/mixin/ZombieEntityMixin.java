package me.xander.firstmod.mixin;

import me.xander.firstmod.entity.custom.WhispererEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.mob.HuskEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ZombieEntity.class)
public abstract class ZombieEntityMixin extends LivingEntity {
    @Shadow protected abstract void convertTo(EntityType<? extends ZombieEntity> entityType);

    protected ZombieEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        if (damageSource.getAttacker() instanceof WhispererEntity) {
            if (((ZombieEntity) (Object) this) instanceof HuskEntity) {
                this.convertTo(EntityType.ZOMBIE);
            } else if (!((ZombieEntity) (Object) this instanceof DrownedEntity)) {
                this.convertTo(EntityType.DROWNED);
            }
        }
        super.onDeath(damageSource);
    }
}
