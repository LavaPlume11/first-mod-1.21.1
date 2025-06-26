package me.xander.firstmod.util;

import me.xander.first_mod;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ModKeyBindings {
    public static final KeyBinding M_KEY_BINDING = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.firstmod.m",
            InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_M, "category.firstmod.test"));

    public static final KeyBinding G_KEY_BINDING = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.firstmod.g",
            InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "category.firstmod.test"));

    public static final KeyBinding ALT_KEY_BINDING = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.firstmod.alt",
            InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT, "category.firstmod.test"));
    public static void registerKeyBindings() {
        first_mod.LOGGER.info("Registering Key Bindings for"+ first_mod.MOD_ID);
    }
}
