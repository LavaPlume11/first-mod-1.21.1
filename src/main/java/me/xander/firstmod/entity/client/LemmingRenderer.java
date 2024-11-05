package me.xander.firstmod.entity.client;

import com.google.common.collect.Maps;
import me.xander.first_mod;
import me.xander.firstmod.entity.custom.LemmingEntity;
import me.xander.firstmod.entity.variant.LemmingVariant;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class LemmingRenderer extends MobEntityRenderer<LemmingEntity,LemmingModel> {
    private static final Map<LemmingVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(LemmingVariant.class), map -> {
                map.put(LemmingVariant.GOLD,Identifier.of(first_mod.MOD_ID,"textures/entity/lemming/lemming_gold.png"));
                map.put(LemmingVariant.BLUE,Identifier.of(first_mod.MOD_ID,"textures/entity/lemming/lemming_blue.png"));
            });
    public LemmingRenderer(EntityRendererFactory.Context context) {
        super(context, new LemmingModel(context.getPart(ModEntityModelLayers.LEMMING)), 0.1f);
    }

    @Override
    public Identifier getTexture(LemmingEntity entity) {
        return LOCATION_BY_VARIANT.get(entity.getVariant());
        // OLD CODE: Identifier.of(first_mod.MOD_ID,"textures/entity/lemming/lemming_gold.png");
    }

    @Override
    public void render(LemmingEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {

        if(livingEntity.isBaby()) {
            matrixStack.scale(0.5f,0.5f,0.5f);
        }

        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
