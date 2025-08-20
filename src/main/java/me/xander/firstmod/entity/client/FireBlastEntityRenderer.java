package me.xander.firstmod.entity.client;

import me.xander.firstmod.entity.custom.FireBlastProjectileEntity;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class FireBlastEntityRenderer extends EntityRenderer<FireBlastProjectileEntity> {
    public FireBlastEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(FireBlastProjectileEntity entity) {
        return null;
    }

    @Override
    public boolean shouldRender(FireBlastProjectileEntity entity, Frustum frustum, double x, double y, double z) {
        return true;
    }

    @Override
    public void render(FireBlastProjectileEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

}
