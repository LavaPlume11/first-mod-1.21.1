package me.xander.firstmod.item;

import me.xander.firstmod.item.custom.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

import java.util.function.Supplier;

public enum ModToolMaterials implements ToolMaterial {
     MITHRIL(4,1000,6.5f,3.5f,10,
          ()-> Ingredient.ofItems(ModItems.MITHRIL)),
    SWORD(5,2000,3.5f,9.5f,26,
            ()-> Ingredient.ofItems(ModItems.MITHRIL_SWORD_SHARD)),
    SUPER_SWORD(5,2500,3.5f,0.0f,26,
            ()-> Ingredient.ofItems(ModItems.GEM_CLUSTER)),
    DAMAGED_SWORD(2,25,1.5f,2.5f,1,
                 ()-> Ingredient.ofItems(ModItems.RAW_MITHRIL)),
    LIGHT(1,5,1.5f,1.5f,1,
                          ()-> Ingredient.ofItems((net.minecraft.item.ItemConvertible) null)),
    LAVA(5,2500,3.5f,9.5f,26,
                    ()-> Ingredient.ofItems(ModItems.POWER_CELL));



    private final int miningLevel;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairIngredient;


    ModToolMaterials(int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier<Ingredient> repairIngredient) {
        this.miningLevel = miningLevel;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
    }


    @Override
    public int getDurability() {
        return this.itemDurability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public TagKey<Block> getInverseTag() {
        return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
