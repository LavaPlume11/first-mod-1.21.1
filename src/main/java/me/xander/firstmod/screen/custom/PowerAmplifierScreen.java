package me.xander.firstmod.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import me.xander.first_mod;
import me.xander.firstmod.screen.renderer.FluidStackRenderer;
import me.xander.firstmod.util.MouseUtil;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class PowerAmplifierScreen extends HandledScreen<PowerAmplifierScreenHandler> {
    private static final Identifier GUI_TEXTURE =
            Identifier.of(first_mod.MOD_ID, "textures/gui/power_amplifier/power_amplifier_gui.png");
    private static final Identifier GUI_TEXTURE_CHARGED =
            Identifier.of(first_mod.MOD_ID, "textures/gui/power_amplifier/power_amplifier_gui_charged.png");
    private static final Identifier POWER_TEXTURE =
            Identifier.of(first_mod.MOD_ID,"textures/gui/power_amplifier/power.png");

    public PowerAmplifierScreen(PowerAmplifierScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleY = 1000;
        playerInventoryTitleY = 1000;
    }
    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        //renderTooltips(context, mouseX, mouseY,x,y);

    }
    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        renderGui(context, x, y);
        renderPower1(context, x, y);
        renderPower2(context, x, y);
        
    }
    private void renderTooltips(DrawContext context, int pMouseX, int pMouseY, int x, int y) {
        if (handler.isCrafting()) {
            if (isMouseAboveArea(pMouseX, pMouseY, x, y, 0, 0, 256, 256)) {
                context.drawTooltip(Screens.getTextRenderer(this), handler.blockEntity.getTooltips(),
                        Optional.empty(), pMouseX - x, pMouseY - y);
            }
        }
    }
    private void renderPower1(DrawContext context, int x, int y) {
        if(handler.isCharged1()) {
            context.drawTexture(POWER_TEXTURE,x + 58, y + 48 + 9 - handler.getScaledPowerProgress1(), 0,
                    45 - handler.getScaledPowerProgress1(), 9, handler.getScaledPowerProgress1(),9, 45);
        }
    }

    private void renderPower2(DrawContext context, int x, int y) {
        if(handler.isCharged2()) {
            context.drawTexture(POWER_TEXTURE,x + 107, y + 48 + 9 - handler.getScaledPowerProgress2(), 0,
                    45 - handler.getScaledPowerProgress2(), 9, handler.getScaledPowerProgress2(),9, 45);
        }
    }
    private void renderGui(DrawContext context, int x, int y) {
        if (handler.isFullyCharged()) {
            context.drawTexture(GUI_TEXTURE_CHARGED, x, y, 0, 0, backgroundWidth, backgroundHeight);
        } else {
            context.drawTexture(GUI_TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
        }
    }



    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
    public static boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, FluidStackRenderer renderer) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, renderer.getWidth(), renderer.getHeight());
    }
}
