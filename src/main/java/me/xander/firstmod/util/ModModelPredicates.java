package me.xander.firstmod.util;


import me.xander.first_mod;
import me.xander.firstmod.components.ModDataComponentTypes;
import me.xander.firstmod.item.custom.ModItems;
import me.xander.firstmod.item.custom.StaffItem;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

public class ModModelPredicates {
    public static void registerModelPredicates() {
        ModelPredicateProviderRegistry.register(ModItems.MAGIC_STAFF, Identifier.of(first_mod.MOD_ID, "staff_state"),
                (stack, world, entity, seed) -> {
                        if (stack.get(ModDataComponentTypes.STAFF_STATE) == null) {
                            return 0.0f;
                        } else if (stack.get(ModDataComponentTypes.STAFF_STATE) == 1f) {

                            return 0.1f;
                        } else if (stack.get(ModDataComponentTypes.STAFF_STATE) == 2f) {

                            return 0.2f;
                        } else {
                            return 0.3f;
                        }
                }
        );


        registerCustomBow(ModItems.NETHER_BOW);
    }

    private static void registerCustomBow(Item item) {
        ModelPredicateProviderRegistry.register(item, Identifier.ofVanilla("pull"), (stack, world, entity, seed) -> {
            if (entity == null) {
                return 0.0f;
            }
            if (entity.getActiveItem() != stack) {
                return 0.0f;
            }
            return (float)(stack.getMaxUseTime(entity) - entity.getItemUseTimeLeft()) / 20.0f;
        });
        ModelPredicateProviderRegistry.register(item, Identifier.ofVanilla("pulling"), (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0f : 0.0f);
    }
}
