package me.xander.firstmod.screen;

import me.xander.first_mod;
import me.xander.firstmod.screen.custom.DisplayScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ModScreenHandlers {
    public static final ScreenHandlerType<DisplayScreenHandler> DISPLAY_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(first_mod.MOD_ID, "display_screen_handler"),
                    new ExtendedScreenHandlerType<>(DisplayScreenHandler::new, BlockPos.PACKET_CODEC));
    public static void registerScreenHandlers() {
        first_mod.LOGGER.info("Registering Screen Handlers for"+ first_mod.MOD_ID);
    }
}
