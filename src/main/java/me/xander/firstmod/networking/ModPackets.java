package me.xander.firstmod.networking;

import me.xander.firstmod.networking.packet.CorruptionPayload;
import me.xander.firstmod.networking.packet.PocketStoragePayload;
import me.xander.firstmod.networking.packet.SenseEntityPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;

public class ModPackets {
    private static void registerClientbound(PayloadTypeRegistry<RegistryByteBuf> registry) {
        // this payload is sent to client
        registry.register(CorruptionPayload.ID, CorruptionPayload.CODEC);
        registry.register(SenseEntityPayload.ID, SenseEntityPayload.CODEC);
    }

    private static void registerServerbound(PayloadTypeRegistry<RegistryByteBuf> registry) {
        // this payload is sent to server
        registry.register(PocketStoragePayload.ID, PocketStoragePayload.CODEC);
    }

    public static void registerServer() {
        registerServerbound(PayloadTypeRegistry.playC2S());
        registerClientbound(PayloadTypeRegistry.playS2C());
        ServerPlayNetworking.registerGlobalReceiver(PocketStoragePayload.ID, ServerboundPackets::handlePocketStoragePayload);
    }
    public static void registerClient() {
        ClientPlayNetworking.registerGlobalReceiver(CorruptionPayload.ID, ClientboundPackets::handelCorruptionPayload);
        ClientPlayNetworking.registerGlobalReceiver(SenseEntityPayload.ID, ClientboundPackets::handleSenseEntityPayload);
    }
}
