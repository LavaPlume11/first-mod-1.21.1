package me.xander.firstmod.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import me.xander.first_mod;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class DisplayScreen extends HandledScreen<DisplayScreenHandler> {
    public static final Identifier GUI_TEXTURE =
            Identifier.of(first_mod.MOD_ID, "textures/gui/mithril_display_block/display_gui.png");

    public DisplayScreen(DisplayScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.setShaderTexture(0,GUI_TEXTURE);
        int x =(width - backgroundWidth) / 2;
        int y =(height - backgroundHeight) / 2;
        context.drawTexture(GUI_TEXTURE, x , y, 0, 0, backgroundWidth, backgroundHeight);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
