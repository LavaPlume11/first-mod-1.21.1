package me.xander.firstmod.item.custom;

import me.xander.firstmod.sound.ModSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class GuitarItem extends Item {

    public GuitarItem(Settings settings) {
        super(settings);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient) {
            user.playSound(ModSounds.GUITAR_RIFF, 1.0f, 1.0f);
        }



        return TypedActionResult.success(user.getStackInHand(hand));
    }

    }
