package me.xander.firstmod.item.custom;

import me.xander.first_mod;
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
    public static final Item BANANA = registerItem("banana",  new Item(new Item.Settings().food(new FoodComponent.Builder()
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
    public static final Item LIGHT_KNIFE = registerItem("light_knife", new LightKnife(ModToolMaterial.DAMAGED_SWORD, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers
            (ModToolMaterial.DAMAGED_SWORD,5,-1.9f)).rarity(Rarity.RARE)));
    public static final Item DYNAMITE = registerItem("dynamite",  new Item(new Item.Settings()));
    public static final Item TIME_STICK = registerItem("time_stick",  new Item(new Item.Settings().maxCount(1)));
    public static final Item MITHRIL_STAFF = registerItem("mithril_staff",  new MithrilStaff(new Item.Settings().maxCount(1)));
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
                    (ModToolMaterial.SWORD,20,-2.1f)).rarity(Rarity.EPIC)));

    public static final Item MITHRIL_PICKAXE = registerItem("mithril_pickaxe",
            new PickaxeItem(ModToolMaterial.MITHRIL, new Item.Settings()));
    public static final Item MITHRIL_SWORD_SHARD = registerItem("mithril_sword_shard", new SwordShard(new Item.Settings().maxCount(16)));
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
    public static final Item NETHER_BOW = registerItem("nether_bow",  new BowItem(new Item.Settings()));
    public static final Item MASTER_SWORD = registerItem("master_sword", new MasterSword(ToolMaterials.NETHERITE, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers
            (ToolMaterials.NETHERITE,2,-2.1f)).rarity(Rarity.RARE)));
    public static final Item CATALYST = registerItem("catalyst", new Item(new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        Registry.register(Registries.ITEM, Identifier.of(first_mod.MOD_ID, name),
                new Item(new Item.Settings()));

        return Registry.register(Registries.ITEM, Identifier.of(first_mod.MOD_ID, name), item);
    }
    public static void registerModItems() {
        first_mod.LOGGER.info("Registering ModItems for " + first_mod.MOD_ID);
    }}