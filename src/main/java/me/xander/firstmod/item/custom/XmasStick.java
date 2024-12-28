package me.xander.firstmod.item.custom;

import me.xander.firstmod.entity.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class XmasStick extends Item {
    public XmasStick(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.of("I think someone dropped these..."));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            BlockPos playerPos = user.getBlockPos().north(5);
            EntityType.LIGHTNING_BOLT.spawn((ServerWorld) world, playerPos, SpawnReason.TRIGGERED);
            ModEntities.SLEIGH.spawn((ServerWorld) world, playerPos, SpawnReason.TRIGGERED);
            user.sendMessage(Text.of("""
                    Jingle bell, jingle bell, jingle bell rock
                    Jingle bells swing and jingle bells ring
                    Snowin' and blowin' up bushels of fun
                    Now the jingle hop has begun
                    Jingle bell, jingle bell, jingle bell rock
                    Jingle bells chime in jingle bell time
                    Dancin' and prancin' in Jingle Bell Square
                    In the frosty air
                    What a bright time, it's the right time
                    To rock the night away
                    Jingle bell time is a swell time
                    To go glidin' in a one-horse sleigh
                    Giddy-up jingle horse, pick up your feet
                    Jingle around the clock
                    Mix and a-mingle in the jingling feet
                    That's the jingle bell rock
                    Jingle bell, jingle bell, jingle bell rock
                    Jingle bell chime in jingle bell time
                    Dancin' and prancin' in Jingle Bell Square
                    In the frosty air
                    What a bright time, it's the right time
                    To rock the night away
                    Jingle bell time is a swell time
                    To go glidin' in a one-horse sleigh
                    Giddy-up jingle horse, pick up your feet
                    Jingle around the clock
                    Mix and a-mingle in the jinglin' feet
                    That's the jingle bell
                    That's the jingle bell
                    That's the jingle bell rock"""));

        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
