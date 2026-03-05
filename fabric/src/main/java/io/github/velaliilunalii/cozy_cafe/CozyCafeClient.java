package io.github.velaliilunalii.cozy_cafe;

import io.github.velaliilunalii.cozy_cafe.block.client.IngredientShelfRenderer;
import io.github.velaliilunalii.cozy_cafe.entity.client.*;
import io.github.velaliilunalii.cozy_cafe.particle.MixingParticle;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class CozyCafeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(
                CommonClass.WOODEN_COASTER,
                WoodenCoasterEntityRenderer::new
        );
        EntityRendererRegistry.register(
                CommonClass.BAMBOO_COASTER,
                BambooCoasterEntityRenderer::new
        );
        EntityRendererRegistry.register(
                CommonClass.MIXING_CUP,
                MixingCupEntityRenderer::new
        );
        EntityRendererRegistry.register(
                CommonClass.DYE_VAT,
                DyeVatEntityRenderer::new
        );
        EntityRendererRegistry.register(
                CommonClass.JUICER,
                JuicerEntityRenderer::new
        );
        EntityRendererRegistry.register(
                CommonClass.COCONUT,
                ThrownItemRenderer::new
        );
        BlockRenderLayerMap.INSTANCE.putBlock(CommonClass.DECORATIVE_BAMBOO, RenderType.cutoutMipped());
        BlockEntityRendererRegistry.register(
                CommonClass.INGREDIENT_SHELF_BLOCK_ENTITY,
                IngredientShelfRenderer::new
        );
        ParticleFactoryRegistry.getInstance().register(
                CommonClass.BLUE_MIXING_PARTICLE,
                MixingParticle.Provider::new
        );
        ParticleFactoryRegistry.getInstance().register(
                CommonClass.GREEN_MIXING_PARTICLE,
                MixingParticle.Provider::new
        );
        ParticleFactoryRegistry.getInstance().register(
                CommonClass.RED_MIXING_PARTICLE,
                MixingParticle.Provider::new
        );
    }
}
