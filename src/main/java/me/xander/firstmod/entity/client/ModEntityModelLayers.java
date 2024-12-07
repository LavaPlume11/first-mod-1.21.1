package me.xander.firstmod.entity.client;

import me.xander.first_mod;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModEntityModelLayers {
    public static final EntityModelLayer LION =
            new EntityModelLayer(Identifier.of(first_mod.MOD_ID,"lion"),"main");
    public static final EntityModelLayer LEMMING =
            new EntityModelLayer(Identifier.of(first_mod.MOD_ID,"lemming"),"main");
    public static final EntityModelLayer WHISPERER =
            new EntityModelLayer(Identifier.of(first_mod.MOD_ID,"whisperer"),"main");
    public static final EntityModelLayer TOMAHAWK =
            new EntityModelLayer(Identifier.of(first_mod.MOD_ID,"tomahawk"),"main");
}
