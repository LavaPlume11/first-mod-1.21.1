package me.xander.firstmod.entity.client;

import me.xander.first_mod;
import me.xander.firstmod.entity.custom.CloneEntity;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CloneEntityRenderer extends LivingEntityRenderer<CloneEntity, BipedEntityModel<CloneEntity>> {


    public CloneEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BipedEntityModel<>(ctx.getPart(ModEntityModelLayers.CLONE)), 0.2f);
    }

    @Override
    public Identifier getTexture(CloneEntity entity) {
        return Identifier.of(first_mod.MOD_ID, "textures/entity/clone/clone.png");
    }

    @Override
    public void render(CloneEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {

        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    protected void renderLabelIfPresent(CloneEntity entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, float tickDelta) {

    }
}
