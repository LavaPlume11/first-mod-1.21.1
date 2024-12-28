package me.xander.firstmod.entity.client;

import me.xander.first_mod;
import me.xander.firstmod.entity.custom.SleighEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SleighRenderer extends MobEntityRenderer<SleighEntity,SleighModel<SleighEntity>> {
    public SleighRenderer(EntityRendererFactory.Context context) {
        super(context, new SleighModel<>(context.getPart(ModEntityModelLayers.SLEIGH)), 1f);
    }

    @Override
    public Identifier getTexture(SleighEntity boatEntity) {
        if (!boatEntity.hasControllingPassenger()) {
            return Identifier.of(first_mod.MOD_ID, "textures/entity/sleigh/sleigh.png");
        } else {
            return Identifier.of(first_mod.MOD_ID, "textures/entity/sleigh/sleigh_jingled.png");
        }
    }

    @Override
    public void render(SleighEntity boatEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.scale(2.5f,2.5f,2.5f);
        super.render(boatEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
