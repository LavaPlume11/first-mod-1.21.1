package me.xander.firstmod.entity.client;

import me.xander.first_mod;
import me.xander.firstmod.entity.custom.WhispererEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class WhispererRenderer extends MobEntityRenderer<WhispererEntity, WhispererModel> {
    public WhispererRenderer(EntityRendererFactory.Context context) {
        super(context, new WhispererModel(context.getPart(ModEntityModelLayers.WHISPERER)), 0.5f);
    }

    @Override
    public Identifier getTexture(WhispererEntity entity) {
        return Identifier.of(first_mod.MOD_ID, "textures/entity/whisperer/whisperer_water.png");
    }

    @Override
    public void render(WhispererEntity livingEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        if (livingEntity.isBaby()) {
            matrixStack.scale(0.5f,0.5f,0.5f);
        }

        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
