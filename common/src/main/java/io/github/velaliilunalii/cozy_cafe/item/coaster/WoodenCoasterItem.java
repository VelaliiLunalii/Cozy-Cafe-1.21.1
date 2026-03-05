package io.github.velaliilunalii.cozy_cafe.item.coaster;

import io.github.velaliilunalii.cozy_cafe.CommonClass;
import io.github.velaliilunalii.cozy_cafe.entity.coaster.CoasterEntity;
import io.github.velaliilunalii.cozy_cafe.entity.coaster.WoodenCoasterEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class WoodenCoasterItem extends CoasterItem {
    public WoodenCoasterItem(Properties properties) {
        super(properties);
    }

    @Override
    public CoasterEntity getAssociatedEntity(Level level, Vec3 pos, float angle, Color color){
        return new WoodenCoasterEntity(CommonClass.WOODEN_COASTER, level, pos, angle, color);
    }
}
