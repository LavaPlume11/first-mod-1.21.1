package me.xander.firstmod.item.custom;

import me.xander.first_mod;
import me.xander.firstmod.block.ModBlocks;
import me.xander.firstmod.block.custom.BridgeBlock;
import me.xander.firstmod.components.ModDataComponentTypes;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BridgeBuilder extends Item {
    public BridgeBuilder(Settings settings) {
        super(settings);
    }
    List<BlockPos> posList = new ArrayList<>();
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        Direction direction = context.getHorizontalPlayerFacing();
        BlockPos pos = context.getBlockPos();
        BlockPos newPos;
        do {
            switch (direction) {
                case NORTH -> newPos = pos.north();
                case SOUTH -> newPos = pos.south();
                case EAST -> newPos = pos.east();
                case WEST -> newPos = pos.west();
                default -> newPos = pos.down();
            }
            pos = newPos;
            posList.add(newPos);
        } while (posList.size() < 30);
        context.getPlayer().getItemCooldownManager().set(this, 200);
        context.getStack().set(ModDataComponentTypes.USED, true);
        return super.useOnBlock(context);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        int cooldown = stack.getOrDefault(ModDataComponentTypes.DEFAULT_INT, 30);
        boolean isExtending = stack.getOrDefault(ModDataComponentTypes.USED, false);
        if (isExtending) {
            if (cooldown <= 0) {
                stack.set(ModDataComponentTypes.DEFAULT_INT, 30);
                extend(world, stack);
            }
            stack.set(ModDataComponentTypes.DEFAULT_INT, cooldown - 1);
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    public void extend(World world, ItemStack stack) {
        if (posList.isEmpty()) {
            stack.set(ModDataComponentTypes.USED, false);
            return;
        }
            BlockPos pos = posList.getFirst();
            BlockState state = world.getBlockState(pos);
            if (state.isReplaceable()) {
                world.setBlockState(pos, ModBlocks.BRIDGE_BLOCK.getDefaultState());
            }
            posList.removeFirst();
        }

}
