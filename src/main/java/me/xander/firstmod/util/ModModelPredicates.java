package me.xander.firstmod.util;


import me.xander.first_mod;
import me.xander.firstmod.components.ModDataComponentTypes;
import me.xander.firstmod.item.ModArmorMaterials;
import me.xander.firstmod.item.custom.ModItems;
import net.minecraft.client.item.ClampedModelPredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class ModModelPredicates {
    public static void registerModelPredicates() {
        registerStaff(ModItems.MAGIC_STAFF);
        registerCustomBow(ModItems.NETHER_BOW);
        registerLavaSaber(ModItems.LAVA_SABER);
        registerElytra(ModItems.ICARUS_WINGS);
        registerUsable(ModItems.DARK_PORTAL_SETTER);
        registerElytra(ModItems.DRAGONSCALE_WINGS);
        registerTriggerable(ModArmorMaterials.MITHRIL_ARMOR.value());
    }
    private static void registerUsable(Item item) {
        ModelPredicateProviderRegistry.register(item, Identifier.of(first_mod.MOD_ID, "usable"), (stack, world, entity, seed) -> {
                if(stack.get(ModDataComponentTypes.USED) == null) {
                    return 0f;
                } else  if (stack.get(ModDataComponentTypes.USED) == false){
                    return 0f;
                } else {
                    return 0.1f;
                }
                });
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
    private static void registerStaff(Item item) {
        ModelPredicateProviderRegistry.register(item, Identifier.of(first_mod.MOD_ID, "staff_state"),
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
    }
    private static void registerLavaSaber(Item item) {
        ModelPredicateProviderRegistry.register(item, Identifier.of(first_mod.MOD_ID, "lava_state"),
                (stack, world, entity, seed) -> {
    if (stack.get(ModDataComponentTypes.LAVA_STATE) == null) {
            return 0.1f;
        } else if (stack.get(ModDataComponentTypes.LAVA_STATE) == 1f) {
            return 0.1f;
        } else if (stack.get(ModDataComponentTypes.LAVA_STATE) == 2f) {
            return 0.2f;
        } else if (stack.get(ModDataComponentTypes.LAVA_STATE) == 3f){
            return 0.3f;
        } else if (stack.get(ModDataComponentTypes.LAVA_STATE) == 4f){
        return 0.4f;
    } else {
        return 0.4f;
    }
    });

    }
    private static void registerElytra(Item item){
        ModelPredicateProviderRegistry.register(item, Identifier.of(first_mod.MOD_ID, "is_broken"),
                (stack, world, entity, seed) -> {
                if (stack.get(ModDataComponentTypes.BROKEN) == null) {
                    return 0f;
                } else if (stack.get(ModDataComponentTypes.BROKEN) == true) {
                    return 1f;
                } else {
                    return 0;
                }
                });
    }
    private static void registerTriggerable(ArmorMaterial material) {
        ModelPredicateProviderRegistry.register( Identifier.of(first_mod.MOD_ID, "triggered"), ((stack, world, entity, seed) -> {
            if (stack.get(ModDataComponentTypes.USED) == null) {
                return 0f;
            } else if (stack.get(ModDataComponentTypes.USED) == false) {
                return 0f;
            } else {
                return 0.1f;
            }
        }));
    }
}
