package io.github.velaliilunalii.cozy_cafe.mixin;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Player.class)
public class PlayerDropMixin {
    @Inject(at = @At("HEAD"), method = "dropEquipment", cancellable = true)
    public void shouldDropLoot(CallbackInfo ci){
        Player player = (Player) (Object) this;
        if (!player.level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
            ItemStack savedStack = player.getInventory().getItem(1).copy();

            destroyVanishingCursedItems();

            Inventory inventory = player.getInventory();
            List<NonNullList<ItemStack>> compartments = ((InventoryAccessor) inventory).getCompartments();
            for(int j = 0; j < compartments.size(); j++) {
                List<ItemStack> list = compartments.get(j);
                for(int i = 0; i < list.size(); ++i) {
                    ItemStack itemstack = (ItemStack)list.get(i);
                    boolean isSlot = i==1 && j==0;
                    if (!itemstack.isEmpty() && !isSlot) {
                        inventory.player.drop(itemstack, true, false);
                        list.set(i, ItemStack.EMPTY);
                    }
                }
            }

            player.getInventory().setItem(1, savedStack);
        }
        ci.cancel();
    }

    @Shadow
    protected void destroyVanishingCursedItems() {

    }
}
