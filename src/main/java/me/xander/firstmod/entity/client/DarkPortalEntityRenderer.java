package me.xander.firstmod.entity.client;

import me.xander.firstmod.entity.custom.DarkPortalEntity;
import me.xander.firstmod.entity.custom.FireBlastProjectileEntity;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class DarkPortalEntityRenderer extends EntityRenderer<DarkPortalEntity> {
    public DarkPortalEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(DarkPortalEntity entity) {
        return null;
    }

    @Override
    public boolean shouldRender(DarkPortalEntity entity, Frustum frustum, double x, double y, double z) {
        return true;
    }

    @Override
    public void render(DarkPortalEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

}
