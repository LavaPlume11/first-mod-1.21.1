package me.xander.firstmod.mixin;

import me.xander.firstmod.inventory.PocketStorageInventory;
import me.xander.firstmod.item.custom.ModItems;
import me.xander.firstmod.inventory.PocketStorageAccess;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.ItemScatterer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements PocketStorageAccess {
    @Shadow public abstract PlayerInventory getInventory();

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
    @Unique
    protected PocketStorageInventory pocketStorageInventory = new PocketStorageInventory();
    @Override
    public PocketStorageInventory first_mod_template_1_21_1$getPocketStorageInventory() {
        return this.pocketStorageInventory;
    }

    @Override
    public void first_mod_template_1_21_1$setPocketStorageInventory(PocketStorageInventory inventory) {
        this.pocketStorageInventory = inventory;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At(value = "TAIL"))
    private void writePocketStorage(NbtCompound nbt, CallbackInfo ci) {
        nbt.put("PocketItems", this.pocketStorageInventory.toNbtList(this.getRegistryManager()));
    }
    @Inject(method = "readCustomDataFromNbt", at = @At(value = "TAIL"))
    private void readPocketStorage(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("PocketItems", NbtElement.LIST_TYPE)) {
            this.pocketStorageInventory.readNbtList(nbt.getList("PocketItems", NbtElement.COMPOUND_TYPE), this.getRegistryManager());
            }
        }
    @Inject(method = "dropInventory", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;vanishCursedItems()V"))
    private void dropPocketStorage(CallbackInfo ci) {
       if (this.getInventory().containsAny(Set.of(ModItems.POCKET_STORAGE))) {
           ItemScatterer.spawn(this.getWorld(), this, pocketStorageInventory);
           pocketStorageInventory.clear();
       }
    }
    @Redirect(method = "checkFallFlying", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean customElytraFlightStart(ItemStack instance, Item item) {
        return instance.getItem() instanceof ElytraItem;
    }
}
