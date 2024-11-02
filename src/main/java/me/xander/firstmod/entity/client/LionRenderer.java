package me.xander.firstmod.entity.client;

import me.xander.first_mod;
import me.xander.firstmod.entity.custom.LionEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class LionRenderer extends MobEntityRenderer<LionEntity,LionModel> {
    public LionRenderer(EntityRendererFactory.Context context) {
        super(context, new LionModel(context.getPart(ModEntityModelLayers.LION)), 0.5f);
    }

    @Override
    public Identifier getTexture(LionEntity entity) {
        return Identifier.of(first_mod.MOD_ID,"textures/entity/lion/lion_gold.png");
    }

    @Override
    public void render(LionEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {

        if(livingEntity.isBaby()) {
            matrixStack.scale(0.5f,0.5f,0.5f);
        }

        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
