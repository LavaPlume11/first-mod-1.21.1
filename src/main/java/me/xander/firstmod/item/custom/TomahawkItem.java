package me.xander.firstmod.item.custom;

import me.xander.firstmod.entity.custom.TomahawkProjectileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class TomahawkItem extends AxeItem {


    public TomahawkItem(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW,
                SoundCategory.NEUTRAL,0.5f,0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
        if (!world.isClient) {
            TomahawkProjectileEntity diceProjectile = new TomahawkProjectileEntity(world, user);
            diceProjectile.setVelocity(user, user.getPitch(), user.getYaw(),0.0f,2.0f,0f);
            world.spawnEntity(diceProjectile);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
            itemStack.decrement(1);


        return TypedActionResult.success(itemStack,world.isClient());
    }
}
