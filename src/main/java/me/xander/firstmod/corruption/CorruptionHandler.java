package me.xander.firstmod.corruption;

import me.xander.first_mod;
import me.xander.firstmod.data.ModData;
import me.xander.firstmod.networking.packet.CorruptionPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

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
        player.setAttached(ModData.CORRUPTION, newValue);
        ServerPlayNetworking.send(player, new CorruptionPayload(newValue, false));
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
                renderTint(drawContext, target, red, green, blue);

            }

    }

    public static void renderTint(DrawContext drawContext, float a, float r, float g, float b) {
        drawContext.fill(RenderLayer.getGuiOverlay(),0, 0, drawContext.getScaledWindowWidth(), drawContext.getScaledWindowHeight(), ((int) a) | (int) (r * 255) << 16 |
                (int) (g * 255) << 8 | (int) (b * 255));
    }

    public static void corruptionEffect(ClientPlayerEntity player, int value) {
        if (value <= 0) {

        }
        if (value >= 1) {
        player.move(MovementType.SELF, new Vec3d(0,5,0));
        first_mod.LOGGER.info("work");
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

