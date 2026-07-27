package me.xander.firstmod.events;

import com.mojang.blaze3d.systems.RenderSystem;
import me.xander.first_mod;
import me.xander.firstmod.corruption.CorruptionHandler;
import me.xander.firstmod.util.ModKeyBindings;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class HudRenderHandler implements HudRenderCallback {
    private float totalTickDelta;
    private static boolean shouldRenderChaos = false;
    float a = 0;

    private static final Identifier CHAOS_TEXTURE = Identifier.of(first_mod.MOD_ID, "textures/misc/chaos_overlay.png");
    private static final Identifier CHAOS_ERROR = Identifier.of(first_mod.MOD_ID, "textures/misc/chaos_error.png");
    private static final Identifier CORRUPTION_OUTLINE = Identifier.of(first_mod.MOD_ID ,"textures/misc/corruption_outline.png");


    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter renderTickCounter) {
            shouldRenderChaos = ModKeyBindings.M_KEY_BINDING.isPressed();
            renderCorruption(drawContext,renderTickCounter);
            renderChaos(drawContext, renderTickCounter);
    }

    private void renderCorruption(DrawContext drawContext, RenderTickCounter renderTickCounter) {
        renderCorruptionHud(drawContext,renderTickCounter);
    }

    public void renderChaos(DrawContext drawContext, RenderTickCounter renderTickCounter) {
        if (shouldRenderChaos) {
            totalTickDelta += renderTickCounter.getTickDelta(true);
            totalTickDelta += renderTickCounter.getTickDelta(true);
            drawContext.drawTexture(CHAOS_TEXTURE, 0, 0, (int) totalTickDelta, 0, 512, 256);
            drawContext.drawTexture(CHAOS_ERROR, 0, 10, 0, 0, 410, 200);
        }
    }
    public static void setShouldRenderChaos(boolean bl) {
        shouldRenderChaos = bl;
    }

    public static void renderCorruptionHud(DrawContext drawContext, RenderTickCounter renderTickCounter) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        int x = drawContext.getScaledWindowWidth() / 2;
        int y = drawContext.getScaledWindowHeight() / 2;
        float tintStrength = 0;
        if (!player.isSpectator()) {
            if (CorruptionHandler.getCorruption(player) >= 1) {
                //drawContext.drawGuiTexture(Identifier.of(first_mod.MOD_ID, "corruption_1"), 64, 64, 0, 0, x, y + 50, 64, 64);
                tintStrength = 1;
            }
            if (CorruptionHandler.getCorruption(player) >= 2) {
                tintStrength = 2;
                drawContext.getMatrices().push();
                drawContext.getMatrices().translate(0.0F, 0.0F, -90.0F);
                drawContext.drawGuiTexture(Identifier.of(first_mod.MOD_ID, "hotbar_corrupted"), x - 91, drawContext.getScaledWindowHeight() - 22, 182, 22);
                drawContext.getMatrices().pop();
            }
            if (CorruptionHandler.getCorruption(player) >= 3) {
               // drawContext.drawGuiTexture(Identifier.of(first_mod.MOD_ID, "corruption_2"), 64, 64, 0, 0, x - 150, y - 30, 64, 64);
                tintStrength = 3;
            }
            if (CorruptionHandler.getCorruption(player) >= 4) {
                //drawContext.drawGuiTexture(Identifier.of(first_mod.MOD_ID, "corruption_3"), 64, 64, 0, 0, x + 50, y - 70, 64, 64);
                tintStrength = 4;
            }
            if (CorruptionHandler.getCorruption(player) >= 5) {
               // drawContext.drawGuiTexture(Identifier.of(first_mod.MOD_ID, "corruption_4"), 64, 64, 0, 0, x - 50, y - 134, 64, 64);
                tintStrength = 5;
            }
            if (CorruptionHandler.getCorruption(player) > 0) {
                float red = 0.35f;
                float green = 0;
                float blue = 0.55f;
                float target = (((int) (((tintStrength / 4) / 1.5)
                        * 255) << 24));
                if (CorruptionHandler.getCorruption(player) > 5) {
                    renderTint(drawContext, 1426063360, red, green, blue, 1);
                    renderBar(drawContext, x, y);
                    renderFrame(drawContext, x, y);
                } else {
                    renderTint(drawContext, target, red, green, blue, tintStrength / 10);
                }

            }
        }

    }
    public static int getScaledCorruptionProgress() {
        int progress = CorruptionHandler.getCorruption(MinecraftClient.getInstance().player);
        int maxProgress = 92; // Max Progress
        int arrowPixelSize = 40; // This is the width in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? progress * arrowPixelSize / maxProgress : 0;
    }




    private static void renderFrame(DrawContext context, int x, int y) {
        context.getMatrices().push();
        context.getMatrices().translate(250.0F, 0.0F, 0.0F);
        context.drawGuiTexture(Identifier.of(first_mod.MOD_ID, "corruption_bar_frame"), x - 60, context.getScaledWindowHeight() - 200, 40, 120);;
        context.getMatrices().pop();

    }
    private static void renderBar(DrawContext context, int x, int y) {
        context.getMatrices().push();
        context.getMatrices().translate(250.0F, 0.0F, 0.0F);
        context.drawTexture(Identifier.of(first_mod.MOD_ID, "textures/gui/sprites/corruption_bar.png"),x - 50,
                context.getScaledWindowHeight() - 98  - getScaledCorruptionProgress() * 2, 0,
                16 - getScaledCorruptionProgress() * 2, 20, getScaledCorruptionProgress() * 2,10, 40);
        context.getMatrices().pop();

    }

    public static void renderTint(DrawContext drawContext, float a, float r, float g, float b, float opacity) {
        drawContext.fill(RenderLayer.getGuiOverlay(),0, 0, drawContext.getScaledWindowWidth(), drawContext.getScaledWindowHeight(), ((int) a) | (int) (r * 255) << 16 |
                (int) (g * 255) << 8 | (int) (b * 255));
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        drawContext.setShaderColor(1.0F, 1.0F, 1.0F, opacity);
        drawContext.drawTexture(CORRUPTION_OUTLINE , 0, 0, -90, 0.0F, 0.0F, drawContext.getScaledWindowWidth(), drawContext.getScaledWindowHeight(), drawContext.getScaledWindowWidth(), drawContext.getScaledWindowHeight());
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        drawContext.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }


}

