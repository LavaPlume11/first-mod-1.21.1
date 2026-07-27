package me.xander.firstmod.item.custom;

import me.xander.firstmod.components.ModDataComponentTypes;
import me.xander.firstmod.entity.ModEntities;
import me.xander.firstmod.entity.custom.CloneEntity;
import me.xander.firstmod.util.mixin.PlayerEntityAccess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.List;

public class CloneCreator extends Item {
    public CloneCreator(Settings settings) {
        super(settings);
    }
    CloneEntity clone1;
    CloneEntity clone2;
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient()) {
            if (user.isSneaking()) {
                List<CloneEntity> clones = world.getEntitiesByType(ModEntities.CLONE, user.getBoundingBox().expand(100), EntityPredicates.EXCEPT_SPECTATOR);
                for (CloneEntity clone : clones) {
                    if (clone.getPlayerOwner() == user) {
                        clone.removeClone(clone.getWorld());
                    }
                }
                clone1 = null;
                clone2 = null;
                stack.set(ModDataComponentTypes.USED, false);
            } else  if(clone1 == null && clone2 == null){
                Direction left;
                Direction right;
                Direction playerFacing = user.getHorizontalFacing();
                switch (playerFacing) {
                    case Direction.NORTH -> {
                        left = Direction.WEST;
                        right = Direction.EAST;

                    }
                    case Direction.EAST -> {
                        left = Direction.NORTH;
                        right = Direction.SOUTH;
                    }
                    case Direction.WEST -> {
                        left = Direction.SOUTH;
                        right = Direction.NORTH;
                    }
                    default -> {
                        left = Direction.EAST;
                        right = Direction.WEST;
                    }
                }
                 clone1 = ModEntities.CLONE.spawn(((ServerWorld) world), user.getBlockPos().offset(left), SpawnReason.TRIGGERED);
                 clone2 = ModEntities.CLONE.spawn(((ServerWorld) world), user.getBlockPos().offset(right), SpawnReason.TRIGGERED);
                if(clone1 != null && clone2 != null) {
                    clone1.setPlayerOwner(user);
                    clone2.setPlayerOwner(user);
                }
                stack.set(ModDataComponentTypes.USED, true);
            } else if (clone1 != null && clone2 != null){
                clone1.setCloneMoving(user.getHorizontalFacing());
                clone2.setCloneMoving(user.getHorizontalFacing());
            }
        }
        return super.use(world, user, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (clone1 == null || clone2 == null) {
            stack.set(ModDataComponentTypes.USED, false);
        }
        if (clone1 != null) {
            if (!clone1.isAlive()) {
                clone1 = null;
            }

        }
        if (clone2 != null) {
            if (!clone2.isAlive()) {
                clone2 = null;
            }

        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
