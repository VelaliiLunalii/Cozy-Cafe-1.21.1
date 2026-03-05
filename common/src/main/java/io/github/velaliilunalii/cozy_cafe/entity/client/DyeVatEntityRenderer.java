package io.github.velaliilunalii.cozy_cafe.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.velaliilunalii.cozy_cafe.CommonClass;
import io.github.velaliilunalii.cozy_cafe.entity.DyeVatEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import static io.github.velaliilunalii.cozy_cafe.item.ColoredItem.setColor;

public class DyeVatEntityRenderer<C extends Entity> extends EntityRenderer<DyeVatEntity> {

    private final ItemRenderer itemRenderer;

    public DyeVatEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(DyeVatEntity entity, float entityYaw, float partialTicks,
                       PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);

        poseStack.pushPose();

        renderDyeVatModel(poseStack, buffer, packedLight, entity);

        ItemStack inputItem = entity.getInputItem();
        if (!inputItem.isEmpty()) {
            renderDisplayedItem(inputItem, poseStack, buffer, packedLight, entity, false);
        }
        ItemStack outputItem = entity.getOutputItem();
        if (!outputItem.isEmpty()) {
            renderDisplayedItem(outputItem, poseStack, buffer, packedLight, entity, true);
        }

        poseStack.popPose();
    }

    private void renderDyeVatModel(PoseStack poseStack, MultiBufferSource buffer, int packedLight, DyeVatEntity entity) {
         ItemStack dyeVatStack = new ItemStack(CommonClass.DYE_VAT_ITEM);
         setColor(dyeVatStack, entity.getDataColor());
         poseStack.pushPose();
         poseStack.translate(0, 0.228, 0);
         poseStack.scale(1.336F, 1.336F, 1.336F);
         this.itemRenderer.renderStatic(
             dyeVatStack,
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

    private void renderDisplayedItem(ItemStack stack, PoseStack poseStack,
                                     MultiBufferSource buffer, int packedLight, DyeVatEntity entity, boolean high) {
        poseStack.pushPose();

        poseStack.translate(0.0, 0.7, 0.0);
        if(high){
            poseStack.translate(0.2, 0.32, 0.0);
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            poseStack.mulPose(Axis.ZP.rotationDegrees(30.0F));
        }
        poseStack.mulPose(Axis.ZP.rotationDegrees(15.0F));

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
    public ResourceLocation getTextureLocation(DyeVatEntity entity) {
        return null;
    }
}