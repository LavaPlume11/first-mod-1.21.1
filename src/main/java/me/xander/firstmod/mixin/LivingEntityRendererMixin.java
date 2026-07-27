package me.xander.firstmod.mixin;

import me.xander.firstmod.events.ModWorldRenderEvents;
import me.xander.firstmod.item.custom.DragonscaleWings;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EndCrystalEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> {
    protected LivingEntityRendererMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }
    @Inject(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
            at = @At(value = "HEAD"))
    public void wingBeam(T livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        float m;
        boolean bl = true;
        if (livingEntity instanceof PlayerEntity && livingEntity.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof DragonscaleWings wings && wings.doCrystalFirstPerson) {
            bl = false;
        }
        if (livingEntity.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof DragonscaleWings wings && bl && !livingEntity.isSpectator()) {
            if (wings.connectedCrystal != null) {
                matrixStack.push();
                m = (float) (wings.connectedCrystal.getX() - MathHelper.lerp((double) g, livingEntity.prevX, livingEntity.getX()));
                float n = (float) (wings.connectedCrystal.getY() - MathHelper.lerp((double) g, livingEntity.prevY, livingEntity.getY()));
                float o = (float) (wings.connectedCrystal.getZ() - MathHelper.lerp((double) g, livingEntity.prevZ, livingEntity.getZ()));
                ModWorldRenderEvents.renderCrystalBeam(m, n + EndCrystalEntityRenderer.getYOffset(wings.connectedCrystal, g), o, g, livingEntity.age, matrixStack, vertexConsumerProvider, i);

                matrixStack.pop();
            }
        }
    }
}
