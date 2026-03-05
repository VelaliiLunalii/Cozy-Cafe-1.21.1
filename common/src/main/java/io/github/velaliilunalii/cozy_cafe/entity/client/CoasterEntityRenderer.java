package io.github.velaliilunalii.cozy_cafe.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.velaliilunalii.cozy_cafe.entity.coaster.CoasterEntity;
import io.github.velaliilunalii.cozy_cafe.item.coaster.CoasterItem;
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

public class CoasterEntityRenderer<C extends Entity> extends EntityRenderer<CoasterEntity> {

    private final ItemRenderer itemRenderer;

    public CoasterEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(CoasterEntity entity, float entityYaw, float partialTicks,
                       PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);

        poseStack.pushPose();

        renderCoasterModel(poseStack, buffer, packedLight, entity);

        ItemStack displayedItem = entity.getDisplayedItem();
        if (!displayedItem.isEmpty()) {
            renderDisplayedItem(displayedItem, poseStack, buffer, packedLight, entity);
        }

        poseStack.popPose();
    }

    private void renderCoasterModel(PoseStack poseStack, MultiBufferSource buffer, int packedLight, CoasterEntity entity) {
         ItemStack coasterStack = new ItemStack(getCoasterItem());
         setColor(coasterStack, entity.getDataColor());
         poseStack.pushPose();
         poseStack.translate(0, 0.219, 0);
         this.itemRenderer.renderStatic(
             coasterStack,
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

    public CoasterItem getCoasterItem(){
        return null;
    }

    private void renderDisplayedItem(ItemStack stack, PoseStack poseStack,
                                     MultiBufferSource buffer, int packedLight, CoasterEntity entity) {
        poseStack.pushPose();

        poseStack.translate(0.0, 0.15, 0.0);

        poseStack.mulPose(Axis.YN.rotationDegrees(entity.getYRot()));

        poseStack.scale(1f, 1f, 1f);

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
    public ResourceLocation getTextureLocation(CoasterEntity entity) {
        return null;
    }
}