package me.xander.firstmod.networking.packet;

import me.xander.first_mod;
import me.xander.firstmod.item.custom.PocketStorageItem;
import net.minecraft.item.Item;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.network.packet.s2c.play.CooldownUpdateS2CPacket;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public record PocketStoragePayload(Item item, boolean isPressing) implements CustomPayload {
    public static final Id<PocketStoragePayload> ID = new Id<>(Identifier.of(first_mod.MOD_ID, "pocket_storage_payload"));
    public static final PacketCodec<RegistryByteBuf, PocketStoragePayload> CODEC = PacketCodec.tuple(
            PacketCodecs.registryValue(RegistryKeys.ITEM),
            PocketStoragePayload::item,
            PacketCodecs.BOOL,
            PocketStoragePayload::isPressing,

            PocketStoragePayload::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
