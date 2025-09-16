package me.xander.firstmod.item.custom;

import me.xander.first_mod;
import me.xander.firstmod.block.ModBlocks;
import me.xander.firstmod.entity.ModEntities;
import me.xander.firstmod.item.ModArmorMaterials;
import me.xander.firstmod.item.ModToolMaterials;
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
            .alwaysEdible().build())));
    public static final Item DYNAMITE = registerItem("dynamite",  new Item(new Item.Settings()));
    public static final Item POCKET_STORAGE = registerItem("pocket_storage", new PocketStorageItem(new Item.Settings().maxCount(1)));


    public static final Item WARDEN_PENDENT = registerItem("warden_pendent",  new WardenPendent(new Item.Settings().maxDamage(100),
            120, 30.0, 20.0F, 2.0, 0.05, 20));

    public static final Item DIAMOND_WARDEN_PENDENT = registerItem("diamond_warden_pendent",  new WardenPendent(new Item.Settings().maxDamage(500),
            60, 50.0, 40.0F, 6.0, 1.15, 1));

    public static final Item LOCATOR = registerItem("locator", new LocatorItem(new Item.Settings()));
    public static final Item DICE = registerItem("dice",  new DiceItem(new Item.Settings()));
    public static final Item XMAS_STICK = registerItem("xmas_stick",  new XmasStick(new Item.Settings().maxCount(1).maxDamage(1).fireproof()));
    public static final Item RAW_MITHRIL = registerItem("raw_mithril", new Item(new Item.Settings()));
    public static final Item MITHRIL = registerItem("mithril", new Item(new Item.Settings()));
    public static final Item MELTED_METAL = registerItem("melted_metal", new Item(new Item.Settings().maxCount(1)));
    public static final Item GEMS = registerItem("gems", new Item(new Item.Settings().maxCount(16)));
    public static final Item GEM_CLUSTER = registerItem("gem_cluster", new Item(new Item.Settings()));
    public static final Item POWER_GEM = registerItem("power_gem", new Item(new Item.Settings().maxCount(1)));
    public static final Item POWER_CELL = registerItem("power_cell", new PowerCell(new Item.Settings().maxCount(1).maxDamage(250)));
    public static final Item AMETHYST_GEMS = registerItem("amethyst_gems", new Item(new Item.Settings().maxCount(16)));
    public static final Spear SPEAR = (Spear) registerItem("spear",  new Spear(new  Item.Settings().maxCount(1)));

    public static final RegistryKey<ItemGroup> CUSTOM_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(first_mod.MOD_ID, "item_group"));

    public static final Item DAMAGED_MITHRIL_SWORD = registerItem("damaged_mithril_sword", new DamagedSword(ModToolMaterials.DAMAGED_SWORD, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers
            (ModToolMaterials.DAMAGED_SWORD,2,-3.4f)).rarity(Rarity.UNCOMMON)));
    public static final Item DASH_SWORD = registerItem("dash_sword", new DashSword(ToolMaterials.STONE, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers
            (ModToolMaterials.DAMAGED_SWORD,5,-2.4f)).rarity(Rarity.UNCOMMON)));
    public static final Item MITHRIL_SWORD = registerItem("mithril_sword",
            new MithrilSword(ModToolMaterials.SWORD, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers
                    (ModToolMaterials.SWORD,9,-3.0f)).rarity(Rarity.RARE), StatusEffects.LEVITATION));
    public static final Item NETHER_BOW = registerItem("nether_bow",
            new NetherBow(new Item.Settings().maxDamage(200)));
    public static final Item REFINED_MITHRIL_SWORD = registerItem("refined_mithril_sword",
            new SwordItem(ModToolMaterials.SWORD, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers
                    (ModToolMaterials.SWORD,15,-2.5f)).rarity(Rarity.RARE).fireproof()));

    public static final Item TRUE_BLADE = registerItem("true_blade",
            new TrueBlade(ModToolMaterials.SUPER_SWORD, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers
                    (ModToolMaterials.SUPER_SWORD, 0,-2.1f)).rarity(Rarity.EPIC).fireproof()));

    public static final Item LAVA_SABER = registerItem("lava_saber",
            new LavaSword(ModToolMaterials.LAVA, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers
                    (ModToolMaterials.LAVA, 5 ,-2.1f)).rarity(Rarity.RARE).fireproof()));
    public static final Item RE_DEAD_SWORD = registerItem("re_dead_sword",
            new ReDeadSword(ToolMaterials.STONE, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers
                    (ToolMaterials.IRON,1,-2.1f)).rarity(Rarity.RARE)));
    public static final Item BIG_SWORD_TEST = registerItem("big_sword_test",
            new BloodSword(ToolMaterials.NETHERITE, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers
                    (ToolMaterials.DIAMOND,5,-3.5f)).rarity(Rarity.RARE).fireproof()));
    public static final Item LIGHT_KNIFE = registerItem("light_knife",
            new LightKnife(ModToolMaterials.LIGHT,new Item.Settings()));
    public static final Item BIG_SWORD = registerItem("big_sword",
            new BloodSword(ToolMaterials.NETHERITE, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers
                    (ToolMaterials.DIAMOND,6,-3.1f)).rarity(Rarity.RARE).fireproof()));
    public static final Item LION_SPAWN_EGG = registerItem("lion_spawn_egg",
            new SpawnEggItem(ModEntities.LION,0x365837,0x4866354, new Item.Settings()));
    public static final Item WHISPERER_SPAWN_EGG = registerItem("whisperer_spawn_egg",
            new SpawnEggItem(ModEntities.WHISPERER,0x295338,0x5828ae31, new Item.Settings()));
    public static final Item WARTURTLE_SPAWN_EGG = registerItem("warturtle_spawn_egg",
            new SpawnEggItem(ModEntities.WARTURTLE,0x7436c21,0x58a3167, new Item.Settings()));
    public static final Item LEMMING_SPAWN_EGG = registerItem("lemming_spawn_egg",
            new SpawnEggItem(ModEntities.LEMMING,0x94756362,0x58395863, new Item.Settings()));
    public static final Item MITHRIL_SWORD_SHARD = registerItem("mithril_sword_shard", new SwordShard(new Item.Settings().maxCount(16).fireproof()));
    public static final Item THANK_HAT = registerItem("thank_hat", new ThankHat(new Item.Settings().maxCount(1)));
    public static final Item XMAS_HAT = registerItem("xmas_hat", new XmasHat(new Item.Settings().maxCount(1)));
    public static final Item MITHRIL_HELMET = registerItem("mithril_helmet", new ArmorItem(ModArmorMaterials.MITHRIL_ARMOR,
            ArmorItem.Type.HELMET, new Item.Settings().maxDamage(165)));
    public static final Item MITHRIL_CHESTPLATE = registerItem("mithril_chestplate",  new  ArmorItem(ModArmorMaterials.MITHRIL_ARMOR,
            ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(240)));
    public static final Item MITHRIL_LEGGINGS = registerItem("mithril_leggings",  new ModArmorItem(ModArmorMaterials.MITHRIL_ARMOR,
            ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(225)));
    public static final Item MITHRIL_BOOTS = registerItem("mithril_boots",  new  ArmorItem(ModArmorMaterials.MITHRIL_ARMOR,
            ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(195)));
    public static final Item MITHRIL_TEMPLATE = registerItem("mithril_template", new Item(new Item.Settings()));
    public static final Item LIGHTNING_HELMET = registerItem("lightning_helmet", new LightningHat(ModArmorMaterials.LIGHTNING,
            ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(17))));
    public static final Item GOD_STICK = registerItem("god_stick",
            new godStick(ModToolMaterials.SWORD, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers
                    (ModToolMaterials.SWORD,1,-0.4f)), StatusEffects.LEVITATION));
    public static final Item WHIP = registerItem("whip",  new WhipItem(new Item.Settings().maxCount(1)));
    public static final Item MITHRIL_HORSE_ARMOR = registerItem("mithril_horse_armor",
            new AnimalArmorItem(ModArmorMaterials.MITHRIL_ARMOR, AnimalArmorItem.Type.EQUESTRIAN,false,new Item.Settings().maxDamage(800)));
    public static final Item CATALYST = registerItem("catalyst", new Item(new Item.Settings().food(new FoodComponent.Builder()
            .nutrition(9)
            .saturationModifier(1.3F)
            .statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 1000,9),0.6f)
            .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 2000, 9), 0.355F)
            .statusEffect(new StatusEffectInstance(StatusEffects.WITHER, 30000, 2), 0.155F).alwaysEdible().build())));
    public static final Item GUITAR = registerItem("guitar", new GuitarItem(new Item.Settings()));
    public static final Item MAGIC_STAFF = registerItem("magic_staff", new StaffItem(new Item.Settings().maxCount(1)));
    public static final Item TOMAHAWK = registerItem("tomahawk", new TomahawkItem(ToolMaterials.IRON, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers
            (ToolMaterials.IRON,4,-3.5f))));
    public static final Item IRON_WARTURTLE_ARMOR = registerItem("iron_warturtle_armor",
            new WarturtleArmorItem(ArmorMaterials.IRON, new Item.Settings().maxDamage(400)));
    public static final Item GOLD_WARTURTLE_ARMOR = registerItem("gold_warturtle_armor",
            new WarturtleArmorItem(ArmorMaterials.GOLD, new Item.Settings().maxDamage(200)));
    public static final Item DIAMOND_WARTURTLE_ARMOR = registerItem("diamond_warturtle_armor",
            new WarturtleArmorItem(ArmorMaterials.DIAMOND, new Item.Settings().maxDamage(600)));
    public static final Item NETHERITE_WARTURTLE_ARMOR = registerItem("netherite_warturtle_armor",
            new WarturtleArmorItem(ArmorMaterials.NETHERITE, new Item.Settings().maxDamage(800)));
    public static final Item MITHRIL_WARTURTLE_ARMOR = registerItem("mithril_warturtle_armor",
            new WarturtleArmorItem(ModArmorMaterials.MITHRIL_ARMOR, new Item.Settings().maxDamage(1000)));
    public static final Item ICARUS_WINGS = registerItem("icarus_wings", new IcarusWings(new Item.Settings().maxDamage(216)));
    public static final Item STICKY_FEATHER = registerItem("sticky_feather", new Item(new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        Registry.register(Registries.ITEM, Identifier.of(first_mod.MOD_ID, name),
                new Item(new Item.Settings()));
        return Registry.register(Registries.ITEM, Identifier.of(first_mod.MOD_ID, name), item);
    }
    public static void registerModItems() {
        first_mod.LOGGER.info("Registering ModItems for " + first_mod.MOD_ID);
    }}