package me.xander.firstmod.mixin.accessor;

import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractSkeletonEntity.class)
public interface AbstractSkeletonEntityAccessor {
    @Invoker("initEquipment")
    void firstmod$initEquipment(Random random, LocalDifficulty localDifficult);
}
