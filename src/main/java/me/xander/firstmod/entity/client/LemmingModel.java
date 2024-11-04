package me.xander.firstmod.entity.client;

import me.xander.firstmod.entity.client.animation.LemmingAnimations;
import me.xander.firstmod.entity.client.animation.LionAnimations;
import me.xander.firstmod.entity.custom.LemmingEntity;
import me.xander.firstmod.entity.custom.LionEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class LemmingModel extends SinglePartEntityModel<LemmingEntity> {
    private final ModelPart whole;
    private final ModelPart leg1;
    private final ModelPart leg2;
    private final ModelPart head;
    private final ModelPart body;
    public LemmingModel(ModelPart root) {
        this.whole = root.getChild("whole");
        this.leg1 = this.whole.getChild("leg1");
        this.leg2 = this.whole.getChild("leg2");
        this.head = this.whole.getChild("head");
        this.body = this.whole.getChild("body");
    }

    public LemmingModel(ModelPart whole, ModelPart leg1, ModelPart leg2, ModelPart head, ModelPart body) {
        this.whole = whole;
        this.leg1 = leg1;
        this.leg2 = leg2;
        this.head = head;
        this.body = body;
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData whole = modelPartData.addChild("whole", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData leg1 = whole.addChild("leg1", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData leg2 = whole.addChild("leg2", ModelPartBuilder.create().uv(8, 4).cuboid(1.0F, -3.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 1.0F, 0.0F));

        ModelPartData head = whole.addChild("head", ModelPartBuilder.create().uv(8, 0).cuboid(-5.0F, -6.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData body = whole.addChild("body", ModelPartBuilder.create().uv(8, 8).cuboid(-1.0F, -4.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 8).cuboid(-3.0F, -4.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 4).cuboid(1.0F, -4.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 16, 16);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        whole.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public ModelPart getPart() {
        return body;
    }

    @Override
    public void setAngles(LemmingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.setHeadAngels(netHeadYaw, headPitch);


        this.animateMovement(LemmingAnimations.walking, limbSwing, limbSwingAmount,2f,2.5f);
        this.updateAnimation(entity.IdleAnimationState, LemmingAnimations.idle,ageInTicks,1f);

    }


    private void setHeadAngels(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -30.0F,30.0f);
        headPitch = MathHelper.clamp(headPitch, -25.0F,45.0f);

        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
    }
}
