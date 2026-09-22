package me.xander.firstmod.networking;

import me.xander.firstmod.corruption.CorruptionHandler;
import me.xander.firstmod.data.ModData;
import me.xander.firstmod.networking.packet.CorruptionPayload;
import me.xander.firstmod.networking.packet.SenseEntityPayload;
import me.xander.firstmod.util.mixin.PlayerEntityAccess;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;

public class ClientboundPackets {
    public static void handelCorruptionPayload(CorruptionPayload corruptionPayload, ClientPlayNetworking.Context context) {
        context.player().setAttached(ModData.CORRUPTION, corruptionPayload.value());
        CorruptionHandler.corruptionEffect(context.player(), corruptionPayload.value());
    }
    public static void handleSenseEntityPayload(SenseEntityPayload payload, ClientPlayNetworking.Context context) {
        ((PlayerEntityAccess) context.player()).first_mod_template_1_21_1$setSenseCooldown(payload.senseTimer());
        ((PlayerEntityAccess) context.player()).first_mod_template_1_21_1$setLastHeardEntity(context.player().getWorld().getEntityById(payload.entityId()));
    }
}
