package me.xander.firstmod.mixin;

import com.mojang.authlib.GameProfile;
import me.xander.firstmod.item.custom.ModItems;
import me.xander.firstmod.item.custom.NetherBow;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin extends PlayerEntity {
    public AbstractClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(method = "getFovMultiplier", at = @At(value = "TAIL"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private void getFovMultiplierMixin(CallbackInfoReturnable<Float> info, float f) {
        Item item = this.getActiveItem().getItem();
        ItemStack itemStack = this.getActiveItem();
        if (this.isUsingItem() && itemStack.isOf(ModItems.BANANA)) {
              int i = this.getItemUseTime();
                float g = (float)i / 20.0f;
                g = g > 1.0f ? 1.0f : (g * g);
                f *= 1.0f - g * 0.15f;
                info.setReturnValue(MathHelper.lerp(MinecraftClient.getInstance().options.getFovEffectScale().getValue().floatValue(),1.0f, f));

        }
         if (this.isUsingItem() && itemStack.isOf(ModItems.BURNT_BANANA)) {
            int i = this.getItemUseTime();
            float g = (float)i / 2.0f;
            g = g > 1.0f ? 1.0f : (g * g);
            f *= 1.0f - g * 0.15f;
            info.setReturnValue(MathHelper.lerp(MinecraftClient.getInstance().options.getFovEffectScale().getValue().floatValue(),1.0f, f));

        }

        if (this.isUsingItem() && itemStack.isOf(ModItems.NETHER_BOW)) {
           if (NetherBow.isUsingFireCharge) {
               int i = this.getItemUseTime();
               float g = (float) i / 15.0f;
               g = g > 2.0f ? 2.0f : (g * g);
               f *= 1.0f - g * 0.15f;
               info.setReturnValue(MathHelper.lerp(MinecraftClient.getInstance().options.getFovEffectScale().getValue().floatValue(), 2.0f, f));

           } else {
               int i = this.getItemUseTime();
               float g = (float) i / 20.0f;
               g = g > 1.0f ? 1.0f : (g * g);
               f *= 1.0f - g * 0.15f;
               info.setReturnValue(MathHelper.lerp(MinecraftClient.getInstance().options.getFovEffectScale().getValue().floatValue(), 1.0f, f));


           }
       }


    }
}
