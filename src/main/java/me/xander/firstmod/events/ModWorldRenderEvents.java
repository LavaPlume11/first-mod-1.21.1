package me.xander.firstmod.events;

import me.xander.firstmod.item.custom.DragonscaleWings;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EndCrystalEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class ModWorldRenderEvents {
    private static final RenderLayer CRYSTAL_BEAM_LAYER;
    public static final Identifier CRYSTAL_BEAM_TEXTURE = Identifier.ofVanilla("textures/entity/end_crystal/end_crystal_beam.png");
    public static void runWorldEvents() {
        WorldRenderEvents.LAST.register(worldRenderContext -> {
            AbstractClientPlayerEntity player = MinecraftClient.getInstance().player;
            wingBeam(player, player.bodyYaw, worldRenderContext.tickCounter().getTickDelta(true),
                        worldRenderContext.matrixStack(), worldRenderContext.consumers(),  15728640);

        });

    }
    public static void wingBeam(AbstractClientPlayerEntity player, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {

            float m;
            if (player.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof DragonscaleWings wings && !player.isSpectator()) {
                if (MinecraftClient.getInstance().options.getPerspective() == Perspective.FIRST_PERSON) {
                if (wings.connectedCrystal != null) {
                    wings.setDoCrystalFirstPerson(true);
                    matrixStack.push();
                    m = (float) (wings.connectedCrystal.getX() - MathHelper.lerp((double) g, player.prevX, player.getX()));
                    float n = (float) (wings.connectedCrystal.getY() - MathHelper.lerp((double) g, player.prevY, player.getY()));
                    float o = (float) (wings.connectedCrystal.getZ() - MathHelper.lerp((double) g, player.prevZ, player.getZ()));
                    ModWorldRenderEvents.renderCrystalBeam2(m, n + EndCrystalEntityRenderer.getYOffset(wings.connectedCrystal, g), o, g, player.age, matrixStack, vertexConsumerProvider, i);
                    matrixStack.pop();
                }
            } else {
                    wings.setDoCrystalFirstPerson(false);
                }
        }
    }
    public static void renderCrystalBeam(float dx, float dy, float dz, float tickDelta, int age, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        float f = MathHelper.sqrt(dx * dx + dz * dz);
        float g = MathHelper.sqrt(dx * dx + dy * dy + dz * dz);
        matrices.push();
        matrices.translate(0.0F, 1.F, 0.0F);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotation((float)(-Math.atan2((double)dz, (double)dx)) - 1.5707964F));
        matrices.multiply(RotationAxis.POSITIVE_X.rotation((float)(-Math.atan2((double)f, (double)dy)) - 1.5707964F));
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(CRYSTAL_BEAM_LAYER);
        float h = 0.0F - ((float)age + tickDelta) * 0.01F;
        float i = MathHelper.sqrt(dx * dx + dy * dy + dz * dz) / 32.0F - ((float)age + tickDelta) * 0.01F;
        float k = 0.0F;
        float l = 0.75F;
        float m = 0.0F;
        MatrixStack.Entry entry = matrices.peek();

        for(int n = 1; n <= 8; ++n) {
            float o = MathHelper.sin((float)n * 6.2831855F / 8.0F) * 0.75F;
            float p = MathHelper.cos((float)n * 6.2831855F / 8.0F) * 0.75F;
            float q = (float)n / 8.0F;
            vertexConsumer.vertex(entry, k * 0.2F, l * 0.2F, 0.0F).color(Colors.BLACK).texture(m, h).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, 0.0F, -1.0F, 0.0F);
            vertexConsumer.vertex(entry, k, l, g).color(Colors.WHITE).texture(m, i).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, 0.0F, -1.0F, 0.0F);
            vertexConsumer.vertex(entry, o, p, g).color(Colors.WHITE).texture(q, i).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, 0.0F, -1.0F, 0.0F);
            vertexConsumer.vertex(entry, o * 0.2F, p * 0.2F, 0.0F).color(Colors.BLACK).texture(q, h).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, 0.0F, -1.0F, 0.0F);
            k = o;
            l = p;
            m = q;
        }

        matrices.pop();
    }
    public static void renderCrystalBeam2(float dx, float dy, float dz, float tickDelta, int age, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        float f = MathHelper.sqrt(dx * dx + dz * dz);
        float g = MathHelper.sqrt(dx * dx + dy * dy + dz * dz);
        matrices.push();
        matrices.translate(0.0F, -0.5F, 0.0F);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotation((float)(-Math.atan2((double)dz, (double)dx)) - 1.5707964F));
        matrices.multiply(RotationAxis.POSITIVE_X.rotation((float)(-Math.atan2((double)f, (double)dy)) - 1.5707964F));
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(CRYSTAL_BEAM_LAYER);
        float h = 0.0F - ((float)age + tickDelta) * 0.01F;
        float i = MathHelper.sqrt(dx * dx + dy * dy + dz * dz) / 32.0F - ((float)age + tickDelta) * 0.01F;
        float k = 0.0F;
        float l = 0.75F;
        float m = 0.0F;
        MatrixStack.Entry entry = matrices.peek();

        for(int n = 1; n <= 8; ++n) {
            float o = MathHelper.sin((float)n * 6.2831855F / 8.0F) * 0.75F;
            float p = MathHelper.cos((float)n * 6.2831855F / 8.0F) * 0.75F;
            float q = (float)n / 8.0F;
            vertexConsumer.vertex(entry, k * 0.2F, l * 0.2F, 0.0F).color(Colors.BLACK).texture(m, h).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, 0.0F, -1.0F, 0.0F);
            vertexConsumer.vertex(entry, k, l, g).color(Colors.WHITE).texture(m, i).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, 0.0F, -1.0F, 0.0F);
            vertexConsumer.vertex(entry, o, p, g).color(Colors.WHITE).texture(q, i).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, 0.0F, -1.0F, 0.0F);
            vertexConsumer.vertex(entry, o * 0.2F, p * 0.2F, 0.0F).color(Colors.BLACK).texture(q, h).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, 0.0F, -1.0F, 0.0F);
            k = o;
            l = p;
            m = q;
        }

        matrices.pop();
    }
    static {
        CRYSTAL_BEAM_LAYER = RenderLayer.getEntitySmoothCutout(CRYSTAL_BEAM_TEXTURE);
    }
}
