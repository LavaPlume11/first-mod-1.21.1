package me.xander.firstmod.renderer.feature;

import me.xander.first_mod;
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
       if (entity instanceof PlayerEntity player && (player.getAttached(ModData.CORRUPTION) != null)) {
           if (player.getAttached(ModData.CORRUPTION) == 1) {
               texture = Identifier.of(first_mod.MOD_ID, "textures/entity/player/corruption_1.png");
           } else if (player.getAttached(ModData.CORRUPTION) == 2) {
               texture = Identifier.of(first_mod.MOD_ID, "textures/entity/player/corruption_2.png");
           } else  if (player.getAttached(ModData.CORRUPTION) == 3) {
               texture = Identifier.of(first_mod.MOD_ID, "textures/entity/player/corruption_3.png");
           } else if (player.getAttached(ModData.CORRUPTION) == 4) {
               texture = Identifier.of(first_mod.MOD_ID, "textures/entity/player/corruption_4.png");
           } else if (player.getAttached(ModData.CORRUPTION) == 5) {
               texture = Identifier.of(first_mod.MOD_ID, "textures/entity/player/corruption_5.png");
               }

           if (texture == null) {
               return;
           }
           VertexConsumer corruptionConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(texture));
           if (player.getAttached(ModData.CORRUPTION) > 0)
               this.getContextModel().render(matrices, corruptionConsumer, light, OverlayTexture.DEFAULT_UV);
       }

    }
}
