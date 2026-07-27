package me.xander.firstmod.components;

import com.mojang.serialization.Codec;
import me.xander.first_mod;
import net.minecraft.component.ComponentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.UUID;
import java.util.function.UnaryOperator;

public class ModDataComponentTypes {
    public static final ComponentType<Float> STAFF_STATE = register("staff_state", builder -> builder.codec(Codec.FLOAT));
    public static final ComponentType<PlayerOwnerComponent> PLAYER_OWNER = register("player_owner", builder ->
            builder.codec(PlayerOwnerComponent.CODEC).packetCodec(PlayerOwnerComponent.PACKET_CODEC));
    public static final ComponentType<Float> LAVA_STATE = register("lava_state", builder -> builder.codec(Codec.FLOAT));
    public static final ComponentType<Boolean> BROKEN = register("is_broken", builder -> builder.codec(Codec.BOOL));
    public static final ComponentType<Boolean> USED = register("is_used", builder -> builder.codec(Codec.BOOL));
    public static final ComponentType<BlockPos> PORTAL_POS = register("portal_pos", blockPosBuilder -> blockPosBuilder.codec(BlockPos.CODEC));
    public static final ComponentType<Boolean> SLOT_LOCKED = register("is_slot_locked", builder -> builder.codec(Codec.BOOL));
    public static final ComponentType<Integer> DEFAULT_INT = register("default_int", builder -> builder.codec(Codec.INT));




    private static <T>ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(first_mod.MOD_ID, name),
                (builderOperator.apply(ComponentType.builder())).build());
    }
    public static void registerDataComponentTypes() {
        first_mod.LOGGER.info("Registering Data Component Types for" + first_mod.MOD_ID);
    }
}
