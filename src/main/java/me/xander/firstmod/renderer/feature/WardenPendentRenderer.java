package me.xander.firstmod.renderer.feature;

import me.xander.first_mod;
import me.xander.firstmod.item.custom.ModItems;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;


public class WardenPendentRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    private static Identifier texture;


    public WardenPendentRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context) {
        super(context);
    }


    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (entity.getInventory().getArmorStack(2).isOf(ModItems.WARDEN_PENDENT)) {
            texture = Identifier.of(first_mod.MOD_ID, "textures/entity/player/warden_pendent.png");
        } else if (entity.getInventory().getArmorStack(2).isOf(ModItems.DIAMOND_WARDEN_PENDENT))
        {
            texture = Identifier.of(first_mod.MOD_ID, "textures/entity/player/diamond_warden_pendent.png");
        }
        if (texture == null) {
            return;
        }
        VertexConsumer wardenConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(texture));
        if (entity.getInventory().getArmorStack(2).isOf(ModItems.WARDEN_PENDENT) || entity.getInventory().getArmorStack(2).isOf(ModItems.DIAMOND_WARDEN_PENDENT))
            this.getContextModel().render(matrices, wardenConsumer, light, OverlayTexture.DEFAULT_UV);
    }
}
