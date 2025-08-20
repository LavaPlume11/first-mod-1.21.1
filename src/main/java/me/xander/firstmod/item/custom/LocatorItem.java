package me.xander.firstmod.item.custom;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LocatorItem extends Item {
    public int range = 0;
    public LocatorItem(Settings settings) {
        super(settings);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient()) {
            if (user.isSneaking()) {
                range--;
            } else {
                range++;
            }
            if (range <= 1) {
                user.sendMessage(Text.of("Tuning to 1 block"), true);
                range = 1;
            } else {
                user.sendMessage(Text.of("Tuning to " + range + " blocks"), true);
            }
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (!context.getWorld().isClient()) {
            PlayerEntity user = context.getPlayer();

            if (user != null) {
                BlockPos pos = context.getBlockPos();
                    BlockPos newPos = doProbe(range, pos);
                    Block block = context.getWorld().getBlockState(newPos).getBlock();
                    user.sendMessage(Text.of(block.asItem().getName().getString()), false);

                }
            }

        return ActionResult.SUCCESS;
    }

    private void doSearch(ItemStack stack, PlayerEntity player) {

    }

    private BlockPos doProbe(int range, BlockPos pos) {
        BlockPos newPos = pos.down();
        int newRange = range - 1;
        if (range <= 0) {
            return newPos;
        }
       return this.doProbe(newRange, newPos);
    }
}
