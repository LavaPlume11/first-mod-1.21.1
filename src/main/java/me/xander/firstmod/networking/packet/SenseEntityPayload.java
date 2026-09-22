package me.xander.firstmod.networking.packet;

import me.xander.first_mod;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record SenseEntityPayload(int senseTimer, int entityId) implements CustomPayload {
    public static final Id<SenseEntityPayload> ID = new Id<>(Identifier.of(first_mod.MOD_ID, "sense_entity_payload"));
    public static final PacketCodec<RegistryByteBuf, SenseEntityPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT,
            SenseEntityPayload::senseTimer,

            PacketCodecs.VAR_INT,
            SenseEntityPayload::entityId,

            SenseEntityPayload::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
