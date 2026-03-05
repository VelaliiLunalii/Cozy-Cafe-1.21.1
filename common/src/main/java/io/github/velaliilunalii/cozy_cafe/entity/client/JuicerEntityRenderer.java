package io.github.velaliilunalii.cozy_cafe.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.velaliilunalii.cozy_cafe.CommonClass;
import io.github.velaliilunalii.cozy_cafe.entity.JuicerEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public class JuicerEntityRenderer<C extends Entity> extends EntityRenderer<JuicerEntity> {

    private final ItemRenderer itemRenderer;

    public JuicerEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(JuicerEntity entity, float entityYaw, float partialTicks,
                       PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);

        poseStack.pushPose();

        poseStack.mulPose(Axis.YN.rotationDegrees(Math.floorDiv((int) entity.getYRot() - 45, 90) * 90 + 90));

        renderJuicerModel(poseStack, buffer, packedLight, entity);

        ItemStack emptyBottle = entity.getEmptyBottle();
        if (!emptyBottle.isEmpty()) {
            renderEmptyBottle(emptyBottle, poseStack, buffer, packedLight, entity);
        }
        ItemStack filledBottle = entity.getFilledBottle();
        if (!filledBottle.isEmpty()) {
            renderFilledBottle(filledBottle, poseStack, buffer, packedLight, entity);
        }
        ItemStack ingredient = entity.getIngredient();
        if (!ingredient.isEmpty() && !entity.isJuicing()) {
            renderIngredient(ingredient, poseStack, buffer, packedLight, entity);
        }

        poseStack.popPose();
    }

    private void renderJuicerModel(PoseStack poseStack, MultiBufferSource buffer, int packedLight, JuicerEntity entity) {
         ItemStack itemStack = new ItemStack(CommonClass.JUICER_ITEM);
         poseStack.pushPose();
         poseStack.translate(0, 0.234, 0);

        if (entity.isJuicing()) {
            CustomData customData = itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
            customData = customData.update(tag -> {
                tag.putBoolean("Pressing", true);
            });
            itemStack.set(DataComponents.CUSTOM_DATA, customData);
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

    private void renderEmptyBottle(ItemStack stack, PoseStack poseStack,
                                     MultiBufferSource buffer, int packedLight, JuicerEntity entity) {
        poseStack.pushPose();

        poseStack.translate(-0.2F/16, 0.1, -3.5F/16);
        poseStack.scale(0.9F, 0.9F, 0.9F);

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

    private void renderFilledBottle(ItemStack stack, PoseStack poseStack,
                                    MultiBufferSource buffer, int packedLight, JuicerEntity entity) {
        poseStack.pushPose();

        poseStack.translate(-4.7F/16, 0.1, 0.0);
        poseStack.mulPose(Axis.YN.rotationDegrees(90));
        poseStack.mulPose(Axis.XN.rotationDegrees(25));
        poseStack.scale(0.9F, 0.9F, 0.9F);

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

    private void renderIngredient(ItemStack stack, PoseStack poseStack,
                                    MultiBufferSource buffer, int packedLight, JuicerEntity entity) {
        poseStack.pushPose();

        poseStack.translate(0.0, 0.55, 0.0);
        poseStack.scale(0.5F, 0.5F, 0.5F);

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
    public ResourceLocation getTextureLocation(JuicerEntity entity) {
        return null;
    }
}