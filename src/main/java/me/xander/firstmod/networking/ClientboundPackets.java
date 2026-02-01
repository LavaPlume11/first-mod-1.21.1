package me.xander.firstmod.networking;

import me.xander.firstmod.corruption.CorruptionHandler;
import me.xander.firstmod.data.ModData;
import me.xander.firstmod.networking.packet.CorruptionPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class ClientboundPackets {
    public static void handelCorruptionPayload(CorruptionPayload corruptionPayload, ClientPlayNetworking.Context context) {
        context.player().setAttached(ModData.CORRUPTION, corruptionPayload.value());
        CorruptionHandler.corruptionEffect(context.player(), corruptionPayload.value());
    }
}
