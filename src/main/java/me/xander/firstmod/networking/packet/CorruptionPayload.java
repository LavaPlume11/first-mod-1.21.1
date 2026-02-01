package me.xander.firstmod.networking.packet;

import me.xander.first_mod;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record CorruptionPayload(int value, boolean strong) implements CustomPayload {
    public static final CustomPayload.Id<CorruptionPayload> ID = new CustomPayload.Id<>(Identifier.of(first_mod.MOD_ID, "corruption_payload"));
    public static final PacketCodec<RegistryByteBuf, CorruptionPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT,
            CorruptionPayload::value,

            PacketCodecs.BOOL,
            CorruptionPayload::strong,

            CorruptionPayload::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
