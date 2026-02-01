package me.xander.firstmod.sound;

import me.xander.first_mod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    public static final SoundEvent GUITAR_RIFF = registerSoundEvent("guitar_riff");
    public static final SoundEvent WHISPERER_IDLE = registerSoundEvent("whisperer_idle");
    public static final SoundEvent WELCOME_MESSAGE = registerSoundEvent("welcome_message");
    public static final SoundEvent PORTAL_BOOM = registerSoundEvent("portal_boom");
    public static final SoundEvent STICKING = registerSoundEvent("stick");


    private static SoundEvent registerSoundEvent(String name) {
        return Registry.register(Registries.SOUND_EVENT, Identifier.of(first_mod.MOD_ID, name),
                SoundEvent.of(Identifier.of(first_mod.MOD_ID, name)));
    }
    public static void registerSounds() {
        first_mod.LOGGER.info("Registering Mod Sounds for" + first_mod.MOD_ID);
    }
}
