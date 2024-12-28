package me.xander.firstmod.entity.client;

import me.xander.firstmod.entity.custom.SleighEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class SleighModel<T extends SleighEntity>extends SinglePartEntityModel<T> {
    private final ModelPart sleigh;




    public SleighModel(ModelPart root) {
        this.sleigh = root.getChild("sleigh");


    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData sleigh = modelPartData.addChild("sleigh", ModelPartBuilder.create(), ModelTransform.pivot(-7.0F, 20.0F, 21.0F));

        ModelPartData tracks = sleigh.addChild("tracks", ModelPartBuilder.create().uv(78, 0).cuboid(13.0F, 2.0F, -39.0F, 2.0F, 2.0F, 37.0F, new Dilation(0.0F))
                .uv(0, 96).cuboid(-1.0F, 2.0F, -39.0F, 2.0F, 2.0F, 37.0F, new Dilation(0.0F))
                .uv(94, 110).cuboid(13.0F, 0.0F, -2.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(112, 47).cuboid(-1.0F, 0.0F, -2.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(94, 114).cuboid(13.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(114, 104).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 2.0F, 0.0F));

        ModelPartData jinglebells = tracks.addChild("jinglebells", ModelPartBuilder.create().uv(102, 116).cuboid(13.0F, 0.0F, -10.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(110, 124).cuboid(13.0F, 0.0F, -6.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(110, 116).cuboid(13.0F, 0.0F, -14.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(78, 118).cuboid(13.0F, 0.0F, -18.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(86, 118).cuboid(13.0F, 0.0F, -22.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(94, 118).cuboid(13.0F, 0.0F, -26.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(118, 116).cuboid(13.0F, 0.0F, -30.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(120, 47).cuboid(13.0F, 0.0F, -34.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(102, 120).cuboid(-1.0F, 0.0F, -6.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(110, 120).cuboid(-1.0F, 0.0F, -10.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(118, 120).cuboid(-1.0F, 0.0F, -14.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(78, 122).cuboid(-1.0F, 0.0F, -18.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(124, 108).cuboid(-1.0F, 0.0F, -22.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(86, 122).cuboid(-1.0F, 0.0F, -26.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(102, 124).cuboid(-1.0F, 0.0F, -30.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(94, 122).cuboid(-1.0F, 0.0F, -34.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(122, 104).cuboid(-1.0F, 0.0F, -38.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData basket = sleigh.addChild("basket", ModelPartBuilder.create().uv(0, 51).cuboid(-1.0F, -7.0F, -39.0F, 1.0F, 8.0F, 37.0F, new Dilation(0.0F))
                .uv(76, 51).cuboid(14.0F, -7.0F, -39.0F, 1.0F, 8.0F, 37.0F, new Dilation(0.0F))
                .uv(78, 96).cuboid(-1.0F, -7.0F, -2.0F, 16.0F, 7.0F, 1.0F, new Dilation(0.0F))
                .uv(78, 39).cuboid(-1.0F, -11.0F, -40.0F, 16.0F, 11.0F, 1.0F, new Dilation(0.0F))
                .uv(78, 104).cuboid(-1.0F, -6.0F, -1.0F, 16.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, -1.0F));

        ModelPartData cube_r1 = basket.addChild("cube_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -11.0F, -18.0F, 2.0F, 14.0F, 37.0F, new Dilation(0.0F)), ModelTransform.of(11.0F, 0.0F, -21.0F, 0.0F, 0.0F, -1.5708F));

        ModelPartData presents = basket.addChild("presents", ModelPartBuilder.create().uv(108, 108).cuboid(0.0F, -4.0F, -39.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
                .uv(78, 110).cuboid(4.0F, -4.0F, -39.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
                .uv(112, 39).cuboid(3.0F, -4.0F, -35.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
                .uv(112, 96).cuboid(2.0F, -8.0F, -39.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData reins = basket.addChild("reins", ModelPartBuilder.create().uv(78, 108).cuboid(0.0F, -8.0F, -4.0F, 14.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 1.0F));
        return TexturedModelData.of(modelData, 256, 256);
    }
    @Override
    public void setAngles(SleighEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
    }
    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        sleigh.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public ModelPart getPart() {
        return sleigh;
    }
}
