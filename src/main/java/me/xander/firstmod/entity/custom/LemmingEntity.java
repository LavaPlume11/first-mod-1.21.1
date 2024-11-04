package me.xander.firstmod.entity.custom;

import me.xander.firstmod.entity.ModEntities;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class LemmingEntity extends AnimalEntity {
    public LemmingEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
    public final AnimationState IdleAnimationState = new AnimationState();
    private int idleAnimationTimeOut = 0;

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(5, new EscapeDangerGoal(this,1.5f));
        this.goalSelector.add(5, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(5, new FollowParentGoal(this, 1.25));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.4));
        this.goalSelector.add(5, new TemptGoal(this,1.25,stack-> stack.isOf(Items.CARROT),false));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(10, new LookAtEntityGoal(this, MobEntity.class, 8.0f));
        this.goalSelector.add(10, new RevengeGoal(this));


    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeOut <= 0) {
            this.idleAnimationTimeOut = 268;
            this.IdleAnimationState.start(this.age);
        }else {
            --this.idleAnimationTimeOut;
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getWorld().isClient()) {
            this.setupAnimationStates();
        }
    }

    public static DefaultAttributeContainer.Builder createLemmingAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH,6)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED,0.55)
                .add(EntityAttributes.GENERIC_JUMP_STRENGTH,5.5)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE,1);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.CARROT);
    }

    @Override
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.LEMMING.create(world);
    }
}
