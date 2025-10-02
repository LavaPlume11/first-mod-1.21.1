package me.xander.firstmod.inventory;

import me.xander.firstmod.item.custom.ModItems;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;

public class PocketStorageInventory extends SimpleInventory {


	public PocketStorageInventory() {
		super(18);
	}


	public void readNbtList(NbtList list, RegistryWrapper.WrapperLookup registries) {
		int i;
		for(i = 0; i < this.size(); ++i) {
			this.setStack(i, ItemStack.EMPTY);
		}

		for(i = 0; i < list.size(); ++i)  {
			NbtCompound nbtCompound = list.getCompound(i);
			int j = nbtCompound.getByte("Slot") & 255;
			if (j >= 0 && j < this.size()) {
				this.setStack(j, (ItemStack)ItemStack.fromNbt(registries, nbtCompound).orElse(ItemStack.EMPTY));
			}
		}

	}

	public NbtList toNbtList(RegistryWrapper.WrapperLookup registries) {
		NbtList nbtList = new NbtList();

		for(int i = 0; i < this.size(); ++i) {
			ItemStack itemStack = this.getStack(i);
			if (!itemStack.isEmpty()) {
				NbtCompound nbtCompound = new NbtCompound();
				nbtCompound.putByte("Slot", (byte)i);
				nbtList.add(itemStack.encode(registries, nbtCompound));
			}
		}
		return nbtList;
	}

}
