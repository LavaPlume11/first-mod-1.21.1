package me.xander.firstmod.item.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class godStick extends SwordItem {
    private final RegistryEntry<StatusEffect> effect;




    public godStick(ToolMaterial toolMaterial, Settings settings, RegistryEntry<StatusEffect> effect) {
        super(toolMaterial, settings);
        this.effect = effect;
    }

    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        attacker.playSound(SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, 20.0f, 1.0f);
        super.postDamageEntity(stack, target, attacker);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            BlockPos playerPos = user.getBlockPos().down(10);
            EntityType.LIGHTNING_BOLT.spawn((ServerWorld) world, playerPos, SpawnReason.TRIGGERED);

        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos().up(); // Position above the block
        if (!world.isClient) {
            context.getWorld().playSound(null,context.getBlockPos(),SoundEvents.ENTITY_DRAGON_FIREBALL_EXPLODE, SoundCategory.HOSTILE,1,1);

            // server particals
            ((ServerWorld) context.getWorld()).spawnParticles(ParticleTypes.SCULK_SOUL, context.getBlockPos().getX() + 0.5f, context.getBlockPos().getY()
            + 1.0f,context.getBlockPos().getZ()+0.5f,500,0.1,20.5,0.1,0.2);


        }

        return ActionResult.SUCCESS;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.addStatusEffect(new StatusEffectInstance(effect, 200, 9), attacker);



        return super.postHit(stack, target, attacker);
    }
}

