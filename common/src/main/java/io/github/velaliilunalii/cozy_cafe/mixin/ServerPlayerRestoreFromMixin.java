package io.github.velaliilunalii.cozy_cafe.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.monster.warden.WardenSpawnTracker;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerRestoreFromMixin {
    @Inject(
            method = "restoreFrom",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/server/level/ServerPlayer;enchantmentSeed:I",
                    shift = At.Shift.BEFORE
            )
    )
    private void addCustomRestoreLogic(ServerPlayer that, boolean keepEverything, CallbackInfo ci) {
        ServerPlayer serverPlayer = (ServerPlayer) (Object) this;

        if (true) {
            System.out.println(that.getInventory().getItem(1));
            serverPlayer.getInventory().setItem(1, that.getInventory().getItem(1));
        }
    }
}
