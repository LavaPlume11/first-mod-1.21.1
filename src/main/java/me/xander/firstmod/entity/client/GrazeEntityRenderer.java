package me.xander.firstmod.entity.client;

import me.xander.first_mod;
import me.xander.firstmod.entity.custom.GrazeEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class GrazeEntityRenderer extends MobEntityRenderer<GrazeEntity, GrazeEntityModel> {
    public GrazeEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new GrazeEntityModel(context.getPart(ModEntityModelLayers.GRAZE)), 0.5f);
    }

    @Override
    public Identifier getTexture(GrazeEntity entity) {
        return Identifier.of(first_mod.MOD_ID, "textures/entity/graze/graze.png");
    }

    @Override
    public void render(GrazeEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

}
