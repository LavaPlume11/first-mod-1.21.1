package me.xander.firstmod.item.custom;

import me.xander.first_mod;
import me.xander.firstmod.components.ModDataComponentTypes;
import me.xander.firstmod.entity.ModEntities;
import me.xander.firstmod.entity.custom.CloneEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RemoteItem extends Item {
    public RemoteItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            List<ArmorStandEntity> armorStandEntities = world.getEntitiesByType(EntityType.ARMOR_STAND, user.getBoundingBox().expand(25), EntityPredicates.EXCEPT_SPECTATOR);
            List<PlayerEntity> playerEntitiesInRange = world.getEntitiesByType(EntityType.PLAYER, user.getBoundingBox().expand(25), EntityPredicates.EXCEPT_SPECTATOR);
            List<LivingEntity> entities = new ArrayList<>(armorStandEntities);
            entities.addAll(playerEntitiesInRange);
            List<Iterable<ItemStack>> items = new ArrayList<>();
            for (LivingEntity livingEntity : entities) {
                items.add(livingEntity.getArmorItems());
            }

            for (int i = 0; i < items.size(); i++) {
                int itemCount = 0;
                List<ItemStack> armors = new ArrayList<>();
                for (Iterator<ItemStack> var4 = items.get(i).iterator(); var4.hasNext();) {
                    ItemStack itemStack = var4.next();
                    if (itemStack.getItem() instanceof TrapArmorItem) {
                        itemCount++;
                        armors.add(itemStack);
                    }
                    if (itemCount >= 4) {
                        LivingEntity activeEntity = entities.get(i);

                        for (int j = 0; j < armors.size(); j++) {
                            ((TrapArmorItem) armors.get(j).getItem()).trigger(armors.get(j));
                        }
                        if (activeEntity instanceof ArmorStandEntity armorStand) {
                             armorStand.discard();
                             world.spawnEntity(EntityType.IRON_GOLEM.spawn((ServerWorld) world, activeEntity.getBlockPos(), SpawnReason.TRIGGERED));
                        }
                    }
                }
            }
        }
        return super.use(world, user, hand);
    }
}
