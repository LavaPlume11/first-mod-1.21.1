package me.xander.firstmod.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

import java.util.Optional;
import java.util.UUID;

public record PlayerOwnerComponent(UUID ownerUuid, Optional<String> ownerName) {

    public static final Codec<PlayerOwnerComponent> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(Codec.STRING.xmap(UUID::fromString, UUID::toString).fieldOf("owner_uuid")
                    .forGetter(PlayerOwnerComponent::ownerUuid), Codec.STRING.optionalFieldOf("owner_name")
                    .forGetter(PlayerOwnerComponent::ownerName)).apply(instance, PlayerOwnerComponent::new));

    public static final PacketCodec<RegistryByteBuf, PlayerOwnerComponent> PACKET_CODEC =
            new PacketCodec<RegistryByteBuf, PlayerOwnerComponent>() {
                @Override
                public PlayerOwnerComponent decode(RegistryByteBuf buf) {
                    long mostSigBits = buf.readLong();
                    long leastSigBits = buf.readLong();
                    boolean hasName = buf.readBoolean();
                    Optional<String> name = Optional.empty();
                    if (hasName) {
                        name = Optional.of(buf.readString());
                    }
                    return new PlayerOwnerComponent(new UUID(mostSigBits, leastSigBits), name);
                }

                @Override
                public void encode(RegistryByteBuf buf, PlayerOwnerComponent value) {
                    buf.writeLong(value.ownerUuid().getMostSignificantBits());
                    buf.writeLong(value.ownerUuid().getLeastSignificantBits());
                    if (value.ownerName().isPresent()) {
                        buf.writeBoolean(true);
                        buf.writeString(value.ownerName().get());
                    } else {
                        buf.writeBoolean(false);
                    }
                }
            };
    public static PlayerOwnerComponent unowned() {
        return new PlayerOwnerComponent(null, Optional.empty());
    }
    public boolean isOwned() {
        return ownerUuid != null;
    }
    public static PlayerOwnerComponent owned(UUID uuid, String name) {
        return new PlayerOwnerComponent(uuid, Optional.ofNullable(name));
    }
}
