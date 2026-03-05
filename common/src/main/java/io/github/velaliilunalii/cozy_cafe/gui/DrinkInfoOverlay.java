package io.github.velaliilunalii.cozy_cafe.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.velaliilunalii.cozy_cafe.CommonClass;
import io.github.velaliilunalii.cozy_cafe.food.DrinkIngredient;
import io.github.velaliilunalii.cozy_cafe.item.DrinkItem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class DrinkInfoOverlay {
    private static final int MAX_SIPS = CommonClass.GLASS_TUMBLER_MAX_SIPS;
    private static final ResourceLocation[] GLASS_TUMBLER = {
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/glass_tumbler_sips_0.png"),
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/glass_tumbler_sips_1.png"),
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/glass_tumbler_sips_2.png"),
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/glass_tumbler_sips_3.png"),
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/glass_tumbler_sips_4.png"),
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/glass_tumbler_sips_5.png"),
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/glass_tumbler_sips_6.png"),
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/glass_tumbler_sips_7.png"),
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/glass_tumbler_sips_8.png"),
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/glass_tumbler_sips_9.png")
    };
    private static final ResourceLocation[] SPLIT_COCONUT_SIPS = {
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/split_coconut_sips_0.png"),
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/split_coconut_sips_1.png"),
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/split_coconut_sips_2.png"),
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/split_coconut_sips_3.png")
    };
    private static final ResourceLocation[] SODA_BOTTLE_SIPS = {
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/soda_bottle_sips_0.png"),
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/soda_bottle_sips_1.png"),
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/soda_bottle_sips_2.png"),
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/soda_bottle_sips_3.png"),
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/soda_bottle_sips_4.png"),
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/soda_bottle_sips_5.png"),
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/soda_bottle_sips_6.png")
    };
    private static final ResourceLocation[] GLASS_CAN_SIPS = {
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/glass_can_sips_0.png"),
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/glass_can_sips_1.png"),
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/glass_can_sips_2.png"),
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/glass_can_sips_3.png"),
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/glass_can_sips_4.png"),
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/glass_can_sips_5.png"),
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/glass_can_sips_6.png"),
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/glass_can_sips_7.png"),
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/glass_can_sips_8.png"),
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/glass_can_sips_9.png")
    };
    private static final ResourceLocation[] COCKTAIL_GLASS_SIPS = {
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/cocktail_glass_sips_0.png"),
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/cocktail_glass_sips_1.png"),
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/cocktail_glass_sips_2.png"),
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/cocktail_glass_sips_3.png")
    };
    private static final ResourceLocation[] GLASS_CUP_SIPS = {
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/glass_cup_sips_0.png"),
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/glass_cup_sips_1.png"),
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/glass_cup_sips_2.png"),
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/glass_cup_sips_3.png"),
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/glass_cup_sips_4.png"),
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/glass_cup_sips_5.png"),
            ResourceLocation.fromNamespaceAndPath("cozy_cafe", "textures/gui/glass_cup_sips_6.png")
    };

    public static void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;

        if (player == null) return;

        ItemStack mainHandItem = player.getMainHandItem();
        ItemStack offhandItem = player.getOffhandItem();
        if (!mainHandItem.isEmpty() && mainHandItem.getItem() instanceof DrinkItem){
            renderSidedDrink(guiGraphics, mainHandItem, true);
        }
        if (!offhandItem.isEmpty() && offhandItem.getItem() instanceof DrinkItem){
            renderSidedDrink(guiGraphics, offhandItem, false);
        }
    }

    private static void renderSidedDrink(GuiGraphics guiGraphics, ItemStack itemStack, boolean isMainHand){
        Minecraft minecraft = Minecraft.getInstance();
        CustomData customData = itemStack.get(DataComponents.CUSTOM_DATA);
        if (customData == null) return;

        int currentSips = customData.copyTag().getInt("Sips");

        if (currentSips < 0 || currentSips > MAX_SIPS) return;

        Optional<DrinkIngredient.ContainerType> container = DrinkIngredient.FilledContainer.getFilledContainer(itemStack);
        if(container.isEmpty()) return;
        ResourceLocation resourceLocation = getResourceLocation(container, currentSips);
        if(resourceLocation == null) return;

        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();

        int x = screenWidth / 2 + (isMainHand ? 92 : -136);
        int y = screenHeight - 18;

        RenderSystem.enableBlend();
        guiGraphics.blit(
                resourceLocation,
                x, y,
                0, 0,
                16, 16,
                16, 16
        );
        RenderSystem.disableBlend();
    }

    private static @Nullable ResourceLocation getResourceLocation(Optional<DrinkIngredient.ContainerType> container, int currentSips) {
        ResourceLocation resourceLocation = null;
        if(container.get().equals(DrinkIngredient.ContainerType.GLASS_TUMBLER)) resourceLocation = GLASS_TUMBLER[currentSips];
        if(container.get().equals(DrinkIngredient.ContainerType.SPLIT_COCONUT)) resourceLocation = SPLIT_COCONUT_SIPS[currentSips];
        if(container.get().equals(DrinkIngredient.ContainerType.SODA_BOTTLE)) resourceLocation = SODA_BOTTLE_SIPS[currentSips];
        if(container.get().equals(DrinkIngredient.ContainerType.GLASS_CAN)) resourceLocation = GLASS_CAN_SIPS[currentSips];
        if(container.get().equals(DrinkIngredient.ContainerType.COCKTAIL_GLASS)) resourceLocation = COCKTAIL_GLASS_SIPS[currentSips];
        if(container.get().equals(DrinkIngredient.ContainerType.GLASS_CUP)) resourceLocation = GLASS_CUP_SIPS[currentSips];
        return resourceLocation;
    }
}