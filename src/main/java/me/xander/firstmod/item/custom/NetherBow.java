package me.xander.firstmod.item.custom;

import me.xander.firstmod.sound.ModSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;

public class NetherBow extends BowItem {
    public static final Predicate<ItemStack>  NETHER_PROJECTILES = BOW_PROJECTILES.or((stack) -> stack.isOf(Items.FIRE_CHARGE));

    public NetherBow(Settings settings) {
        super(settings);
    }
    @Override
    public Predicate<ItemStack> getProjectiles() {
        return NETHER_PROJECTILES;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity playerEntity) {
            ItemStack itemStack = playerEntity.getProjectileType(stack);
            if (!itemStack.isEmpty()) {
                int i = this.getMaxUseTime(stack, user) - remainingUseTicks;
                float f = getPullProgress(i);
                if (!((double)f < 0.1)) {
                    List<ItemStack> list = load(stack, itemStack, playerEntity);
                    if (world instanceof ServerWorld) {
                        ServerWorld serverWorld = (ServerWorld)world;
                        if (!list.isEmpty()) {
                            this.shootAll(serverWorld, playerEntity, playerEntity.getActiveHand(), stack, list, f * 7.0F, 1.0F, f == 1.0F, (LivingEntity)null);
                        }
                    }

                    world.playSound((PlayerEntity)null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), ModSounds.GUITAR_RIFF, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
                }
            }
        }
    }
}
