package me.xander.firstmod.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import me.xander.firstmod.corruption.CorruptionHandler;
import me.xander.firstmod.effect.ModEffects;
import me.xander.firstmod.gui.hud.CustomHeartType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Shadow @Final private MinecraftClient client;

    @Inject(method = "drawHeart", at = @At(value = "HEAD"), cancellable = true)
    private void customHeart(DrawContext context, InGameHud.HeartType type, int x, int y, boolean hardcore, boolean blinking, boolean half, CallbackInfo ci) {
        PlayerEntity player = client.player;
        if (player != null) {
          /*  int corruption = CorruptionHandler.getCorruption(player);
            if (corruption >= 2 && type != InGameHud.HeartType.CONTAINER) {
                RenderSystem.enableBlend();
                context.drawGuiTexture(CustomHeartType.CORRUPTED.getTexture(hardcore, half, blinking), x, y, 9, 9);
                RenderSystem.disableBlend();
                ci.cancel();
            }
            if (player.hasStatusEffect(ModEffects.STEEL_BLOODED) && type != InGameHud.HeartType.CONTAINER) {
                RenderSystem.enableBlend();
                context.drawGuiTexture(CustomHeartType.STEEL.getTexture(hardcore, half, blinking), x, y, 9, 9);
                RenderSystem.disableBlend();
                ci.cancel();
            }
           */
            CustomHeartType customHeartType = CustomHeartType.shouldRenderCustomHeart(player);
            if (customHeartType != null && type != InGameHud.HeartType.CONTAINER) {
                RenderSystem.enableBlend();
                context.drawGuiTexture(customHeartType.getTexture(hardcore, half, blinking), x, y, 9, 9);
                RenderSystem.disableBlend();
                ci.cancel();
            }
        }
    }
}
