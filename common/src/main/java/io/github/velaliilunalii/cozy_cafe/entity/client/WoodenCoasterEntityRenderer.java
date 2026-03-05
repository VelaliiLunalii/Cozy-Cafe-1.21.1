package io.github.velaliilunalii.cozy_cafe.entity.client;

import io.github.velaliilunalii.cozy_cafe.CommonClass;
import io.github.velaliilunalii.cozy_cafe.entity.coaster.CoasterEntity;
import io.github.velaliilunalii.cozy_cafe.item.coaster.CoasterItem;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class WoodenCoasterEntityRenderer extends CoasterEntityRenderer<CoasterEntity> {

    public WoodenCoasterEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public CoasterItem getCoasterItem(){
        return CommonClass.WOODEN_COASTER_ITEM;
    }
}