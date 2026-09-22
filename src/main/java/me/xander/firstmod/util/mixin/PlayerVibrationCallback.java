package me.xander.firstmod.util.mixin;

import me.xander.first_mod;
import me.xander.firstmod.item.custom.ModItems;
import me.xander.firstmod.networking.packet.SenseEntityPayload;
import me.xander.firstmod.sound.ModSounds;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.GameEventTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.EntityPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.PositionSource;
import net.minecraft.world.event.Vibrations;
import org.jetbrains.annotations.Nullable;

public class PlayerVibrationCallback implements Vibrations.Callback {
    private static final int RANGE = 16;
    private final PlayerEntity player;
    private final PositionSource positionSource;
    public PlayerVibrationCallback(PlayerEntity player) {
        this.player = player;
        this.positionSource  = new EntityPositionSource(player, player.getHeight() / 2);
    }

    public int getRange() {
        return RANGE;
    }

    public PositionSource getPositionSource() {
        return this.positionSource;
    }

    public TagKey<GameEvent> getTag() {
        return GameEventTags.WARDEN_CAN_LISTEN;
    }

    public boolean triggersAvoidCriterion() {
        return true;
    }

    public boolean accepts(ServerWorld world, BlockPos pos, RegistryEntry<GameEvent> event, GameEvent.Emitter emitter) {
        if (emitter.sourceEntity() == player || ((PlayerEntityAccess) player).first_mod_template_1_21_1$getVibrationCooldown() >= 0) {
            return false;
        }
        return !player.isDead() && world.getWorldBorder().contains(pos) && player.getEquippedStack(EquipmentSlot.CHEST).isOf(ModItems.ECHO_CHESTPLATE);
    }

    public void accept(ServerWorld world, BlockPos pos, RegistryEntry<GameEvent> event, @Nullable Entity sourceEntity, @Nullable Entity entity, float distance) {
      world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_WARDEN_TENDRIL_CLICKS, SoundCategory.PLAYERS, 2f, 1f);
      int i = (int) (Math.random() * 1000);
      if (i == 404) {
          player.sendMessage(Text.literal("You heard Herobrine").formatted(Formatting.RED).formatted(Formatting.BOLD), true);
      } else if (sourceEntity != null) {
           if (sourceEntity instanceof LivingEntity livingEntity) {
               //livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 40, 0, false, false, false));
               ((PlayerEntityAccess) player).first_mod_template_1_21_1$setLastHeardEntity(livingEntity);
               //first_mod.LOGGER.info(((PlayerEntityAccess) player).first_mod_template_1_21_1$getLastHeardEntity().toString());
               ServerPlayNetworking.send((ServerPlayerEntity) player, new SenseEntityPayload(40, livingEntity.getId()));
               if (livingEntity.hasCustomName() || livingEntity instanceof PlayerEntity) {
                   player.sendMessage(Text.literal("You heard " + sourceEntity.getName().getString()).formatted(Formatting.RED), true);
               } else {
                   player.sendMessage(Text.literal("You heard a " + sourceEntity.getName().getString()).formatted(Formatting.RED), true);
               }
               ((PlayerEntityAccess) player).first_mod_template_1_21_1$setSenseCooldown(40);
           }
       }
       ((PlayerEntityAccess) player).first_mod_template_1_21_1$setVibrationCooldown(30);
    }
}
