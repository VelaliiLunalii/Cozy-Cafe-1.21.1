package io.github.velaliilunalii.cozy_cafe.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.velaliilunalii.cozy_cafe.CommonClass;
import io.github.velaliilunalii.cozy_cafe.entity.MixingCupEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import org.jetbrains.annotations.UnknownNullability;

public class MixingCupEntityRenderer extends EntityRenderer<MixingCupEntity> {

    private final ItemRenderer itemRenderer;

    public MixingCupEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(MixingCupEntity entity, float entityYaw, float partialTicks,
                       PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);

        poseStack.pushPose();
        poseStack.translate(0, 0.219, 0);
        poseStack.mulPose(Axis.YN.rotationDegrees(Math.floorDiv((int) entity.getYRot() - 45, 90) * 90 + 90));

        ItemStack itemStack = new ItemStack(CommonClass.MIXING_CUP_ITEM);

        if (entity.isMixing()) {
            CustomData customData = itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
            customData = customData.update(tag -> {
                tag.putBoolean("Mixing", true);
            });
            itemStack.set(DataComponents.CUSTOM_DATA, customData);
        }

        ItemStack liquid = entity.getLiquid();
        if (!liquid.isEmpty()) {
            renderItem(liquid, poseStack, buffer, packedLight, entity, 0, false);
        }
        ItemStack mainFlavor = entity.getFlavor1();
        if (!mainFlavor.isEmpty()) {
            renderItem(mainFlavor, poseStack, buffer, packedLight, entity, 1, false);
        }
        ItemStack secondaryFlavor = entity.getFlavor2();
        if (!secondaryFlavor.isEmpty()) {
            renderItem(secondaryFlavor, poseStack, buffer, packedLight, entity, 1, true);
        }
        ItemStack sweetener = entity.getSweetener();
        if (!sweetener.isEmpty()) {
            renderItem(sweetener, poseStack, buffer, packedLight, entity, 2, false);
        }

        this.itemRenderer.renderStatic(
                itemStack,
                ItemDisplayContext.GROUND,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                buffer,
                entity.level(),
                entity.getId()
        );

        poseStack.popPose();
    }

    private void renderItem(ItemStack stack, PoseStack poseStack,
                            MultiBufferSource buffer, int packedLight, @UnknownNullability MixingCupEntity entity, int i, boolean second) {
        poseStack.pushPose();

        poseStack.mulPose(Axis.YP.rotationDegrees(-90 + 90 * i));

        poseStack.translate(0.0, -1.5F/16, -3.0F/16);
        poseStack.scale(0.6F, 0.6F, 0.6F);
        if(i==1) poseStack.translate(second ? 1.0/16 : -1.0/16, 0.0, second ? 0.5/16 : 0);

        BakedModel model = this.itemRenderer.getModel(stack, entity.level(), null, entity.getId());
        this.itemRenderer.render(
                stack,
                ItemDisplayContext.GROUND,
                false,
                poseStack,
                buffer,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                model
        );

        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(MixingCupEntity entity) {
        return null;
    }
}