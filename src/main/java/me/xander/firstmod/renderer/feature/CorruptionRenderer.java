package me.xander.firstmod.renderer.feature;

import me.xander.first_mod;
import me.xander.firstmod.corruption.CorruptionHandler;
import me.xander.firstmod.effect.ModEffects;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.Identifier;


public class CorruptionRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    private static Identifier texture;


    public CorruptionRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context) {
        super(context);
    }


    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
           if (CorruptionHandler.getCorruption(entity) < 6) {
               texture = null;
           }
           if (entity.hasStatusEffect(ModEffects.CORRUPTED)) {
               StatusEffectInstance instance = entity.getStatusEffect(ModEffects.CORRUPTED);
               if (instance.getAmplifier() >= 0) {
                   texture = Identifier.of(first_mod.MOD_ID, "textures/entity/player/corruption_1.png");
               }
               if (instance.getAmplifier() >= 1) {
                   texture = Identifier.of(first_mod.MOD_ID, "textures/entity/player/corruption_2.png");
               }
               if (instance.getAmplifier() >= 2) {
                   texture = Identifier.of(first_mod.MOD_ID, "textures/entity/player/corruption_3.png");
               }
               if (instance.getAmplifier() >= 3) {
                   texture = Identifier.of(first_mod.MOD_ID, "textures/entity/player/corruption_4.png");
               }
           }
           if (texture == null) {
               return;
           }
           VertexConsumer corruptionConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(texture));
           if (CorruptionHandler.getCorruption(entity) > 0)
               this.getContextModel().render(matrices, corruptionConsumer, light, OverlayTexture.DEFAULT_UV);

    }
}
