package me.xander.firstmod.mixin;

import me.xander.first_mod;
import me.xander.firstmod.item.custom.TrapArmorItem;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;

@Mixin(ArmorFeatureRenderer.class)
public abstract class ArmorFeatureRendererMixin<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> extends FeatureRenderer<T, M> {
    public ArmorFeatureRendererMixin(FeatureRendererContext<T, M> context) {
        super(context);
    }
    @Shadow
    protected abstract void renderArmorParts(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, A model, int j, Identifier identifier);
    @Shadow
    protected abstract void renderTrim(RegistryEntry<ArmorMaterial> armorMaterial, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ArmorTrim trim, A model, boolean leggings);
    @Shadow
    protected abstract void renderGlint(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, A model);

    @Inject(method = "renderArmor", at = @At(value = "INVOKE", target = "Ljava/util/Iterator;hasNext()Z"), cancellable = true)
    public void renderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T entity, EquipmentSlot armorSlot, int light, A model, CallbackInfo ci) {
        ItemStack itemStack = entity.getEquippedStack(armorSlot);
        if (itemStack.getItem() instanceof TrapArmorItem armorItem) {
            Iterator<ArmorMaterial.Layer> var12 =  armorItem.getMaterial().value().layers().iterator();
            boolean bl = armorSlot == EquipmentSlot.LEGS;
            while(var12.hasNext()) {
                ArmorMaterial.Layer layer = (ArmorMaterial.Layer)var12.next();
                int j = layer.isDyeable() ? light : -1;
                if (!armorItem.isTriggered(itemStack)) {
                    this.renderArmorParts(matrices, vertexConsumers, light, model, j, layer.getTexture(bl));
                } else {
                    this.renderArmorParts(matrices, vertexConsumers, light, model, j, getTrapTexture(bl));
                }
            }

            ArmorTrim armorTrim = (ArmorTrim)itemStack.get(DataComponentTypes.TRIM);
            if (armorTrim != null) {
                this.renderTrim(armorItem.getMaterial(), matrices, vertexConsumers, light, armorTrim, model, bl);
            }

            if (itemStack.hasGlint()) {
                this.renderGlint(matrices, vertexConsumers, light, model);
            }
            ci.cancel();
        }
    }
    @Unique
    private Identifier getTrapTexture(boolean bl) {
        if (bl) {
            return Identifier.of(first_mod.MOD_ID, "textures/models/armor/mithril_layer_2_triggered.png");
        } else  {
            return Identifier.of(first_mod.MOD_ID, "textures/models/armor/mithril_layer_1_triggered.png");
        }
    }
}
