package me.xander.firstmod.mixin;

import com.mojang.authlib.GameProfile;
import me.xander.firstmod.inventory.PocketStorageAccess;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }


    @Inject(method = "copyFrom", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/data/DataTracker;set(Lnet/minecraft/entity/data/TrackedData;Ljava/lang/Object;)V"))
    private void setPocketInventory(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci) {
        ((PocketStorageAccess) this).first_mod_template_1_21_1$setPocketStorageInventory(((PocketStorageAccess) oldPlayer).first_mod_template_1_21_1$getPocketStorageInventory());
    }
}
