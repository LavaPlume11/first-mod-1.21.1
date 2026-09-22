package me.xander.firstmod.mixin;

import me.xander.first_mod;
import me.xander.firstmod.components.ModDataComponentTypes;
import me.xander.firstmod.corruption.CorruptionHandler;
import me.xander.firstmod.inventory.PocketStorageInventory;
import me.xander.firstmod.item.custom.ModItems;
import me.xander.firstmod.util.mixin.PlayerEntityAccess;
import me.xander.firstmod.util.mixin.PlayerVibrationCallback;
import me.xander.firstmod.util.mixin.PocketStorageAccess;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ItemScatterer;
import net.minecraft.world.World;
import net.minecraft.world.event.Vibrations;
import net.minecraft.world.event.listener.EntityGameEventHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.*;
import java.util.function.BiConsumer;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements PocketStorageAccess, PlayerEntityAccess, Vibrations {
    @Shadow public abstract PlayerInventory getInventory();
    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
    @Unique
    protected PocketStorageInventory pocketStorageInventory = new PocketStorageInventory();
    @Unique
    protected List<String> entitiesKilledWithCorruption = new ArrayList<>();
    @Unique
    private final EntityGameEventHandler<VibrationListener> gameEventHandler = new EntityGameEventHandler<>(new VibrationListener(this));
    @Unique
    private final Callback vibrationCallback = new PlayerVibrationCallback(((PlayerEntity) (Object) this));
    @Unique
    private ListenerData vibrationListenerData = new ListenerData();
    @Unique
    private int vibrationCooldown = 30;
    @Unique
    private int senseCooldown = 0;
    @Unique
    private Entity lastHeardEntity;
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
        if (lastHeardEntity != null) {
            nbt.putInt("lastHeardEntity", this.lastHeardEntity.getId());
        }

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
         if (nbt.contains("lastHeardEntity")) {
             this.lastHeardEntity = this.getWorld().getEntityById(nbt.getInt("lastHeardEntity"));
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
    @Inject(method = "tick", at = @At(value = "HEAD"))
    private void tickVibration(CallbackInfo ci) {
        World var2 = this.getWorld();
         if (senseCooldown > 0) {
            senseCooldown--;
        }
        if (var2 instanceof ServerWorld serverWorld) {
            Ticker.tick(serverWorld, this.vibrationListenerData, this.vibrationCallback);
            if(((PlayerEntity) (Object) this).getEquippedStack(EquipmentSlot.CHEST).isOf(ModItems.ECHO_CHESTPLATE)) {
                vibrationCooldown--;
            } else if (vibrationCooldown < 30) {
                vibrationCooldown = 30;
            }
        }
    }
    @Inject(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setMovementSpeed(F)V"))
    private void tickCorruptionMovement(CallbackInfo ci) {
    }
    @Inject(method = "dropItem(Lnet/minecraft/item/ItemStack;Z)Lnet/minecraft/entity/ItemEntity;", at = @At(value = "HEAD"))
    private void dropLockedItem(ItemStack stack, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir) {
    }

    @Override
    public ListenerData getVibrationListenerData() {
        return vibrationListenerData;
    }

    @Override
    public Callback getVibrationCallback() {
        return vibrationCallback;
    }

    @Override
    public int first_mod_template_1_21_1$getVibrationCooldown() {
        return vibrationCooldown;
    }

    @Override
    public void first_mod_template_1_21_1$setVibrationCooldown(int vibrationCooldown) {
        this.vibrationCooldown = vibrationCooldown;
    }

    @Override
    public int first_mod_template_1_21_1$getSenseCooldown() {
        return this.senseCooldown;
    }

    @Override
    public void first_mod_template_1_21_1$setSenseCooldown(int senseCooldown) {
        this.senseCooldown = senseCooldown;
    }

    @Override
    public boolean first_mod_template_1_21_1$canSense(Entity entity) {
        if (this.getWorld().isClient()) {
            //first_mod.LOGGER.info(String.valueOf(senseCooldown));
            //first_mod.LOGGER.info(String.valueOf(this.lastHeardEntity));
        }
        return senseCooldown > 0 && entity == this.lastHeardEntity;
    }

    @Override
    public void first_mod_template_1_21_1$setLastHeardEntity(Entity entity) {
        this.lastHeardEntity = entity;
    }

    @Override
    public Entity first_mod_template_1_21_1$getLastHeardEntity() {
        return this.lastHeardEntity;
    }

    @Override
    public void updateEventHandler(BiConsumer<EntityGameEventHandler<?>, ServerWorld> callback) {
        World var3 = this.getWorld();
        if (var3 instanceof ServerWorld serverWorld) {
            callback.accept(this.gameEventHandler, serverWorld);
        }
    }
}
