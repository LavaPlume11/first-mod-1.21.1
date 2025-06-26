package me.xander.firstmod.block.renderer;

import me.xander.firstmod.block.custom.StoneOfSwordBlock;
import me.xander.firstmod.block.entity.custom.DisplayBlockEntity;
import me.xander.firstmod.block.entity.custom.StoneOfSwordBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

import java.util.Objects;

public class StoneOfSwordBlockEntityRenderer implements BlockEntityRenderer<StoneOfSwordBlockEntity> {

    public int x;
    public int y;
    public int z;

    public StoneOfSwordBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
    }
    private int getLightLevel(World world, BlockPos pos) {
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight, sLight);
    }

    @Override
    public void render(StoneOfSwordBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        Direction Facing = entity.getCachedState().get(StoneOfSwordBlock.FACING);
        if (Facing == Direction.NORTH || Facing == Direction.SOUTH) {
            x = 180;
            z = 45;
            y = 0;
        } else {
            x = 225;
            z = 0;
            y = 90;
        }
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        ItemStack stack = entity.getStack(0);
        matrices.push();
        matrices.translate(0.5, 0.55, 0.5);
        matrices.scale(0.7f, 0.7f, 0.7f);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(x));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(z));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(y));
        itemRenderer.renderItem(stack, ModelTransformationMode.GUI, getLightLevel(entity.getWorld(),
                entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
        matrices.pop();
    }
}



