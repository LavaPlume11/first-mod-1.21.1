package me.xander.firstmod.mixin;

import me.xander.first_mod;
import me.xander.firstmod.components.ModDataComponentTypes;
import me.xander.firstmod.corruption.CorruptionHandler;
import me.xander.firstmod.inventory.PocketStorageInventory;
import me.xander.firstmod.item.custom.ModItems;
import me.xander.firstmod.util.mixin.PlayerEntityAccess;
import me.xander.firstmod.util.mixin.PocketStorageAccess;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.ItemScatterer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements PocketStorageAccess, PlayerEntityAccess {
    @Shadow public abstract PlayerInventory getInventory();
    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
    @Unique
    protected PocketStorageInventory pocketStorageInventory = new PocketStorageInventory();
    @Unique
    protected List<String> entitiesKilledWithCorruption = new ArrayList<>();
    @Override
    public PocketStorageInventory first_mod_template_1_21_1$getPocketStorageInventory() {
        return this.pocketStorageInventory;
    }

    @Override
    public void first_mod_template_1_21_1$setPocketStorageInventory(PocketStorageInventory inventory) {
        this.pocketStorageInventory = inventory;
    }

    @Override
    public List<String> first_mod_template_1_21_1$getCorruptedKills() {
        return entitiesKilledWithCorruption;
    }

    @Override
    public void first_mod_template_1_21_1$setCorruptedKills(List<String> list) {
        this.entitiesKilledWithCorruption = list;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At(value = "TAIL"))
    private void writeModData(NbtCompound nbt, CallbackInfo ci) {
        nbt.put("PocketItems", this.pocketStorageInventory.toNbtList(this.getRegistryManager()));
        if (!entitiesKilledWithCorruption.isEmpty()) {
            for (int i = 0; i < entitiesKilledWithCorruption.size(); i++) {
                nbt.putString("corrupted_kills_" + i , entitiesKilledWithCorruption.get(i));
            }
        }
        nbt.putInt("length", entitiesKilledWithCorruption.size());

    }
    @Inject(method = "readCustomDataFromNbt", at = @At(value = "TAIL"))
    private void readModData(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("PocketItems", NbtElement.LIST_TYPE)) {
            this.pocketStorageInventory.readNbtList(nbt.getList("PocketItems", NbtElement.COMPOUND_TYPE), this.getRegistryManager());
            }
        int size = nbt.getInt("length");
        List<String> strings = new ArrayList<>();
         if (size > 0) {
            for (int i = 0; i < size; i++) {
                strings.add(nbt.getString("corrupted_kills_" + i));
            }
            this.entitiesKilledWithCorruption = strings;
        }
        }
    @Inject(method = "dropInventory", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;vanishCursedItems()V"))
    private void dropPocketStorage(CallbackInfo ci) {
       if (this.getInventory().containsAny(Set.of(ModItems.POCKET_STORAGE))) {
           ItemScatterer.spawn(this.getWorld(), this, pocketStorageInventory);
           pocketStorageInventory.clear();
       }
    }
    @Inject(method = "canUseSlot", at = @At(value = "HEAD"), cancellable = true)
    private void checkIfLocked(EquipmentSlot slot, CallbackInfoReturnable<Boolean> cir) {
        if(Boolean.TRUE.equals(getEquippedStack(slot).get(ModDataComponentTypes.SLOT_LOCKED))) {
            cir.setReturnValue(false);
        }
    }
    @Redirect(method = "checkFallFlying", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean customElytraFlightStart(ItemStack instance, Item item) {
        return instance.getItem() instanceof ElytraItem;
    }
    @Inject(method = "tick", at = @At(value = "TAIL"))
    private void tickCorruption(CallbackInfo ci) {
        if (!getWorld().isClient())
            CorruptionHandler.tickCorruption((PlayerEntity) (Object)this, CorruptionHandler.getCorruption((PlayerEntity) (Object)this));
    }
    @Inject(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setMovementSpeed(F)V"))
    private void tickCorruptionMovement(CallbackInfo ci) {
    }
    @Inject(method = "dropItem(Lnet/minecraft/item/ItemStack;Z)Lnet/minecraft/entity/ItemEntity;", at = @At(value = "HEAD"))
    private void dropLockedItem(ItemStack stack, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir) {
    }

}
