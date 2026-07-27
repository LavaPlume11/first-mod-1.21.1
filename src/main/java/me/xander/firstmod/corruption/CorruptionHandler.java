package me.xander.firstmod.corruption;

import me.xander.first_mod;
import me.xander.firstmod.components.ModDataComponentTypes;
import me.xander.firstmod.data.ModData;
import me.xander.firstmod.item.custom.ModItems;
import me.xander.firstmod.networking.packet.CorruptionPayload;
import me.xander.firstmod.util.mixin.PlayerEntityAccess;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;

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


    public static void corruptionEffect(PlayerEntity player, int value) {
        if (value <= 0) {
            ((PlayerEntityAccess) player).first_mod_template_1_21_1$setCorruptedKills(new ArrayList<>());
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
    public static void tickCorruption(PlayerEntity player, int value) {
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

