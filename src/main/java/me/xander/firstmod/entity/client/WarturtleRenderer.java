package me.xander.firstmod.entity.client;

import me.xander.first_mod;
import me.xander.firstmod.entity.custom.WarturtleEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class WarturtleRenderer extends MobEntityRenderer<WarturtleEntity, WarturtleModel<WarturtleEntity>> {
    public WarturtleRenderer(EntityRendererFactory.Context context) {
        super(context, new WarturtleModel<>(context.getPart(ModEntityModelLayers.WARTURTLE)), 0.9f);
        this.addFeature(new WarturtleArmorFeatureRenderer(this, context.getModelLoader()));
    }

    @Override
    public Identifier getTexture(WarturtleEntity entity) {
        return Identifier.of(first_mod.MOD_ID,"textures/entity/warturtle/warturtle.png");
    }

    @Override
    public void render(WarturtleEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if (livingEntity.isBaby()) {
            matrixStack.scale(0.5f,0.5f,0.5f);
        } else {
            matrixStack.scale(1f,1f,1f);
        }
        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
