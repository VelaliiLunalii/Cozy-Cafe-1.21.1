package io.github.velaliilunalii.cozy_cafe.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ItemUtil {
    public static boolean hasEntityInside(Entity dyeVatEntity) {
        List<Entity> entities = dyeVatEntity.level().getEntities(dyeVatEntity, dyeVatEntity.getBoundingBox(), Entity::isPickable);
        return !entities.isEmpty();
    }

    public static void smartAddItem(Player player, ItemStack itemStack){
        boolean hasSpace = player.getInventory().getSlotWithRemainingSpace(itemStack) != -1
                || player.getInventory().getFreeSlot() != -1;
        if (hasSpace) player.addItem(itemStack);
        else {
            ItemEntity itemEntity = new ItemEntity(player.level(), player.getX(), player.getY(), player.getZ(), itemStack);
            itemEntity.setDefaultPickUpDelay();
            player.level().addFreshEntity(itemEntity);
        }
    }
}
