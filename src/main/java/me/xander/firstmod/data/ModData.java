package me.xander.firstmod.data;

import com.mojang.serialization.Codec;
import me.xander.first_mod;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.util.Identifier;

public class ModData {
    public static final AttachmentType<Integer> CORRUPTION = AttachmentRegistry.createPersistent(Identifier.of(first_mod.MOD_ID, "corruption"),
            Codec.INT);
    public static void registerModData() {
        first_mod.LOGGER.info("Registering Data for"+ first_mod.MOD_ID);
    }
}
