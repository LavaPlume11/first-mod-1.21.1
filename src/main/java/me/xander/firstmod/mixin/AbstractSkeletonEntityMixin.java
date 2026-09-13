package me.xander.firstmod.mixin;

import me.xander.firstmod.item.custom.ModItems;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.*;
import net.minecraft.util.Hand;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractSkeletonEntity.class)
public abstract class AbstractSkeletonEntityMixin extends HostileEntity {

    protected AbstractSkeletonEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initialize", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/AbstractSkeletonEntity;updateEnchantments(Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/util/math/random/Random;Lnet/minecraft/world/LocalDifficulty;)V"))
    private void checkNether(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, CallbackInfoReturnable<EntityData> cir) {
         Random random1 = world.getRandom();
         if (world.getDimension().ultrawarm() && random1.nextFloat() < 0.25F) {
             initNetherEquipment(random1, difficulty);
         }
    }
    @Unique
    protected void initNetherEquipment(Random random, LocalDifficulty localDifficulty) {
        super.initEquipment(random, localDifficulty);
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModItems.NETHER_BOW));
    }
    @Unique
    protected Hand betterHandHolding(LivingEntity entity, Item item) {
        return entity.getStackInHand(Hand.MAIN_HAND).getItem() instanceof BowItem ? Hand.MAIN_HAND : Hand.OFF_HAND;
    }
    @Redirect(method = "updateAttackType", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean customBowAttack(ItemStack instance, Item item) {
        return instance.getItem() instanceof BowItem;
    }
    @Inject(method = "canUseRangedWeapon", at = @At(value = "HEAD"), cancellable = true)
    private void isBowItem(RangedWeaponItem weapon, CallbackInfoReturnable<Boolean> cir) {
        if (weapon instanceof BowItem) {
            cir.setReturnValue(true);
        }
    }
    @Redirect(method = "shootAt", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/ProjectileUtil;getHandPossiblyHolding(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/Item;)Lnet/minecraft/util/Hand;"))
    private Hand customBowAttack(LivingEntity entity, Item item) {
        return betterHandHolding(entity, item);
    }
    @Redirect(method = "updateAttackType", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/ProjectileUtil;getHandPossiblyHolding(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/Item;)Lnet/minecraft/util/Hand;"))
    private Hand customBowAttack2(LivingEntity entity, Item item) {
        return betterHandHolding(entity, item);
    }

    @Override
    public ItemStack getProjectileType(ItemStack stack) {
        if (stack.isOf(ModItems.NETHER_BOW)) {
            return new ItemStack(Items.FIRE_CHARGE);
        } else {
            return super.getProjectileType(stack);
        }
    }
}
