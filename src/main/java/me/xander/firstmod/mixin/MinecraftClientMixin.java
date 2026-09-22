package me.xander.firstmod.mixin;

import me.xander.firstmod.util.mixin.PlayerEntityAccess;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Redirect(method = "hasOutline",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isGlowing()Z"))
    public boolean customOutline(Entity instance) {
        if (((MinecraftClient) (Object) this).player == null) {
            return instance.isGlowing();
        }
        //return instance.isGlowing() || ((MinecraftClient) (Object) this).player.getEquippedStack(EquipmentSlot.HEAD).isOf(Items.DIAMOND_HELMET);
        return instance.isGlowing() || ((PlayerEntityAccess) ((MinecraftClient) (Object) this).player).first_mod_template_1_21_1$canSense(instance);
    }
}
