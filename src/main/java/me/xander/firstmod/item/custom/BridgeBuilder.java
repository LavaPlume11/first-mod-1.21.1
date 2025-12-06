package me.xander.firstmod.item.custom;

import me.xander.firstmod.block.ModBlocks;
import me.xander.firstmod.block.custom.BridgeBlock;
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

public class BridgeBuilder extends Item {
    public BridgeBuilder(Settings settings) {
        super(settings);
    }
    BlockState block;
    boolean isExtending;
    int extendTicks = 0;
    Direction direction;
    BlockPos pos;
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        direction = context.getHorizontalPlayerFacing();
        pos = context.getBlockPos();
        isExtending = true;
        context.getPlayer().getItemCooldownManager().set(this, 100);
        extend(context.getPlayer(), 1);
        return super.useOnBlock(context);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
      /*  if (entity instanceof PlayerEntity player && isExtending) {
            if (extendTicks % 10 == 0)
                extend(player, 1);
            extendTicks++;
            if (extendTicks >= 100) {
                extendTicks = 0;
                isExtending = false;
            }
        }

       */
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    public void extend(PlayerEntity player, int amount) {
            BlockPos newPos;
            switch (direction) {
                case NORTH -> newPos = pos.north();
                case SOUTH -> newPos = pos.south();
                case EAST -> newPos = pos.east();
                case WEST -> newPos = pos.west();
                default -> newPos = pos.down();
            }
            block = player.getWorld().getBlockState(newPos);
            if (!block.isOpaque() || block == Blocks.AIR.getDefaultState()) {
                player.getWorld().setBlockState(newPos, ModBlocks.BRIDGE_BLOCK.getDefaultState());
                pos = newPos;
            }
        }

}
