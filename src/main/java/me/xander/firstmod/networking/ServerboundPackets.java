package me.xander.firstmod.networking;

import me.xander.firstmod.networking.packet.CorruptionPayload;
import me.xander.firstmod.sound.ModSounds;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.sound.SoundCategory;

public class ServerboundPackets {
    // SERVER ONLY
    public static void handleTestPayload(CorruptionPayload testPayload, ServerPlayNetworking.Context context) {
        context.player().getServerWorld().playSound(null, context.player().getBlockPos(), ModSounds.GUITAR_RIFF, SoundCategory.NEUTRAL, 1.0f, 1.0f);
    }
}
