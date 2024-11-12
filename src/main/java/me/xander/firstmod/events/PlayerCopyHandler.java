package me.xander.firstmod.events;

import me.xander.firstmod.util.IEntityDataSaver;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerCopyHandler implements ServerPlayerEvents.CopyFrom{
    @Override
    public void copyFromPlayer(ServerPlayerEntity serverPlayerEntity, ServerPlayerEntity serverPlayerEntity1, boolean b) {
        ((IEntityDataSaver) serverPlayerEntity1).getPersistentData().putIntArray("firstmod.homepos",
                ((IEntityDataSaver)serverPlayerEntity).getPersistentData().getIntArray("firstmod.homepos"));
    }
}
