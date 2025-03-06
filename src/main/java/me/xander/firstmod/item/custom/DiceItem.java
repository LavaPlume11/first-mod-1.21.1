package me.xander.firstmod.item.custom;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.TickDurationMonitor;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

public class DiceItem extends Item {
    public DiceItem(Settings settings) {
        super(settings);
    }
int number;
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            number = (int) (Math.random() * 8 - 1);
            BlockPos playerPos = user.getBlockPos();
            BlockPos randomPlayerPos = user.getBlockPos().add((int) (Math.random() * (10 - 1) - 1), (int) (Math.random() * (10 - 1) - 1), (int) (Math.random() * (10 - 1) - 1));
            int printNumber = number + 1;
            user.sendMessage(Text.of(String.valueOf(printNumber)));
            switch (number) {
                case 0:
                {EntityType.LIGHTNING_BOLT.spawn((ServerWorld) world, playerPos, SpawnReason.TRIGGERED);
                break;}

                case 1: {
                    EntityType.BEE.spawn((ServerWorld) world, randomPlayerPos, SpawnReason.TRIGGERED);
                    EntityType.BEE.spawn((ServerWorld) world, randomPlayerPos, SpawnReason.TRIGGERED);
                    EntityType.BEE.spawn((ServerWorld) world, randomPlayerPos, SpawnReason.TRIGGERED);
                    EntityType.BEE.spawn((ServerWorld) world, randomPlayerPos, SpawnReason.TRIGGERED);
                    EntityType.BEE.spawn((ServerWorld) world, randomPlayerPos, SpawnReason.TRIGGERED);
                    EntityType.BEE.spawn((ServerWorld) world, randomPlayerPos, SpawnReason.TRIGGERED);
                    EntityType.BEE.spawn((ServerWorld) world, randomPlayerPos, SpawnReason.TRIGGERED);
                    break;
                }

                case 2:
                {user.sendMessage(Text.of("You're safe... for now"));break;}

                case 3:
                {EntityType.IRON_GOLEM.spawn((ServerWorld) world, randomPlayerPos, SpawnReason.TRIGGERED);break;}

                case 4:
                {
                    EntityType.TNT.spawn((ServerWorld) world, playerPos, SpawnReason.TRIGGERED);user.setMainArm(Arm.RIGHT);break;}


                case 5:
                {user.sendMessage(Text.of("HEHEHEHEHEH"));
                    user.setMainArm(Arm.LEFT);
                    break;}

                case 6:
                { user.sendMessage(Text.literal("this is not physically possible."));
                    user.sendMessage(Text.literal("you can have 6 extra hearts if you don't tell anyone. Deal?"));
                    user.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION,1000,2));}
            }
        }
            return TypedActionResult.consume(user.getStackInHand(hand));

    }

}
