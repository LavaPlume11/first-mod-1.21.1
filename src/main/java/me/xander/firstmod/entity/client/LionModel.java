package me.xander.firstmod.entity.client;

import me.xander.firstmod.entity.client.animation.LionAnimations;
import me.xander.firstmod.entity.custom.LionEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class LionModel extends SinglePartEntityModel<LionEntity> {
    private final ModelPart lion;
    private final ModelPart body;
    private final ModelPart head;


    public LionModel(ModelPart root) {
        this.lion = root.getChild("lion");
        this.body = this.lion.getChild("body");
        this.head = this.lion.getChild("head");



    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData whole = modelPartData.addChild("lion", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData leg1 = whole.addChild("leg1", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData leg1seg1 = leg1.addChild("leg1seg1", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -8.0F, -14.5F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F))
                .uv(-1, -1).cuboid(-8.0F, -2.0F, -14.0F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData cube_r1 = leg1seg1.addChild("cube_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-7.0F, -2.5F, -12.25F, -0.4363F, 0.0F, 0.0F));

        ModelPartData leg1seg2 = leg1.addChild("leg1seg2", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -15.75F, -14.5F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData leg2 = whole.addChild("leg2", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData leg2seg1 = leg2.addChild("leg2seg1", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -8.0F, 0.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-1.0F, -2.0F, 1.5F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(8.0F, 0.0F, -14.5F));

        ModelPartData cube_r2 = leg2seg1.addChild("cube_r2", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -0.25F, 2.25F, -0.4363F, 0.0F, 0.0F));

        ModelPartData leg2seg2 = leg2.addChild("leg2seg2", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -7.0F, -1.0F, 2.0F, 7.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(8.0F, -8.0F, -13.5F));

        ModelPartData leg3 = whole.addChild("leg3", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData leg3seg1 = leg3.addChild("leg3seg1", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData leg3seg2 = leg3.addChild("leg3seg2", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData leg4 = whole.addChild("leg4", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData leg4seg1 = leg4.addChild("leg4seg1", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData leg4seg2 = leg4.addChild("leg4seg2", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData body = whole.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData belly = body.addChild("belly", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData head = whole.addChild("head", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData mouth = head.addChild("mouth", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData tail = whole.addChild("tail", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData tailseg2 = tail.addChild("tailseg2", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData tailseg3 = tailseg2.addChild("tailseg3", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }
    @Override
    public void setAngles(LionEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.setHeadAngels(netHeadYaw, headPitch);


        this.animateMovement(LionAnimations.walking, limbSwing, limbSwingAmount,2f,2.5f);
        this.updateAnimation(entity.IdleAnimationState, LionAnimations.licketydickety,ageInTicks,1f);

    }


    private void setHeadAngels(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -30.0F,30.0f);
        headPitch = MathHelper.clamp(headPitch, -25.0F,45.0f);

        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        lion.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public ModelPart getPart() {
        return body;
    }
}
