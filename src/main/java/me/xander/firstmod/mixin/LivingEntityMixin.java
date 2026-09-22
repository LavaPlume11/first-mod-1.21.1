package me.xander.firstmod.mixin;

import me.xander.firstmod.effect.ModEffects;
import me.xander.firstmod.item.custom.ModItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.ItemScatterer;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.Iterator;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow public abstract boolean damage(DamageSource source, float amount);

    @Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);

    @Shadow public abstract void remove(RemovalReason reason);

    @Shadow public abstract float getMaxHealth();

    @Shadow public abstract float getHealth();

    @Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect, @Nullable Entity source);

    @Shadow public abstract boolean hasStatusEffect(RegistryEntry<StatusEffect> effect);

    @Shadow public abstract Collection<StatusEffectInstance> getStatusEffects();

    @Shadow protected abstract void onStatusEffectRemoved(StatusEffectInstance effect);

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Redirect(method = "tickFallFlying", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean customElytraFlight(ItemStack instance, Item item) {
        return instance.getItem() instanceof ElytraItem;
        }

    @Inject(method = "onDeath", at = @At(value = "TAIL"))
    private void tryDropBottle(DamageSource damageSource, CallbackInfo ci) {
        if (damageSource.getAttacker() != null && damageSource.getAttacker() instanceof LivingEntity entity && entity.hasStatusEffect(ModEffects.VAMPIRIFICATION)) {
            double random = Math.random();
            if (entity instanceof PlayerEntity player) {
                if (Math.round(random * 100) < this.getMaxHealth() && player.getInventory().contains(Items.GLASS_BOTTLE.getDefaultStack())) {
                    if (this.hasStatusEffect(ModEffects.VAMPIRIFICATION)) {
                        ItemScatterer.spawn(this.getWorld(), this.getX(), this.getY(), this.getZ(), new ItemStack(ModItems.VAMPIRE_BLOOD));
                    } else {
                        ItemScatterer.spawn(this.getWorld(), this.getX(), this.getY(), this.getZ(), new ItemStack(ModItems.BLOOD_BOTTLE));
                    }
                    player.getInventory().removeStack(player.getInventory().getSlotWithStack(Items.GLASS_BOTTLE.getDefaultStack()), 1);
                }
            } else if (Math.round(random * 100) < this.getMaxHealth()) {
                ItemScatterer.spawn(this.getWorld(), this.getX(), this.getY(), this.getZ(), new ItemStack(ModItems.BLOOD_BOTTLE));
            }
        }
    }
    @Inject(method = "damage", at = @At(value = "HEAD"), cancellable = true)
    private void tryTurnEntity(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (source.getAttacker() instanceof LivingEntity entity) {
            if (entity.hasStatusEffect(ModEffects.VAMPIRIFICATION) && !this.hasStatusEffect(ModEffects.VAMPIRIFICATION) && this.getHealth() - amount <= 0 && Math.round(Math.random() * 10) <= 1) {
                this.addStatusEffect(new StatusEffectInstance(ModEffects.VAMPIRIFICATION, -1));
                cir.setReturnValue(false);
            }
        }
    }
    @Inject(method = "clearStatusEffects", at = @At(value = "INVOKE_ASSIGN", target = "Ljava/util/Collection;iterator()Ljava/util/Iterator;"), cancellable = true)
    private void removeUnlessVamperism(CallbackInfoReturnable<Boolean> cir) {
        Iterator<StatusEffectInstance> iterator = this.getStatusEffects().iterator();

        boolean bl;
        for(bl = false; iterator.hasNext(); bl = true) {
            StatusEffectInstance instance = iterator.next();
            if (!(instance.getEffectType() == ModEffects.VAMPIRIFICATION)) {
                this.onStatusEffectRemoved(instance);
                iterator.remove();
            }
        }
        cir.setReturnValue(bl);
    }

}

