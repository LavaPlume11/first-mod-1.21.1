package me.xander.firstmod;

import me.xander.firstmod.entity.ModEntities;
import me.xander.firstmod.entity.client.*;
import me.xander.firstmod.item.ModArmorMaterials;
import me.xander.firstmod.screen.ModScreenHandlers;
import me.xander.firstmod.screen.custom.CrystallizerScreen;
import me.xander.firstmod.screen.custom.DisplayScreen;
import me.xander.firstmod.block.ModBlocks;
import me.xander.firstmod.block.entity.ModBlockEntities;
import me.xander.firstmod.block.renderer.DisplayBlockEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class First_modClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MITHRIL_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BANANA_BUSH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLACKWOOD_SAPLING, RenderLayer.getCutout());
        ModScreenHandlers.registerScreenHandlers();
        BlockEntityRendererFactories.register(ModBlockEntities.DISPLAY_BE, DisplayBlockEntityRenderer::new);
        HandledScreens.register(ModScreenHandlers.DISPLAY_SCREEN_HANDLER, DisplayScreen::new);
        HandledScreens.register(ModScreenHandlers.CRYSTALLIZER_SCREEN_HANDLER, CrystallizerScreen::new);

        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.LION, LionModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.LION, LionRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.LEMMING, LemmingModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.LEMMING, LemmingRenderer::new);
    }
}
