package io.github.velaliilunalii.cozy_cafe.entity.coaster;

import io.github.velaliilunalii.cozy_cafe.CommonClass;
import io.github.velaliilunalii.cozy_cafe.item.ColoredItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import static io.github.velaliilunalii.cozy_cafe.item.ColoredItem.setColor;

public class WoodenCoasterEntity extends CoasterEntity{
    public WoodenCoasterEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    public WoodenCoasterEntity(EntityType<?> entityType, Level level, Vec3 pos, float angle, ColoredItem.Color color) {
        super(entityType, level, pos, angle, color);
    }

    @Override
    public void dropCoasterItem() {
        ItemStack coasterStack = new ItemStack(CommonClass.WOODEN_COASTER_ITEM);
        setColor(coasterStack, this.getDataColor());
        ItemEntity itemEntity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), coasterStack);
        itemEntity.setDefaultPickUpDelay();
        this.level().addFreshEntity(itemEntity);
    }
}
