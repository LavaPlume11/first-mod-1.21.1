package me.xander.firstmod;

import me.xander.first_mod;
import me.xander.firstmod.block.renderer.MelterBlockEntityRenderer;
import me.xander.firstmod.block.renderer.StoneOfSwordBlockEntityRenderer;
import me.xander.firstmod.block.renderer.TankBlockEntityRenderer;
import me.xander.firstmod.entity.ModEntities;
import me.xander.firstmod.entity.client.*;
import me.xander.firstmod.fluid.ModFluids;
import me.xander.firstmod.particle.BloodParticle;
import me.xander.firstmod.screen.ModScreenHandlers;
import me.xander.firstmod.screen.custom.*;
import me.xander.firstmod.block.ModBlocks;
import me.xander.firstmod.block.entity.ModBlockEntities;
import me.xander.firstmod.block.renderer.DisplayBlockEntityRenderer;
import me.xander.firstmod.util.ModModelPredicates;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
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
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TANK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MELTER, RenderLayer.getTranslucent());
        ModScreenHandlers.registerScreenHandlers();

        FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.STILL_MITHRIL_WATER, ModFluids.FLOWING_MITHRIL_WATER,
                SimpleFluidRenderHandler.coloredWater(0x576A39C7));
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(),
                ModFluids.STILL_MITHRIL_WATER, ModFluids.FLOWING_MITHRIL_WATER);

        BlockEntityRendererFactories.register(ModBlockEntities.DISPLAY_BE, DisplayBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.STONE_BE, StoneOfSwordBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.TANK_BE, TankBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.MELTER_BE, MelterBlockEntityRenderer::new);
        HandledScreens.register(ModScreenHandlers.DISPLAY_SCREEN_HANDLER, DisplayScreen::new);
        HandledScreens.register(ModScreenHandlers.CRYSTALLIZER_SCREEN_HANDLER, CrystallizerScreen::new);
        HandledScreens.register(ModScreenHandlers.WARTURTLE_SCREEN_HANDLER, WarturtleScreen::new);
        HandledScreens.register(ModScreenHandlers.ECHO_GENERATOR_SCREEN_HANDLER, EchoGeneratorScreen::new);
        HandledScreens.register(ModScreenHandlers.TANK_SCREEN_HANDLER, TankScreen::new);
        HandledScreens.register(ModScreenHandlers.COMPRESSOR_SCREEN_HANDLER, CompressorScreen::new);
        HandledScreens.register(ModScreenHandlers.MELTER_SCREEN_HANDLER, MelterScreen::new);

        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.LION, LionModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.LION, LionRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.LEMMING, LemmingModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.LEMMING, LemmingRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.WHISPERER, WhispererModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.WHISPERER, WhispererRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.TOMAHAWK, TomahawkProjectileModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.TOMAHAWK, TomahawkProjectileRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.WARTURTLE, WarturtleModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.WARTURTLE, WarturtleRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.WARTURTLE_ARMOR, WarturtleModel::getTexturedModelData);

        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.SLEIGH, SleighModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.SLEIGH, SleighRenderer::new);

        EntityRendererRegistry.register(ModEntities.DISPLAY_ENTITY, DisplayBlockEntityEntityRenderer::new);

        ParticleFactoryRegistry.getInstance().register(first_mod.BLOOD_PARTICLE, BloodParticle.Factory::new);

        ModModelPredicates.registerModelPredicates();

    }
}
