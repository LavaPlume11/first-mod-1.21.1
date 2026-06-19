package me.xander.firstmod.entity.client;

import me.xander.firstmod.entity.client.animation.GrazeAnimations;
import me.xander.firstmod.entity.custom.GrazeEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class GrazeEntityModel extends SinglePartEntityModel<GrazeEntity> {
    private final ModelPart graze;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart ring1;
    private final ModelPart ring3;
    private final ModelPart ring2;
    public GrazeEntityModel(ModelPart root) {
        this.graze = root.getChild("graze");
        this.body = this.graze.getChild("body");
        this.head = this.body.getChild("head");
        this.ring1 = this.graze.getChild("ring1");
        this.ring3 = this.graze.getChild("ring3");
        this.ring2 = this.graze.getChild("ring2");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData graze = modelPartData.addChild("graze", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData body = graze.addChild("body", ModelPartBuilder.create().uv(36, 11).cuboid(-2.0F, -4.0F, -1.0F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 46).cuboid(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(36, 4).cuboid(-3.0F, -6.0F, -2.0F, 5.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, 0.0F));

        ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -11.0F, -4.0F, 9.0F, 9.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -4.0F, 0.0F));

        ModelPartData ring1 = graze.addChild("ring1", ModelPartBuilder.create().uv(0, 38).cuboid(-13.0F, -2.0F, -4.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -6.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        ModelPartData ring3 = graze.addChild("ring3", ModelPartBuilder.create().uv(32, 38).cuboid(-13.0F, -10.0F, -4.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -6.0F, 0.0F, 0.0F, -2.3562F, 0.0F));

        ModelPartData ring2 = graze.addChild("ring2", ModelPartBuilder.create().uv(16, 38).cuboid(-13.0F, -12.0F, -4.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(GrazeEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.setHeadAngles(netHeadYaw, headPitch);
        this.updateAnimation(entity.idleAnimationState, GrazeAnimations.idle, ageInTicks);
        this.updateAnimation(entity.digAnimationState, GrazeAnimations.dig, ageInTicks);
        this.updateAnimation(entity.emergeAnimationState, GrazeAnimations.emerge, ageInTicks);

    }
    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -30F, 30F);
        headPitch = MathHelper.clamp(headPitch, -25F, 45F);
        this.body.yaw = headYaw * 0.017453292F;
        this.body.pitch = headPitch * 0.017453292F;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        graze.render(matrices, vertexConsumer, light, color);
    }

    @Override
    public ModelPart getPart() {
        return graze;
    }
}
