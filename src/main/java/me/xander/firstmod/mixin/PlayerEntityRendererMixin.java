package me.xander.firstmod.mixin;

import me.xander.firstmod.events.ModWorldRenderEvents;
import me.xander.firstmod.item.custom.DragonscaleWings;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EndCrystalEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Inject(method = "render(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
            at = @At(value = "HEAD"))
    public void wingBeam(AbstractClientPlayerEntity player, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        float m;
        if (player.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof DragonscaleWings wings && !wings.doCrystalFirstPerson && !player.isSpectator()) {
            if (wings.connectedCrystal != null) {
                matrixStack.push();
                m = (float) (wings.connectedCrystal.getX() - MathHelper.lerp((double) g, player.prevX, player.getX()));
                float n = (float) (wings.connectedCrystal.getY() - MathHelper.lerp((double) g, player.prevY, player.getY()));
                float o = (float) (wings.connectedCrystal.getZ() - MathHelper.lerp((double) g, player.prevZ, player.getZ()));
                ModWorldRenderEvents.renderCrystalBeam(m, n + EndCrystalEntityRenderer.getYOffset(wings.connectedCrystal, g), o, g, player.age, matrixStack, vertexConsumerProvider, i);

                matrixStack.pop();
            }
        }
    }

}
