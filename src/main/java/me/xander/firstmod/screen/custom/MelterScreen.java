package me.xander.firstmod.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import me.xander.first_mod;
import me.xander.firstmod.recipe.MelterRecipe;
import me.xander.firstmod.screen.renderer.FluidStackRenderer;
import me.xander.firstmod.util.MouseUtil;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class MelterScreen extends HandledScreen<MelterScreenHandler> {
    public MelterScreen(MelterScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }
    private static final Identifier GUI_TEXTURE =
            Identifier.of(first_mod.MOD_ID, "textures/gui/melter/melter_gui.png");
    private static final Identifier ARROW_TEXTURE =
            Identifier.of(first_mod.MOD_ID, "textures/gui/melter/arrow_progress.png");
    private static final Identifier ARROW_ERROR_TEXTURE =
            Identifier.of(first_mod.MOD_ID, "textures/gui/melter/error.png");
    private FluidStackRenderer fluidStackRenderer;

    @Override
    protected void init() {
        super.init();
        titleY = 1000;
        playerInventoryTitleY = 1000;
        assignFluidStackRenderer();
    }
    private void assignFluidStackRenderer() {
        fluidStackRenderer = new FluidStackRenderer((FluidConstants.BUCKET / 81) * 4, true, 14, 57);
    }
    private void renderFluidTooltip(DrawContext context, int mouseX, int mouseY, int x, int y, int offsetX, int offsetY, FluidStackRenderer renderer) {
        if(isMouseAboveArea(mouseX, mouseY, x, y, offsetX, offsetY, renderer)) {
            context.drawTooltip(Screens.getTextRenderer(this), renderer.getTooltip(handler.blockEntity.fluidStorage, Item.TooltipContext.DEFAULT),
                    Optional.empty(), mouseX - x, mouseY - y);
        }
    }
    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        renderErrorTooltips(context, mouseX, mouseY, x, y);
        renderFluidTooltip(context, mouseX, mouseY, x, y, 105, 12, fluidStackRenderer);

    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(GUI_TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
        renderProgressArrow(context, x, y);
        fluidStackRenderer.drawFluid(context, handler.blockEntity.fluidStorage, x + 105, y + 11, 14, 57,
                (FluidConstants.BUCKET / 81) * 4);
    }

    private void renderProgressArrow(DrawContext context, int x, int y) {
        int heat = this.handler.getHeat();
        if(handler.isCrafting()) {
            context.drawTexture(ARROW_TEXTURE, x + 77, y + 33, 0,0,
                    handler.getScaledArrowProgress(), 16,24,16);
        }
            if (heat == this.handler.getRequiredHeat()) {
                    context.drawTexture(ARROW_TEXTURE, x + 29, y + 34, 0, 0, 24,
                            16, 24, 16);
        } else {
            renderErrorArrow(context,x,y);
        }
    }
    private void renderErrorTooltips(DrawContext context, int pMouseX, int pMouseY, int x, int y) {
        int heat = this.handler.getHeat();
        if (heat != this.handler.getRequiredHeat()) {
            if (isMouseAboveArea(pMouseX, pMouseY, x, y, 29, 34, 24, 16)) {
                context.drawTooltip(Screens.getTextRenderer(this), this.handler.getErrorTooltips(),
                        Optional.empty(), pMouseX - x, pMouseY - y);
            }
        }
    }
    private void renderErrorArrow(DrawContext context, int x, int y) {
        context.drawTexture(ARROW_ERROR_TEXTURE, x + 29, y + 34, 0,0, 24,
                16,24,16);
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
