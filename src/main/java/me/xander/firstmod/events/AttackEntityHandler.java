package me.xander.firstmod.events;

import me.xander.firstmod.item.custom.ModItems;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class AttackEntityHandler implements AttackEntityCallback {
    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand,
                                 Entity entity, @Nullable EntityHitResult hitResult) {
        if (entity instanceof SheepEntity && !world.isClient){
            if (player.getMainHandStack().getItem() == Items.TNT) {
                player.sendMessage(Text.of("But Nothing Happened!"));
            }
        }
        if (player.getMainHandStack().getItem() == ModItems.BANANA) {
            player.sendMessage(Text.of("Something Happened!"));
            ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING,800));
            player.getMainHandStack().decrement(1);
        }

        return ActionResult.PASS;
    }
}
