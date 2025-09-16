package me.xander.firstmod.item.custom;

import me.xander.firstmod.inventory.PocketStorageInventory;
import me.xander.firstmod.inventory.PocketStorageAccess;
import me.xander.firstmod.util.ModKeyBindings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.Set;

public class PocketStorageItem extends Item {

    public PocketStorageItem(Settings settings) {
        super(settings);

    }


    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient()) {
            if (entity instanceof PlayerEntity player) {
                if (ModKeyBindings.G_KEY_BINDING.isPressed()) {
                    openStorage(player, world);
                }
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient)
            openStorage(user, world);
        return super.use(world, user, hand);
    }
    public void openStorage(PlayerEntity player, World world) {
            PocketStorageInventory pocketStorageInventory = ((PocketStorageAccess)player).first_mod_template_1_21_1$getPocketStorageInventory();
            if (pocketStorageInventory.containsAny(Set.of(ModItems.POCKET_STORAGE))) {
                pocketStorageInventory.clear();
            }
            player.openHandledScreen(new SimpleNamedScreenHandlerFactory((i, playerInventory, playerEntity) ->
                    GenericContainerScreenHandler.createGeneric9x3(i, playerInventory, pocketStorageInventory), Text.of("Pocket Dimension")));

    }

}
