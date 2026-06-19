package me.xander.firstmod.corruption;

import me.xander.first_mod;
import me.xander.firstmod.data.ModData;
import me.xander.firstmod.networking.packet.CorruptionPayload;
import me.xander.firstmod.util.ModKeyBindings;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CorruptionHandler {
    public static void setCorruption(ServerPlayerEntity player, int value) {
        player.setAttached(ModData.CORRUPTION, value);
        ServerPlayNetworking.send(player, new CorruptionPayload(value, false));
    }

    public static void addCorruption(ServerPlayerEntity player, int value) {
        if (player.getAttached(ModData.CORRUPTION) == null) {
            player.setAttached(ModData.CORRUPTION, 0);
        }
        int newValue = player.getAttached(ModData.CORRUPTION) + value;
        if (player.getAttached(ModData.CORRUPTION) < 100) {
            player.setAttached(ModData.CORRUPTION, newValue);
            ServerPlayNetworking.send(player, new CorruptionPayload(newValue, false));
        } else {
            player.setAttached(ModData.CORRUPTION, 100);
            ServerPlayNetworking.send(player, new CorruptionPayload(100, false));
        }

    }

    public static void subtractCorruption(ServerPlayerEntity player, int value) {
        if (player.getAttached(ModData.CORRUPTION) == null) {
            player.setAttached(ModData.CORRUPTION, 0);
        }
        int newValue = player.getAttached(ModData.CORRUPTION) - value;
        player.setAttached(ModData.CORRUPTION, newValue);
        ServerPlayNetworking.send(player, new CorruptionPayload(newValue, false));
    }

    public static int getCorruption(PlayerEntity player) {
        if (player.getAttached(ModData.CORRUPTION) == null) {
            return 0;
        } else {
            return player.getAttached(ModData.CORRUPTION);
        }
    }

    public static void renderHud(DrawContext drawContext, RenderTickCounter renderTickCounter) {
        PlayerEntity player = MinecraftClient.getInstance().player;
            int x = drawContext.getScaledWindowWidth() / 2;
            int y = drawContext.getScaledWindowHeight() / 2;
            float tintStrength = 0;
            if (!player.isSpectator()) {


                if (getCorruption(player) >= 1) {
                    drawContext.drawGuiTexture(Identifier.of(first_mod.MOD_ID, "corruption_1"), 64, 64, 0, 0, x, y + 50, 64, 64);
                    tintStrength = 1;
                }
                if (getCorruption(player) >= 2) {
                    tintStrength = 2;
                    // drawContext.drawGuiTexture(Identifier.of(first_mod.MOD_ID, "hotbar_corrupted"), 182, 22, 0, 0, x - 92, y + 113, 182, 22);
                    drawContext.getMatrices().push();
                    drawContext.getMatrices().translate(0.0F, 0.0F, -90.0F);
                    drawContext.drawGuiTexture(Identifier.of(first_mod.MOD_ID, "hotbar_corrupted"), x - 91, drawContext.getScaledWindowHeight() - 22, 182, 22);
                    drawContext.getMatrices().pop();
                }
                if (getCorruption(player) >= 3) {
                    drawContext.drawGuiTexture(Identifier.of(first_mod.MOD_ID, "corruption_2"), 64, 64, 0, 0, x - 150, y - 30, 64, 64);
                    tintStrength = 3;
                }
                if (getCorruption(player) >= 4) {
                    drawContext.drawGuiTexture(Identifier.of(first_mod.MOD_ID, "corruption_3"), 64, 64, 0, 0, x + 50, y - 70, 64, 64);
                    tintStrength = 4;
                }
                if (getCorruption(player) >= 5) {
                    drawContext.drawGuiTexture(Identifier.of(first_mod.MOD_ID, "corruption_4"), 64, 64, 0, 0, x - 50, y - 134, 64, 64);
                    tintStrength = 5;
                }
                if (getCorruption(player) > 0) {
                    float red = 0.35f;
                    float green = 0;
                    float blue = 0.55f;
                    float target = (((int) (((tintStrength / 4) / 1.5)
                            * 255) << 24));
                    if (getCorruption(player) > 5) {
                        renderBar(drawContext, x, y);
                        renderFrame(drawContext, x, y);
                        renderTint(drawContext, 1426063360, red, green, blue);
                    } else {
                        renderTint(drawContext, target, red, green, blue);
                    }

                }
            }

        if (ModKeyBindings.C_KEY_BINDING.wasPressed()) {
            MinecraftClient.getInstance().player.sendMessage(Text.of(String.valueOf(CorruptionHandler.getCorruption(MinecraftClient.getInstance().player))), true);
        }
    }
    public static int getScaledCorruptionProgress() {
        int progress = getCorruption(MinecraftClient.getInstance().player);
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

    public static void renderTint(DrawContext drawContext, float a, float r, float g, float b) {
        drawContext.fill(RenderLayer.getGuiOverlay(),0, 0, drawContext.getScaledWindowWidth(), drawContext.getScaledWindowHeight(), ((int) a) | (int) (r * 255) << 16 |
                (int) (g * 255) << 8 | (int) (b * 255));
    }

    public static void corruptionEffect(ClientPlayerEntity player, int value) {
        if (value <= 0) {

        }
        if (value >= 1) {

        }
        if (value >= 2) {

        }
        if (value >= 3) {

        }
        if (value >= 4) {

        }
        if (value >= 5) {

        }
    }
}

