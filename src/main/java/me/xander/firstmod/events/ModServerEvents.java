package me.xander.firstmod.events;

import me.xander.firstmod.corruption.CorruptionHandler;
import me.xander.firstmod.data.ModData;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;

public class ModServerEvents {
    public static void runServerEvents() {
        ServerPlayerEvents.COPY_FROM.register((serverPlayerEntity, serverPlayerEntity1, b) -> {
            CorruptionHandler.setCorruption(serverPlayerEntity1, serverPlayerEntity.getAttached(ModData.CORRUPTION));
        });
        ServerPlayerEvents.JOIN.register(serverPlayerEntity -> {
            CorruptionHandler.addCorruption(serverPlayerEntity, 0);
        });
        ServerPlayerEvents.AFTER_RESPAWN.register((serverPlayerEntity, serverPlayerEntity1, b) -> {
            CorruptionHandler.setCorruption(serverPlayerEntity1, serverPlayerEntity.getAttached(ModData.CORRUPTION));
        });
    }
}
