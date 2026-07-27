package me.xander.firstmod.item.custom;

import me.xander.first_mod;
import me.xander.firstmod.components.ModDataComponentTypes;
import me.xander.firstmod.mixin.accessor.ArmorMaterialAccessor;
import me.xander.firstmod.mixin.accessor.ArmorMaterialLayerAccessor;
import me.xander.firstmod.util.ModKeyBindings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.world.World;

public class TrapArmorItem extends ArmorItem  {
    public int trapTimerMax = 200;
    private int trapTimer = trapTimerMax;
    private final RegistryEntry<ArmorMaterial> armorMat;
    public TrapArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);
        armorMat = material;
    }

    private boolean hasCorrectArmorOn(RegistryEntry<ArmorMaterial> material, PlayerEntity player) {
        for (ItemStack armorStack: player.getInventory().armor) {
            if(!(armorStack.getItem() instanceof ArmorItem)) {
                return false;
            }
        }

        ArmorItem boots = ((ArmorItem)player.getInventory().getArmorStack(0).getItem());
        ArmorItem leggings = ((ArmorItem)player.getInventory().getArmorStack(1).getItem());
        ArmorItem breastplate = ((ArmorItem)player.getInventory().getArmorStack(2).getItem());
        ArmorItem helmet = ((ArmorItem)player.getInventory().getArmorStack(3).getItem());

        return helmet.getMaterial() == material && breastplate.getMaterial() == material &&
                leggings.getMaterial() == material && boots.getMaterial() == material;
    }
    private boolean hasFullSuitOfArmorOn(PlayerEntity player) {
        ItemStack boots = player.getInventory().getArmorStack(0);
        ItemStack leggings = player.getInventory().getArmorStack(1);
        ItemStack breastplate = player.getInventory().getArmorStack(2);
        ItemStack helmet = player.getInventory().getArmorStack(3);

        return !helmet.isEmpty() && !breastplate.isEmpty()
                && !leggings.isEmpty() && !boots.isEmpty();
    }
    private boolean hasFullSuitOfArmorOn(LivingEntity entity) {
        ItemStack boots = entity.getEquippedStack(EquipmentSlot.FEET);
        ItemStack leggings = entity.getEquippedStack(EquipmentSlot.LEGS);
        ItemStack breastplate = entity.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack helmet = entity.getEquippedStack(EquipmentSlot.HEAD);

        return !helmet.isEmpty() && !breastplate.isEmpty()
                && !leggings.isEmpty() && !boots.isEmpty();
    }

    private boolean hasCorrectArmorOn(RegistryEntry<ArmorMaterial> material, LivingEntity entity) {
        for (ItemStack armorStack: entity.getArmorItems()) {
            if(!(armorStack.getItem() instanceof ArmorItem)) {
                return false;
            }
        }

        ArmorItem boots = ((ArmorItem)entity.getEquippedStack(EquipmentSlot.FEET).getItem());
        ArmorItem leggings = ((ArmorItem)entity.getEquippedStack(EquipmentSlot.LEGS).getItem());
        ArmorItem breastplate = ((ArmorItem)entity.getEquippedStack(EquipmentSlot.CHEST).getItem());
        ArmorItem helmet = ((ArmorItem)entity.getEquippedStack(EquipmentSlot.HEAD).getItem());

        return helmet.getMaterial() == material && breastplate.getMaterial() == material &&
                leggings.getMaterial() == material && boots.getMaterial() == material;
    }
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(stack.get(ModDataComponentTypes.USED) == null) {
            stack.set(ModDataComponentTypes.USED, false);
        }

       /* if (world.isClient() && ModKeyBindings.C_KEY_BINDING.isPressed()) {
            if (entity instanceof PlayerEntity player && hasFullSuitOfArmorOn(player) && hasCorrectArmorOn(armorMat, player)) {
                stack.set(ModDataComponentTypes.USED, true);
            }
        }

        */
            if (entity instanceof LivingEntity player && !world.isClient()) {
                stack.set(ModDataComponentTypes.SLOT_LOCKED, stack.get(ModDataComponentTypes.USED));
                if(Boolean.TRUE.equals(stack.get(ModDataComponentTypes.USED))) {
                    if(trapTimer == trapTimerMax && hasFullSuitOfArmorOn(player)) {
                       oneTimeEffects(player, stack, world);
                    }
                    if(trapTimer % 20 == 0) {
                        perSecondEffects(player, stack, world);
                    }
                    constantEffects(player, stack, world);
                    trapTimer--;
                }
                if(trapTimer <= 0 || !entity.isAlive()) {
                    ((ArmorMaterialLayerAccessor) (Object) ((ArmorMaterialAccessor) (Object) armorMat.value()).firstmod$getLayers().getFirst()).firstmod$setSuffix("");
                    trapTimer = trapTimerMax;
                    stack.set(ModDataComponentTypes.USED, false);
                }
            }

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    public void oneTimeEffects(LivingEntity player, ItemStack stack, World world) {
        ((ArmorMaterialLayerAccessor) (Object) ((ArmorMaterialAccessor) (Object) armorMat.value()).firstmod$getLayers().getFirst()).firstmod$setSuffix("_triggered");
        if (!world.isClient()) {
            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLOCK_ANVIL_FALL, SoundCategory.NEUTRAL, 20f, 1f);
        }
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, trapTimerMax, 9, false, false, false));
        player.damage(player.getDamageSources().inWall(), 8);
    }

    public void perSecondEffects(LivingEntity player, ItemStack stack, World world) {
        if (!world.isClient()) {
            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_BLAZE_BURN, SoundCategory.NEUTRAL, 20f, 1f);
        }
        BlockPos pos = player.getBlockPos().up().up();
        if (!world.getBlockState(pos).getBlock().getDefaultState().isReplaceable()) {
            player.damage(player.getDamageSources().inWall(), 4);
        }
    }

    // Once per tick
    public void constantEffects(LivingEntity player, ItemStack stack, World world) {
        if(!world.isClient()) {
            ((ServerWorld) world).spawnParticles(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, player.getX(), player.getY(), player.getZ(), 1, 0, 0, 0, 0.1);
            ((ServerWorld) world).spawnParticles(ParticleTypes.FLAME, player.getX(), player.getY(), player.getZ(), 5, 0, 0, 0, 0.1);
        }
    }

    public boolean isTriggered(ItemStack stack) {
        return Boolean.TRUE.equals(stack.get(ModDataComponentTypes.USED));
    }

    public void trigger(ItemStack stack) {
        stack.set(ModDataComponentTypes.USED, true);

    }

    public void setTrapTimerMax(int timer) {
        this.trapTimerMax = timer;
    }



    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return (Boolean.TRUE.equals(stack.get(ModDataComponentTypes.USED))) || super.isItemBarVisible(stack);
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        if(Boolean.TRUE.equals(stack.get(ModDataComponentTypes.USED))) {
            return ColorHelper.Argb.getArgb(1, trapTimer + 55, 0, 0);
        }
        return super.getItemBarColor(stack);
    }


}
