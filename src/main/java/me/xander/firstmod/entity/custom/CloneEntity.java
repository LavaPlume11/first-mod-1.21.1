package me.xander.firstmod.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Arm;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CloneEntity extends LivingEntity {
    private PlayerEntity playerOwner = null;
    public CloneEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);

    }
    private int attackCoolDown = 40;
    private int moveTime = 40;
    private boolean shouldMove = false;
    private boolean shouldRecall = false;
    private Direction direction = Direction.NORTH;
    private Direction playerLeft = Direction.WEST;
    private Direction playerRight = Direction.EAST;
    private boolean isLeft;

    public static DefaultAttributeContainer.Builder createCloneAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.10000000149011612)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0)
                .add(EntityAttributes.GENERIC_STEP_HEIGHT, 1.0);
    }

    @Override
    public void tick() {
        super.tick();
        if (attackCoolDown > 0) {
            attackCoolDown--;
        }
        moveClone();
        if (this.age >= 600) {
            removeClone(this.getWorld());
        }
        if (playerOwner != null) {
            Vec3d movement = playerOwner.getMovement();
            this.move(MovementType.SELF, movement);
            if (!playerOwner.isAlive()) {
                removeClone(this.getWorld());
            }
        }
    }

    @Override
    public Arm getMainArm() {
        return Arm.RIGHT;
    }

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return null;
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {

    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (amount >= 8 && !this.getWorld().isClient() && source != this.getDamageSources().fall()) {
            ((ServerWorld) this.getWorld()).spawnParticles(ParticleTypes.SMALL_FLAME, this.getX(), this.getY() + 1, this.getZ(), 10, 0.1, 0.1, 0.1, 0.3);
            removeClone(this.getWorld());
        }
        return false;
    }


    public void setPlayerOwner(PlayerEntity player) {
        this.playerOwner = player;
    }
    public PlayerEntity getPlayerOwner() {
       return this.playerOwner;
    }


    @Override
    protected MoveEffect getMoveEffect() {
        return MoveEffect.NONE;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (playerOwner != null) {
            nbt.putUuid("player_owner", playerOwner.getUuid());
        }
        nbt.putInt("cooldown", attackCoolDown);
        nbt.putBoolean("should_move", shouldMove);
        nbt.putString("direction", direction.asString());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        try {
            playerOwner = this.getWorld().getPlayerByUuid(nbt.getUuid("player_owner"));
        } catch (NullPointerException ex) {
            playerOwner = null;
        }
        attackCoolDown = nbt.getInt("cooldown");
        shouldMove = nbt.getBoolean("should_move");
        direction = Direction.byName(nbt.getString("direction"));
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        if (playerOwner != null) {
            if (player != playerOwner && attackCoolDown <= 0) {
                player.damage(player.getDamageSources().playerAttack(playerOwner), 12);
                attackCoolDown = 40;
            }
        } else if (attackCoolDown <= 0){
            player.damage(player.getDamageSources().mobAttackNoAggro(this), 12);
            attackCoolDown = 40;
        }
    }
    private void moveClone() {
        if (shouldMove) {
            if (moveTime > 0) {
                Vec3d vec3d = new Vec3d(direction.getOffsetX(), direction.getOffsetY(), direction.getOffsetZ());
                if (moveTime % 3 == 0)
                    this.move(MovementType.SELF, vec3d);
                moveTime--;
            } else if (moveTime > -40){
                Vec3d vec3d = new Vec3d(direction.getOpposite().getOffsetX(), direction.getOpposite().getOffsetY(), direction.getOpposite().getOffsetZ());
                if (moveTime % 3 == 0)
                    this.move(MovementType.SELF, vec3d);
                moveTime--;
            } else {
                shouldMove = false;
                moveTime = 40;
            }
        } else if (shouldRecall) {
            BlockPos leftPos;
            BlockPos rightPos;
            switch (this.playerLeft) {
                case Direction.EAST -> {leftPos = playerOwner.getBlockPos().east();}
                case Direction.WEST -> {leftPos = playerOwner.getBlockPos().west();}
                case Direction.NORTH -> {leftPos = playerOwner.getBlockPos().north();}
                case Direction.SOUTH -> {leftPos = playerOwner.getBlockPos().south();}
                default -> {leftPos = playerOwner.getBlockPos();}
            }
            switch (this.playerRight) {
                case Direction.EAST -> {rightPos = playerOwner.getBlockPos().east();}
                case Direction.WEST -> {rightPos = playerOwner.getBlockPos().west();}
                case Direction.NORTH -> {rightPos = playerOwner.getBlockPos().north();}
                case Direction.SOUTH -> {rightPos = playerOwner.getBlockPos().south();}
                default -> {rightPos = playerOwner.getBlockPos();}
            }

            Vec3d vec3d = new Vec3d(rightPos.getX(), rightPos.getY(), rightPos.getZ());
            if (this.isLeft) {
                 vec3d = new Vec3d(leftPos.getX(), leftPos.getY(), leftPos.getZ());
            }
            if (moveTime % 3 == 0)
                    this.move(MovementType.SELF, vec3d);
                moveTime--;
                shouldMove = false;
                if (moveTime >= 0) {
                    moveTime = 40;
                }
                if ((this.isLeft && this.getBlockPos() == leftPos) || (!this.isLeft && this.getBlockPos() == rightPos)) {
                    moveTime = 40;
                    shouldRecall = false;
                }
        }
    }
    public void setCloneMoving(Direction direction) {
        shouldMove = true;
        this.direction = direction;
    }

    public void setCloneRecall(Direction left, Direction right, boolean isLeft) {
        shouldMove = false;
        shouldRecall = true;
        this.playerLeft = left;
        this.playerRight = right;
        this.isLeft = isLeft;
        moveTime = 40;
    }
    public void removeClone(World world) {
        if (!world.isClient() && playerOwner != null) {
            ((ServerWorld) world).spawnParticles(ParticleTypes.FALLING_OBSIDIAN_TEAR, this.getX(), this.getY() + 1, this.getZ(), 30, 0.1, 0.6, 0.1, 0.1);
        }
        this.discard();
    }
}
