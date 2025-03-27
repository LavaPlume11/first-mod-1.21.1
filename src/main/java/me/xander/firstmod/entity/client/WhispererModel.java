package me.xander.firstmod.entity.client;

import me.xander.firstmod.entity.client.animation.WhispererAnimations;
import me.xander.firstmod.entity.custom.WhispererEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class WhispererModel extends SinglePartEntityModel<WhispererEntity> {
    private final ModelPart whisperer;
    private final ModelPart body;
    private final ModelPart ring;
    private final ModelPart ring2;
    private final ModelPart ring3;
    public WhispererModel(ModelPart root) {
        this.whisperer = root.getChild("whisperer");
        this.body = whisperer.getChild("body");
        this.ring = whisperer.getChild("ring");
        this.ring2 = whisperer.getChild("ring2");
        this.ring3 = whisperer.getChild("ring3");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData whisperer = modelPartData.addChild("whisperer", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData ring = whisperer.addChild("ring", ModelPartBuilder.create().uv(0, 16).cuboid(-8.0F, 0.0F, 6.0F, 14.0F, 0.0F, 2.0F, new Dilation(0.0F))
                .uv(32, 6).cuboid(-8.0F, -1.0F, -4.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -6.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        ModelPartData cube_r1 = ring.addChild("cube_r1", ModelPartBuilder.create().uv(0, 22).cuboid(-12.0F, 0.0F, -1.0F, 14.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(7.0F, 0.0F, -4.0F, 0.0F, 1.5708F, 0.0F));

        ModelPartData cube_r2 = ring.addChild("cube_r2", ModelPartBuilder.create().uv(0, 20).cuboid(-12.0F, 0.0F, -1.0F, 14.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-4.0F, 0.0F, -7.0F, 0.0F, 3.1416F, 0.0F));

        ModelPartData cube_r3 = ring.addChild("cube_r3", ModelPartBuilder.create().uv(0, 18).cuboid(-12.0F, 0.0F, -1.0F, 14.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-7.0F, 0.0F, 4.0F, 0.0F, -1.5708F, 0.0F));

        ModelPartData ring2 = whisperer.addChild("ring2", ModelPartBuilder.create().uv(0, 24).cuboid(-8.0F, 0.0F, 6.0F, 14.0F, 0.0F, 2.0F, new Dilation(0.0F))
                .uv(32, 10).cuboid(-8.0F, -1.0F, -4.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -9.0F, 0.0F, 0.0F, -1.3963F, 0.0F));

        ModelPartData cube_r4 = ring2.addChild("cube_r4", ModelPartBuilder.create().uv(0, 30).cuboid(-12.0F, 0.0F, -1.0F, 14.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(7.0F, 0.0F, -4.0F, 0.0F, 1.5708F, 0.0F));

        ModelPartData cube_r5 = ring2.addChild("cube_r5", ModelPartBuilder.create().uv(0, 28).cuboid(-12.0F, 0.0F, -1.0F, 14.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-4.0F, 0.0F, -7.0F, 0.0F, 3.1416F, 0.0F));

        ModelPartData cube_r6 = ring2.addChild("cube_r6", ModelPartBuilder.create().uv(0, 26).cuboid(-12.0F, 0.0F, -1.0F, 14.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-7.0F, 0.0F, 4.0F, 0.0F, -1.5708F, 0.0F));

        ModelPartData ring3 = whisperer.addChild("ring3", ModelPartBuilder.create().uv(0, 32).cuboid(-8.0F, 0.0F, 6.0F, 14.0F, 0.0F, 2.0F, new Dilation(0.0F))
                .uv(32, 14).cuboid(-8.0F, -1.0F, -4.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -12.0F, 0.0F, 0.0F, -1.8762F, 0.0F));

        ModelPartData cube_r7 = ring3.addChild("cube_r7", ModelPartBuilder.create().uv(32, 4).cuboid(-12.0F, 0.0F, -1.0F, 14.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(7.0F, 0.0F, -4.0F, 0.0F, 1.5708F, 0.0F));

        ModelPartData cube_r8 = ring3.addChild("cube_r8", ModelPartBuilder.create().uv(32, 2).cuboid(-12.0F, 0.0F, -1.0F, 14.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-4.0F, 0.0F, -7.0F, 0.0F, 3.1416F, 0.0F));

        ModelPartData cube_r9 = ring3.addChild("cube_r9", ModelPartBuilder.create().uv(32, 0).cuboid(-12.0F, 0.0F, -1.0F, 14.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-7.0F, 0.0F, 4.0F, 0.0F, -1.5708F, 0.0F));

        ModelPartData body = whisperer.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -13.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);                                                                                                                                                                   /*0.0F*/   /*0.0F*/        /*0.0F*/
    }

    @Override
    public void setAngles(WhispererEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.setHeadAngles(netHeadYaw, headPitch);

        this.animateMovement(WhispererAnimations.WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.updateAnimation(entity.idleAnimationState, WhispererAnimations.IDLE, ageInTicks, 1f);
        ring3.visible = entity.getHealth() == entity.getMaxHealth();
        ring.visible = entity.getHealth() >= (entity.getMaxHealth() / 2f);
    }


    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -30F, 30F);
        headPitch = MathHelper.clamp(headPitch, -25F, 45F);
        /*body for both*/               /*0.017453292F*/
        this.body.yaw = headYaw * 0.017453292F;
        this.body.pitch = headPitch * 0.017453292F;
    }                                       /*0.017453292F*/

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        whisperer.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public ModelPart getPart() {
        return whisperer;
    }

}
