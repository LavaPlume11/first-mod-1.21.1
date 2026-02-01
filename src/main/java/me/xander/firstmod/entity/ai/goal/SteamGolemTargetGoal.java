package me.xander.firstmod.entity.ai.goal;

import me.xander.firstmod.entity.custom.SteamGolemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.mob.MobEntity;

public class SteamGolemTargetGoal<T extends LivingEntity> extends ActiveTargetGoal<T> {
    public SteamGolemTargetGoal(SteamGolemEntity mob, Class<T> targetClass, boolean checkVisibility) {
        super(mob, targetClass, checkVisibility);
    }

    @Override
    public boolean canStart() {
        if (targetEntity instanceof SteamGolemEntity) {
            return false;
        }
        return super.canStart();
    }

    @Override
    public boolean shouldContinue() {
        if (targetEntity instanceof SteamGolemEntity) {
            return false;
        }
        return super.shouldContinue();
    }
}
