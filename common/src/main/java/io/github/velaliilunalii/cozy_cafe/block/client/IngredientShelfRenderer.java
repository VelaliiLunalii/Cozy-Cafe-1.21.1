package io.github.velaliilunalii.cozy_cafe.block.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.velaliilunalii.cozy_cafe.block.IngredientShelfBlock;
import io.github.velaliilunalii.cozy_cafe.block.entity.IngredientShelfBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class IngredientShelfRenderer implements BlockEntityRenderer<IngredientShelfBlockEntity> {
    private static final float SIZE = 0.325F;
    private final ItemRenderer itemRenderer;

    public IngredientShelfRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    public void render(IngredientShelfBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Direction direction = (Direction)blockEntity.getBlockState().getValue(IngredientShelfBlock.FACING);
        NonNullList<ItemStack> nonnulllist = blockEntity.getItems();
        int i = (int)blockEntity.getBlockPos().asLong();

        for(int j = 0; j < nonnulllist.size(); ++j) {
            ItemStack itemstack = (ItemStack)nonnulllist.get(j);
            if (itemstack != ItemStack.EMPTY) {
                poseStack.pushPose();
                poseStack.translate(0.5F, 0.5F, 0.5F);
                poseStack.mulPose(Axis.YN.rotationDegrees(direction.get2DDataValue() * 90.F));
                poseStack.translate(-3F/16, -5.2F/16, -2.5F/16);
                poseStack.mulPose(Axis.XP.rotationDegrees(67.5F));
                poseStack.translate(j%2==1 ? 6F/16 : 0, j > 1 ? 6F/16 : 0, 0);
                poseStack.scale(SIZE, SIZE, SIZE);
                this.itemRenderer.renderStatic(itemstack, ItemDisplayContext.FIXED, packedLight, packedOverlay, poseStack, bufferSource, blockEntity.getLevel(), i + j);
                poseStack.popPose();
            }
        }

    }
}

