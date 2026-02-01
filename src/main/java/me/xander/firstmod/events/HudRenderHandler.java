package me.xander.firstmod.events;

import me.xander.first_mod;
import me.xander.firstmod.corruption.CorruptionHandler;
import me.xander.firstmod.data.ModData;
import me.xander.firstmod.util.ModKeyBindings;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.util.Identifier;

public class HudRenderHandler implements HudRenderCallback {
    private float totalTickDelta;
    private static boolean shouldRenderChaos = false;
    float a = 0;

    private static final Identifier CHAOS_TEXTURE = Identifier.of(first_mod.MOD_ID, "textures/misc/chaos_overlay.png");
    private static final Identifier CHAOS_ERROR = Identifier.of(first_mod.MOD_ID, "textures/misc/chaos_error.png");


    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter renderTickCounter) {
            shouldRenderChaos = ModKeyBindings.M_KEY_BINDING.isPressed();
            renderCorruption(drawContext,renderTickCounter);
            renderChaos(drawContext, renderTickCounter);
    }

    private void renderCorruption(DrawContext drawContext, RenderTickCounter renderTickCounter) {
        CorruptionHandler.renderHud(drawContext,renderTickCounter);
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

}

