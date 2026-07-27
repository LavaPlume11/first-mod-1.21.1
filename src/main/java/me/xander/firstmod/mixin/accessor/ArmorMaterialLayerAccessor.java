package me.xander.firstmod.mixin.accessor;

import net.minecraft.item.ArmorMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ArmorMaterial.Layer.class)
public interface ArmorMaterialLayerAccessor {
    @Accessor("suffix")
    @Mutable
     void firstmod$setSuffix(String suffix);
    @Accessor("suffix")
    String firstmod$getSuffix();
}
