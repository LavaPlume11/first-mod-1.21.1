package me.xander.firstmod.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class Spear extends Item {

    public Spear(Settings settings) {
        super(settings);
    }



    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.of("Be Careful!"));
        tooltip.add(Text.of("Right click to use"));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
if (hand == Hand.MAIN_HAND) {

user.playSound(SoundEvents.EVENT_MOB_EFFECT_BAD_OMEN, 1.0f, 1.0f);
user.sendMessage(Text.of("Ya Hoo"));
}else{
    user.playSound(SoundEvents.BLOCK_AMETHYST_BLOCK_BREAK, 9.0f, 1.0f);
    user.setHealth(0);
    user.sendMessage(Text.of( "L BOZO you stabbed yourself"));

}



return TypedActionResult.success(user.getStackInHand(hand));


    }
    }


