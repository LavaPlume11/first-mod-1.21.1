package me.xander.firstmod.util;


import me.xander.first_mod;
import me.xander.firstmod.item.custom.ModItems;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

public class ModModelPredicates {
    public static void registerModelPredicates() {
        ModelPredicateProviderRegistry.register(ModItems.MASTER_SWORD, Identifier.of(first_mod.MOD_ID,"on"),
                ((stack, world, entity, seed) -> Float.parseFloat(null)));
    }



}
