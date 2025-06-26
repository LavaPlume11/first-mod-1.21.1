package me.xander.firstmod.components;

import com.mojang.serialization.Codec;
import me.xander.first_mod;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {
    public static final ComponentType<BlockPos> COORDINATES = register("coordinates", builder -> builder.codec(BlockPos.CODEC));
    public static final ComponentType<Float> STAFF_STATE = register("staff_state", builder -> builder.codec(Codec.FLOAT));



    private static <T>ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(first_mod.MOD_ID, name),
                (builderOperator.apply(ComponentType.builder())).build());
    }
    public static void registerDataComponentTypes() {
        first_mod.LOGGER.info("Registering Data Component Types for" + first_mod.MOD_ID);
    }
}
