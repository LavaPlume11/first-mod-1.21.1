package me.xander.firstmod.util;

import net.minecraft.component.type.NbtComponent;
import net.minecraft.nbt.NbtCompound;

public interface IEntityDataSaver {
    NbtCompound getPersistentData();
}
