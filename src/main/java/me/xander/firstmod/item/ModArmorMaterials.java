package me.xander.firstmod.item;


import me.xander.first_mod;
import me.xander.firstmod.item.custom.ModItems;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ModArmorMaterials {
    public static final RegistryEntry<ArmorMaterial> MITHRIL_ARMOR = registerArmorMaterial("mithril",
        () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
            map.put(ArmorItem.Type.BOOTS, 7);
            map.put(ArmorItem.Type.LEGGINGS, 11);
            map.put(ArmorItem.Type.CHESTPLATE, 13);
            map.put(ArmorItem.Type.HELMET, 9);
            map.put(ArmorItem.Type.BODY, 10);
        }), 20, SoundEvents.AMBIENT_CAVE, () -> Ingredient.ofItems(ModItems.MITHRIL),
                List.of(new ArmorMaterial.Layer(Identifier.of(first_mod.MOD_ID, "mithril"))), 0, 0));
    public static final RegistryEntry<ArmorMaterial> HOLIDAY = registerArmorMaterial("holiday",
            () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 2);
                map.put(ArmorItem.Type.LEGGINGS, 4);
                map.put(ArmorItem.Type.CHESTPLATE, 6);
                map.put(ArmorItem.Type.HELMET, 4);
                map.put(ArmorItem.Type.BODY, 5);
            }), 20, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, () -> Ingredient.ofItems(Items.LEATHER),
                    List.of(new ArmorMaterial.Layer(Identifier.of(first_mod.MOD_ID, "holiday"))), 0, 0));
    public static final RegistryEntry<ArmorMaterial> LIGHTNING = registerArmorMaterial("lightning",
            () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 2);
                map.put(ArmorItem.Type.LEGGINGS, 4);
                map.put(ArmorItem.Type.CHESTPLATE, 8);
                map.put(ArmorItem.Type.HELMET, 7);
                map.put(ArmorItem.Type.BODY, 6);
            }), 20, SoundEvents.ITEM_ARMOR_EQUIP_IRON, () -> Ingredient.ofItems(Items.COPPER_INGOT),
                    List.of(new ArmorMaterial.Layer(Identifier.of(first_mod.MOD_ID, "lightning"))), 0, 0));


    public static RegistryEntry<ArmorMaterial> registerArmorMaterial(String name, Supplier<ArmorMaterial> material) {
        return Registry.registerReference(Registries.ARMOR_MATERIAL, Identifier.of(first_mod.MOD_ID, name), material.get());
    }
}





