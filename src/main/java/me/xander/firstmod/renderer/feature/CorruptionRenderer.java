package me.xander.firstmod.renderer.feature;

import me.xander.first_mod;
import me.xander.firstmod.corruption.CorruptionHandler;
import me.xander.firstmod.data.ModData;
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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;


public class CorruptionRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    private static Identifier texture;


    public CorruptionRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context) {
        super(context);
    }


    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
       if (entity instanceof PlayerEntity player) {
           if (CorruptionHandler.getCorruption(player) < 6) {
               texture = null;
           }
           if (CorruptionHandler.getCorruption(player) >= 6) {
               texture = Identifier.of(first_mod.MOD_ID, "textures/entity/player/corruption_1.png");
           } if (CorruptionHandler.getCorruption(player) >= 20) {
               texture = Identifier.of(first_mod.MOD_ID, "textures/entity/player/corruption_2.png");
           }   if (CorruptionHandler.getCorruption(player) >= 50) {
               texture = Identifier.of(first_mod.MOD_ID, "textures/entity/player/corruption_3.png");
           }  if (CorruptionHandler.getCorruption(player) >= 100) {
               texture = Identifier.of(first_mod.MOD_ID, "textures/entity/player/corruption_4.png");
           }

           if (texture == null) {
               return;
           }
           VertexConsumer corruptionConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(texture));
           if (CorruptionHandler.getCorruption(player) > 0)
               this.getContextModel().render(matrices, corruptionConsumer, light, OverlayTexture.DEFAULT_UV);
       }

    }
}
