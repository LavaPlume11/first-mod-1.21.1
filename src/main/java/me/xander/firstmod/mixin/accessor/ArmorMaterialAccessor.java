package me.xander.firstmod.mixin.accessor;

import net.minecraft.item.ArmorMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(ArmorMaterial.class)
public interface ArmorMaterialAccessor {
    @Accessor("layers")
    List<ArmorMaterial.Layer> firstmod$getLayers();
}
