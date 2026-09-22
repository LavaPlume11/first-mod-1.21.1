package me.xander.firstmod.util.mixin;

import net.minecraft.entity.Entity;

import java.util.List;

public interface PlayerEntityAccess {
    List<String> first_mod_template_1_21_1$getCorruptedKills();
    void first_mod_template_1_21_1$setCorruptedKills(List<String> list);
    int first_mod_template_1_21_1$getVibrationCooldown();
    void first_mod_template_1_21_1$setVibrationCooldown(int vibrationCooldown);
    boolean first_mod_template_1_21_1$canSense(Entity entity);
    void first_mod_template_1_21_1$setLastHeardEntity(Entity entity);
    Entity first_mod_template_1_21_1$getLastHeardEntity();
    int first_mod_template_1_21_1$getSenseCooldown();
    void first_mod_template_1_21_1$setSenseCooldown(int senseCooldown);
}
