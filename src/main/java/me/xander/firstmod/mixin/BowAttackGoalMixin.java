package me.xander.firstmod.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.BowAttackGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BowAttackGoal.class)
public abstract class BowAttackGoalMixin<T extends HostileEntity & RangedAttackMob> extends Goal {
    @Shadow @Final private T actor;

    @Inject(method = "isHoldingBow", at = @At(value = "HEAD"), cancellable = true)
    private void isHoldingBowItem(CallbackInfoReturnable<Boolean> cir) {
        ItemStack stack =  actor.getMainHandStack();
        ItemStack offStack =  actor.getOffHandStack();
        cir.setReturnValue(stack.getItem() instanceof BowItem || offStack.getItem() instanceof BowItem);
    }
    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/ProjectileUtil;getHandPossiblyHolding(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/Item;)Lnet/minecraft/util/Hand;"))
    private Hand goodHandHolding(LivingEntity entity, Item item) {
        if (entity.getStackInHand(Hand.MAIN_HAND).getItem() instanceof BowItem) {
            return Hand.MAIN_HAND;
        } else {
            return Hand.OFF_HAND;
        }
    }
}
