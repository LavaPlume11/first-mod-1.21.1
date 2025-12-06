package me.xander.firstmod.entity.client;

import me.xander.first_mod;
import me.xander.firstmod.entity.custom.SteamGolemEntity;
import me.xander.firstmod.entity.custom.WhispererEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.IronGolemEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SteamGolemRenderer extends MobEntityRenderer<SteamGolemEntity, IronGolemEntityModel<SteamGolemEntity>> {
    public SteamGolemRenderer(EntityRendererFactory.Context context) {
        super(context, new IronGolemEntityModel<>(context.getPart(ModEntityModelLayers.STEAM_GOLEM)), 0.9f);
    }

    @Override
    public Identifier getTexture(SteamGolemEntity entity) {
        return Identifier.of(first_mod.MOD_ID, "textures/entity/steam_golem/steam_golem.png");
    }

    @Override
    public void render(SteamGolemEntity livingEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        if (livingEntity.isBaby()) {
            matrixStack.scale(0.5f,0.5f,0.5f);
        }

        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
