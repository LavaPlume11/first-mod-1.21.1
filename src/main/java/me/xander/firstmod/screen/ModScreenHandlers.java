package me.xander.firstmod.screen;

import me.xander.first_mod;
import me.xander.firstmod.screen.custom.*;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.Uuids;
import net.minecraft.util.math.BlockPos;

public class ModScreenHandlers {
    public static final ScreenHandlerType<DisplayScreenHandler> DISPLAY_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(first_mod.MOD_ID, "display_screen_handler"),
                    new ExtendedScreenHandlerType<>(DisplayScreenHandler::new, BlockPos.PACKET_CODEC));
    public static final ScreenHandlerType<CrystallizerScreenHandler> CRYSTALLIZER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(first_mod.MOD_ID,"crystllizer_screen_handler"),
                    new ExtendedScreenHandlerType<>(CrystallizerScreenHandler::new, BlockPos.PACKET_CODEC));
    public static final ScreenHandlerType<WarturtleScreenHandler> WARTURTLE_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(first_mod.MOD_ID, "warturtle_screen_handler"),
                    new ExtendedScreenHandlerType<>(WarturtleScreenHandler::create, Uuids.PACKET_CODEC));
    public static final ScreenHandlerType<EchoGeneratorScreenHandler> ECHO_GENERATOR_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(first_mod.MOD_ID,"echo_generator_screen_handler"),
                    new ExtendedScreenHandlerType<>(EchoGeneratorScreenHandler::new, BlockPos.PACKET_CODEC));
    public static final ScreenHandlerType<TankScreenHandler> TANK_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(first_mod.MOD_ID,"tank_screen_handler"),
                    new ExtendedScreenHandlerType<>(TankScreenHandler::new, BlockPos.PACKET_CODEC));
    public static void registerScreenHandlers() {
        first_mod.LOGGER.info("Registering Screen Handlers for"+ first_mod.MOD_ID);
    }
}
