package me.xander.firstmod.entity.client;

import me.xander.firstmod.entity.custom.DisplayBlockEntityEntity;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class DisplayBlockEntityEntityRenderer extends EntityRenderer<DisplayBlockEntityEntity> {
    public DisplayBlockEntityEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(DisplayBlockEntityEntity entity) {
        return null;
    }

    @Override
    public boolean shouldRender(DisplayBlockEntityEntity entity, Frustum frustum, double x, double y, double z) {
        return true;
    }
}
