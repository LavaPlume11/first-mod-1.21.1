package me.xander.firstmod.item.custom;

import me.xander.firstmod.entity.custom.TyriniteArrowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TyriniteArrowItem extends ArrowItem {
    public TyriniteArrowItem(Item.Settings settings) {
        super(settings);
    }
    @Override
    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter, @Nullable ItemStack shotFrom) {
        return new TyriniteArrowEntity(world, shooter, stack.copyWithCount(1), shotFrom);
    }

    @Override
    public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
        TyriniteArrowEntity tyriniteArrowEntity = new TyriniteArrowEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack.copyWithCount(1), (ItemStack)null);
        tyriniteArrowEntity.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
        return tyriniteArrowEntity;
    }
}
