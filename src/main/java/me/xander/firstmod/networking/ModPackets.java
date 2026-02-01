package me.xander.firstmod.networking;

import me.xander.firstmod.networking.packet.CorruptionPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.RegistryByteBuf;

public class ModPackets {
    private static void registerClientbound(PayloadTypeRegistry<RegistryByteBuf> registry) {
        // this payload is sent to client
        registry.register(CorruptionPayload.ID, CorruptionPayload.CODEC);
    }

    private static void registerServerbound(PayloadTypeRegistry<RegistryByteBuf> registry) {
        // this payload is sent to server

    }

    public static void registerServer() {
        registerServerbound(PayloadTypeRegistry.playC2S());
        registerClientbound(PayloadTypeRegistry.playS2C());
    }
    public static void registerClient() {
        ClientPlayNetworking.registerGlobalReceiver(CorruptionPayload.ID, ClientboundPackets::handelCorruptionPayload);
    }
}
