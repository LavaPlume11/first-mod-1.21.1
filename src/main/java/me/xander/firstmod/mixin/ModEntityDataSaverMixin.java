package me.xander.firstmod.mixin;

import me.xander.firstmod.util.IEntityDataSaver;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(Entity.class)
public abstract class ModEntityDataSaverMixin implements IEntityDataSaver {

    private NbtCompound persistentData;

    @Override
    public NbtCompound getPersistentData() {
        if (persistentData == null) {
            this.persistentData = new NbtCompound();
        }

        return persistentData;
    }
    @Inject(method = "writeNbt", at = @At("HEAD"))
    protected void ingectWriteMethod(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> info) {
        if (this.persistentData != null) {
            nbt.put("firstmod.custom_data",persistentData);
        }
    }
    @Inject(method = "readNbt", at = @At("HEAD"))
    protected void ingectReadMethod(NbtCompound nbt, CallbackInfo info) {
        if (nbt.contains("firstmod.custom_data",10)) {
            this.persistentData = nbt.getCompound("firstmod.custom_data");
        }
    }
}
