package me.xander.firstmod.mixin;
import me.xander.first_mod;
import me.xander.firstmod.components.ModDataComponentTypes;
import me.xander.firstmod.item.custom.TrapArmorItem;
import me.xander.firstmod.util.mixin.SlotAccess;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slot.class)
public abstract class SlotMixin implements SlotAccess {
    @Shadow public abstract void markDirty();

    @Final
    @Shadow public Inventory inventory;

    @Shadow public abstract ItemStack getStack();

    @Unique
    protected boolean isLocked = false;

    @Override
    public void first_mod_template_1_21_1$setLocked(boolean locked) {
        isLocked = locked;
        markDirty();
    }

    @Inject(method = "canTakeItems", at = @At(value = "RETURN"), cancellable = true)
    private void isLocked(PlayerEntity playerEntity, CallbackInfoReturnable<Boolean> cir) {
        if(Boolean.TRUE.equals(getStack().get(ModDataComponentTypes.SLOT_LOCKED))) {
            cir.setReturnValue(false);
        }

    }
    @Inject(method = "canInsert", at = @At(value = "RETURN"), cancellable = true)
    private void isInsertLocked(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if(Boolean.TRUE.equals(getStack().get(ModDataComponentTypes.SLOT_LOCKED))) {
            cir.setReturnValue(false);
        }

    }
}
