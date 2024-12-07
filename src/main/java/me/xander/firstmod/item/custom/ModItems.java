package me.xander.firstmod.item.custom;

import me.xander.first_mod;
import me.xander.firstmod.block.ModBlocks;
import me.xander.firstmod.entity.ModEntities;
import me.xander.firstmod.item.ModArmorMaterials;
import me.xander.firstmod.item.ModToolMaterial;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {
    public static final Item BANANA = registerItem("banana",  new AliasedBlockItem(ModBlocks.BANANA_BUSH, new Item.Settings().food(new FoodComponent.Builder()
            .nutrition(9)
            .saturationModifier(1.3F)
            .statusEffect(new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, 10, 9), 0.5F)
            .statusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 10000,9),0.5f).alwaysEdible().build())));

    public static final Item BURNT_BANANA = registerItem("burnt_banana",  new Item(new Item.Settings().food(new FoodComponent.Builder()
            .nutrition(2)
            .saturationModifier(0.3F)
            .statusEffect(new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, 10, 9), 1.0F)
            .alwaysEdible().build())
    ));
    public static final Item DYNAMITE = registerItem("dynamite",  new Item(new Item.Settings()));
    public static final Item RAW_MITHRIL = registerItem("raw_mithril", new Item(new Item.Settings()));
    public static final Item MITHRIL = registerItem("mithril", new Item(new Item.Settings()));
    public static final Spear SPEAR = (Spear) registerItem("spear",  new Spear(new  Item.Settings().maxCount(1)));

    public static final RegistryKey<ItemGroup> CUSTOM_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(first_mod.MOD_ID, "item_group"));

    public static final Item DAMAGED_MITHRIL_SWORD = registerItem("damaged_mithril_sword", new DamagedSword(ModToolMaterial.DAMAGED_SWORD, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers
            (ModToolMaterial.DAMAGED_SWORD,2,-3.4f)).rarity(Rarity.UNCOMMON)));
    public static final Item MITHRIL_SWORD = registerItem("mithril_sword",
            new MithrilSword(ModToolMaterial.SWORD, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers
                    (ModToolMaterial.SWORD,9,-3.4f)).rarity(Rarity.RARE), StatusEffects.LEVITATION));

    public static final Item TRUE_BLADE = registerItem("true_blade",
            new SwordItem(ModToolMaterial.SWORD, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers
                    (ModToolMaterial.SWORD,20,-2.1f)).rarity(Rarity.EPIC).fireproof()));

    public static final Item BIG_SWORD_TEST = registerItem("big_sword_test",
            new BloodSword(ToolMaterials.NETHERITE, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers
                    (ToolMaterials.DIAMOND,5,-3.5f)).rarity(Rarity.RARE).fireproof()));
    public static final Item BIG_SWORD = registerItem("big_sword",
            new BloodSword(ToolMaterials.NETHERITE, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers
                    (ToolMaterials.DIAMOND,9,-3.1f)).rarity(Rarity.RARE).fireproof()));
    public static final Item LANCE = registerItem("lance",
            new SwordItem(ModToolMaterial.SWORD, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers
                    (ModToolMaterial.SWORD,2,-2.1f))));
    public static final Item LION_SPAWN_EGG = registerItem("lion_spawn_egg",
            new SpawnEggItem(ModEntities.LION,0x365837,0x4866354, new Item.Settings()));
    public static final Item WHISPERER_SPAWN_EGG = registerItem("whisperer_spawn_egg",
            new SpawnEggItem(ModEntities.WHISPERER,0x295338,0x5828ae31, new Item.Settings()));
    public static final Item LEMMING_SPAWN_EGG = registerItem("lemming_spawn_egg",
            new SpawnEggItem(ModEntities.LEMMING,0x94756362,0x58395863, new Item.Settings()));
    public static final Item MITHRIL_SWORD_SHARD = registerItem("mithril_sword_shard", new SwordShard(new Item.Settings().maxCount(16)));
    public static final Item THANK_HAT = registerItem("thank_hat", new HolidayHat(new Item.Settings().maxCount(1)));
    public static final Item MITHRIL_HELMET = registerItem("mithril_helmet", new ArmorItem(ModArmorMaterials.MITHRIL_ARMOR,
            ArmorItem.Type.HELMET, new Item.Settings().maxDamage(165)));
    public static final Item MITHRIL_CHESTPLATE = registerItem("mithril_chestplate",  new  ArmorItem(ModArmorMaterials.MITHRIL_ARMOR,
            ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(240)));
    public static final Item MITHRIL_LEGGINGS = registerItem("mithril_leggings",  new ModArmorItem(ModArmorMaterials.MITHRIL_ARMOR,
            ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(225)));
    public static final Item MITHRIL_BOOTS = registerItem("mithril_boots",  new  ArmorItem(ModArmorMaterials.MITHRIL_ARMOR,
            ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(195)));
    public static final Item MITHRIL_TEMPLATE = registerItem("mithril_template", new Item(new Item.Settings()));
    public static final Item GOD_STICK = registerItem("god_stick",
            new godStick(ModToolMaterial.SWORD, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers
                    (ModToolMaterial.SWORD,1,-0.4f)), StatusEffects.LEVITATION));
    public static final Item MITHRIL_HORSE_ARMOR = registerItem("mithril_horse_armor",
            new AnimalArmorItem(ModArmorMaterials.MITHRIL_ARMOR, AnimalArmorItem.Type.EQUESTRIAN,false,new Item.Settings().maxDamage(800)));
    public static final Item CATALYST = registerItem("catalyst", new Item(new Item.Settings()));
    public static final Item GUITAR = registerItem("guitar", new GuitarItem(new Item.Settings()));
    public static final Item TOMAHAWK = registerItem("tomahawk", new TomahawkItem(new Item.Settings().maxCount(16)));

    private static Item registerItem(String name, Item item) {
        Registry.register(Registries.ITEM, Identifier.of(first_mod.MOD_ID, name),
                new Item(new Item.Settings()));

        return Registry.register(Registries.ITEM, Identifier.of(first_mod.MOD_ID, name), item);
    }
    public static void registerModItems() {
        first_mod.LOGGER.info("Registering ModItems for " + first_mod.MOD_ID);
    }}