package me.xander.firstmod.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import me.xander.first_mod;
import me.xander.firstmod.screen.renderer.EnergyInfoArea;
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

public class CompressorScreen extends HandledScreen<CompressorScreenHandler> {

    private static final Identifier GUI_TEXTURE =
            Identifier.of(first_mod.MOD_ID, "textures/gui/compressor/compressor_gui.png");
    private static final Identifier ARROW_TEXTURE =
            Identifier.of(first_mod.MOD_ID, "textures/gui/compressor/arrow_progress.png");
    private static final Identifier INSPECTION_TEXTURE =
            Identifier.of(first_mod.MOD_ID, "textures/gui/compressor/inspection_progress.png");
    public CompressorScreen(CompressorScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }
    private EnergyInfoArea energyInfoArea;

    private void assignEnergyInfoArea() {
        energyInfoArea = new EnergyInfoArea(((width - backgroundWidth) / 2) + 11,
                ((height - backgroundHeight) / 2 ) + 9, handler.blockEntity.energyStorage, 9, 45/*48*/);
    }
    private void renderEnergyAreaTooltips(DrawContext context, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 12, 9, 9, 45)) {
            context.drawTooltip(Screens.getTextRenderer(this), energyInfoArea.getTooltips(),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }
    private void renderInspectionTooltips(DrawContext context, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 31, 61, 108, 10)) {
            context.drawTooltip(Screens.getTextRenderer(this), handler.blockEntity.getTooltips(),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    @Override
    protected void init() {
        super.init();
        // Get rid of title and Inventory title
        titleY = 1000;
        playerInventoryTitleY = 1000;
        assignEnergyInfoArea();
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        renderEnergyAreaTooltips(context, mouseX, mouseY, x, y);
        renderInspectionTooltips(context, mouseX, mouseY, x, y);

    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(GUI_TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
        context.drawItem(this.handler.blockEntity.currentInspectedItem, x + 150, y + 10);;
        renderProgressArrow(context, x, y);
        renderInspectionProgress(context, x, y);
        energyInfoArea.draw(context);



    }

    private void renderProgressArrow(DrawContext context, int x, int y) {
        if(handler.isCrafting()) {
            context.drawTexture(ARROW_TEXTURE, x + 79, y + 25, 0,0,
                    handler.getScaledArrowProgress(), 16,24,16);
        }
    }
    private void renderInspectionProgress(DrawContext context, int x, int y) {
        if(handler.isInspecting()) {
            context.drawTexture(INSPECTION_TEXTURE, x + 31, y + 61, 0,0,
                    handler.getScaledInspectionProgress(), 9,106,9);
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
