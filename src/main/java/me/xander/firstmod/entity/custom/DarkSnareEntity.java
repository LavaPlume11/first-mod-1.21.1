package me.xander.firstmod.entity.custom;

import me.xander.first_mod;
import me.xander.firstmod.components.ModDataComponentTypes;
import me.xander.firstmod.item.custom.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class DarkSnareEntity extends Entity {
    public DarkSnareEntity(EntityType<?> type, World world) {
        super(type, world);
    }
    int playerStandTicks = 0;
    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient()) {
            ServerWorld world = (ServerWorld) this.getWorld();
            if(this.age % 5 == 0)
                world.spawnParticles(first_mod.DARK_PORTAL_PARTICLE,this.getX() - Math.random(), this.getY(), this.getZ() - Math.random(),1,0.01,0.01,0.01, 0.03);
        }

    }

    @Override
    public boolean canHit() {
        return true;
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if(!this.getWorld().isClient()) {
            if (itemStack.isOf(ModItems.DARK_PORTAL_SETTER) && player.isSneaking()) {
                player.damage(player.getDamageSources().magic(), 4);
                this.discard();
            }
        }
        return super.interact(player, hand);
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
            player.damage(player.getDamageSources().magic(), 10);
            player.playSound(SoundEvents.BLOCK_ANVIL_PLACE, 5.0f, 0.5f);
            this.discard();

    }
}
