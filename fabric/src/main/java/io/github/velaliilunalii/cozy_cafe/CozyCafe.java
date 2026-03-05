package io.github.velaliilunalii.cozy_cafe;

import io.github.velaliilunalii.cozy_cafe.block.entity.IngredientShelfBlockEntity;
import io.github.velaliilunalii.cozy_cafe.food.DrinkIngredientEffects;
import io.github.velaliilunalii.cozy_cafe.gui.DrinkInfoOverlay;
import io.github.velaliilunalii.cozy_cafe.item.ColoredItem;
import io.github.velaliilunalii.cozy_cafe.component.CupColorComponent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class CozyCafe implements ModInitializer {
    @Override
    public void onInitialize() {
        CommonClass.init();

        CommonClass.REFRESHED_EFFECT = Registry.registerForHolder(
                BuiltInRegistries.MOB_EFFECT,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "refreshed"),
                CommonClass.REFRESHED_EFFECT_SUPPLIER.get()
        );
        CommonClass.SMALL_ABSORPTION_EFFECT = Registry.registerForHolder(
                BuiltInRegistries.MOB_EFFECT,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "small_absorption"),
                CommonClass.SMALL_ABSORPTION_EFFECT_SUPPLIER.get()
        );
        CommonClass.LATTE_EFFECT = Registry.registerForHolder(
                BuiltInRegistries.MOB_EFFECT,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "latte_effect"),
                CommonClass.LATTE_EFFECT_SUPPLIER.get()
        );
        CommonClass.TEA_EFFECT = Registry.registerForHolder(
                BuiltInRegistries.MOB_EFFECT,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "tea_effect"),
                CommonClass.TEA_EFFECT_SUPPLIER.get()
        );
        CommonClass.SHARE_FOOD_EFFECT = Registry.registerForHolder(
                BuiltInRegistries.MOB_EFFECT,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "share_food_effect"),
                CommonClass.SHARE_FOOD_EFFECT_SUPPLIER.get()
        );
        CommonClass.REVEALING_EFFECT = Registry.registerForHolder(
                BuiltInRegistries.MOB_EFFECT,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "revealing_effect"),
                CommonClass.REVEALING_EFFECT_SUPPLIER.get()
        );
        CommonClass.WARPING_EFFECT = Registry.registerForHolder(
                BuiltInRegistries.MOB_EFFECT,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "warping_effect"),
                CommonClass.WARPING_EFFECT_SUPPLIER.get()
        );
        DrinkIngredientEffects.addEffects();

        CommonClass.GLASS_TUMBLER = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "glass_tumbler"),
                CommonClass.GLASS_TUMBLER_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.GLASS_TUMBLER);
        CommonClass.SPLIT_COCONUT = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "split_coconut"),
                CommonClass.SPLIT_COCONUT_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.SPLIT_COCONUT);
        CommonClass.SODA_BOTTLE = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "soda_bottle"),
                CommonClass.SODA_BOTTLE_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.SODA_BOTTLE);
        CommonClass.GLASS_CAN = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "glass_can"),
                CommonClass.GLASS_CAN_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.GLASS_CAN);
        CommonClass.COCKTAIL_GLASS = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "cocktail_glass"),
                CommonClass.COCKTAIL_GLASS_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.COCKTAIL_GLASS);
        CommonClass.GLASS_CUP = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "glass_cup"),
                CommonClass.GLASS_CUP_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.GLASS_CUP);
        CommonClass.CHOCOLATE_MILK = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "chocolate_milk"),
                CommonClass.CHOCOLATE_MILK_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.CHOCOLATE_MILK);
        CommonClass.EMPTY_BOTTLE = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "empty_bottle"),
                CommonClass.EMPTY_BOTTLE_SUPPLIER.get()
        );
        CommonClass.WATER_BOTTLE = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "water_bottle"),
                CommonClass.WATER_BOTTLE_SUPPLIER.get()
        );
        CommonClass.MILK_BOTTLE = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "milk_bottle"),
                CommonClass.MILK_BOTTLE_SUPPLIER.get()
        );
        CommonClass.COCONUT_MILK_BOTTLE = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "coconut_milk_bottle"),
                CommonClass.COCONUT_MILK_BOTTLE_SUPPLIER.get()
        );
        CommonClass.SPARKLING_WATER = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "sparkling_water"),
                CommonClass.SPARKLING_WATER_SUPPLIER.get()
        );
        CommonClass.CHERRY_JUICE = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "cherry_juice"),
                CommonClass.CHERRY_JUICE_SUPPLIER.get()
        );
        CommonClass.BAMBOO_MILK = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "bamboo_milk"),
                CommonClass.BAMBOO_MILK_SUPPLIER.get()
        );
        CommonClass.CHORUS_JUICE = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "chorus_juice"),
                CommonClass.CHORUS_JUICE_SUPPLIER.get()
        );
        CommonClass.GLOW_BERRIES_JUICE = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "glow_berries_juice"),
                CommonClass.GLOW_BERRIES_JUICE_SUPPLIER.get()
        );
        CommonClass.SCULK_JUICE = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "sculk_juice"),
                CommonClass.SCULK_JUICE_SUPPLIER.get()
        );
        CommonClass.PUMPKIN_JUICE = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "pumpkin_juice"),
                CommonClass.PUMPKIN_JUICE_SUPPLIER.get()
        );
        CommonClass.MELON_JUICE = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "melon_juice"),
                CommonClass.MELON_JUICE_SUPPLIER.get()
        );
        CommonClass.SWEET_BERRIES_JUICE = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "sweet_berries_juice"),
                CommonClass.SWEET_BERRIES_JUICE_SUPPLIER.get()
        );
        CommonClass.APPLE_JUICE = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "apple_juice"),
                CommonClass.APPLE_JUICE_SUPPLIER.get()
        );
        CommonClass.SPORE_BLOSSOM_JUICE = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "spore_blossom_juice"),
                CommonClass.SPORE_BLOSSOM_JUICE_SUPPLIER.get()
        );
        CommonClass.MATCHA_TEA_BAG = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "matcha_tea_bag"),
                CommonClass.MATCHA_TEA_BAG_SUPPLIER.get()
        );
        CommonClass.COCONUT_ITEM = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "coconut"),
                CommonClass.COCONUT_ITEM_SUPPLIER.get()
        );

        CommonClass.WOODEN_COASTER_ITEM = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "wooden_coaster"),
                CommonClass.WOODEN_COASTER_ITEM_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.WOODEN_COASTER_ITEM);
        CommonClass.BAMBOO_COASTER_ITEM = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "bamboo_coaster"),
                CommonClass.BAMBOO_COASTER_ITEM_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.BAMBOO_COASTER_ITEM);

        CommonClass.MIXING_CUP_ITEM = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "mixing_cup"),
                CommonClass.MIXING_CUP_ITEM_SUPPLIER.get()
        );
        registerMixingProperty(CommonClass.MIXING_CUP_ITEM);

        CommonClass.DYE_VAT_ITEM = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "dye_vat"),
                CommonClass.DYE_VAT_ITEM_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.DYE_VAT_ITEM);

        CommonClass.JUICER_ITEM = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "juicer"),
                CommonClass.JUICER_ITEM_SUPPLIER.get()
        );
        registerPressingProperty(CommonClass.JUICER_ITEM);

        //--------------------------------Glass Tumbler-------------------------------------

        CommonClass.BAMBOO_FILLED_GLASS_TUMBLER = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "bamboo_filled_glass_tumbler"),
                CommonClass.BAMBOO_FILLED_GLASS_TUMBLER_SUPPLIER.get()
                );
        registerColorProperty(CommonClass.BAMBOO_FILLED_GLASS_TUMBLER);
        CommonClass.CHERRY_FILLED_GLASS_TUMBLER = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "cherry_filled_glass_tumbler"),
                CommonClass.CHERRY_FILLED_GLASS_TUMBLER_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.CHERRY_FILLED_GLASS_TUMBLER);
        CommonClass.CHORUS_FILLED_GLASS_TUMBLER = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "chorus_filled_glass_tumbler"),
                CommonClass.CHORUS_FILLED_GLASS_TUMBLER_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.CHORUS_FILLED_GLASS_TUMBLER);
        CommonClass.GLOW_BERRIES_FILLED_GLASS_TUMBLER = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "glow_berries_filled_glass_tumbler"),
                CommonClass.GLOW_BERRIES_FILLED_GLASS_TUMBLER_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.GLOW_BERRIES_FILLED_GLASS_TUMBLER);
        CommonClass.PUMPKIN_FILLED_GLASS_TUMBLER = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "pumpkin_filled_glass_tumbler"),
                CommonClass.PUMPKIN_FILLED_GLASS_TUMBLER_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.PUMPKIN_FILLED_GLASS_TUMBLER);
        CommonClass.MELON_FILLED_GLASS_TUMBLER = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "melon_filled_glass_tumbler"),
                CommonClass.MELON_FILLED_GLASS_TUMBLER_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.MELON_FILLED_GLASS_TUMBLER);
        CommonClass.SCULK_FILLED_GLASS_TUMBLER = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "sculk_filled_glass_tumbler"),
                CommonClass.SCULK_FILLED_GLASS_TUMBLER_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.SCULK_FILLED_GLASS_TUMBLER);
        CommonClass.MATCHA_FILLED_GLASS_TUMBLER = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "matcha_filled_glass_tumbler"),
                CommonClass.MATCHA_FILLED_GLASS_TUMBLER_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.MATCHA_FILLED_GLASS_TUMBLER);
        CommonClass.SWEET_BERRIES_FILLED_GLASS_TUMBLER = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "sweet_berries_filled_glass_tumbler"),
                CommonClass.SWEET_BERRIES_FILLED_GLASS_TUMBLER_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.SWEET_BERRIES_FILLED_GLASS_TUMBLER);
        CommonClass.APPLE_FILLED_GLASS_TUMBLER = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "apple_filled_glass_tumbler"),
                CommonClass.APPLE_FILLED_GLASS_TUMBLER_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.APPLE_FILLED_GLASS_TUMBLER);
        CommonClass.SPORE_BLOSSOM_FILLED_GLASS_TUMBLER = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "spore_blossom_filled_glass_tumbler"),
                CommonClass.SPORE_BLOSSOM_FILLED_GLASS_TUMBLER_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.SPORE_BLOSSOM_FILLED_GLASS_TUMBLER);
        CommonClass.POTION_FILLED_GLASS_TUMBLER = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "potion_filled_glass_tumbler"),
                CommonClass.POTION_FILLED_GLASS_TUMBLER_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.POTION_FILLED_GLASS_TUMBLER);

        //--------------------------------Split Coconut-------------------------------------

        CommonClass.BAMBOO_FILLED_SPLIT_COCONUT = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "bamboo_filled_split_coconut"),
                CommonClass.BAMBOO_FILLED_SPLIT_COCONUT_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.BAMBOO_FILLED_SPLIT_COCONUT);
        CommonClass.CHERRY_FILLED_SPLIT_COCONUT = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "cherry_filled_split_coconut"),
                CommonClass.CHERRY_FILLED_SPLIT_COCONUT_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.CHERRY_FILLED_SPLIT_COCONUT);
        CommonClass.CHORUS_FILLED_SPLIT_COCONUT = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "chorus_filled_split_coconut"),
                CommonClass.CHORUS_FILLED_SPLIT_COCONUT_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.CHORUS_FILLED_SPLIT_COCONUT);
        CommonClass.GLOW_BERRIES_FILLED_SPLIT_COCONUT = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "glow_berries_filled_split_coconut"),
                CommonClass.GLOW_BERRIES_FILLED_SPLIT_COCONUT_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.GLOW_BERRIES_FILLED_SPLIT_COCONUT);
        CommonClass.PUMPKIN_FILLED_SPLIT_COCONUT = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "pumpkin_filled_split_coconut"),
                CommonClass.PUMPKIN_FILLED_SPLIT_COCONUT_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.PUMPKIN_FILLED_SPLIT_COCONUT);
        CommonClass.MELON_FILLED_SPLIT_COCONUT = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "melon_filled_split_coconut"),
                CommonClass.MELON_FILLED_SPLIT_COCONUT_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.MELON_FILLED_SPLIT_COCONUT);
        CommonClass.SCULK_FILLED_SPLIT_COCONUT = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "sculk_filled_split_coconut"),
                CommonClass.SCULK_FILLED_SPLIT_COCONUT_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.SCULK_FILLED_SPLIT_COCONUT);
        CommonClass.MATCHA_FILLED_SPLIT_COCONUT = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "matcha_filled_split_coconut"),
                CommonClass.MATCHA_FILLED_SPLIT_COCONUT_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.MATCHA_FILLED_SPLIT_COCONUT);
        CommonClass.SWEET_BERRIES_FILLED_SPLIT_COCONUT = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "sweet_berries_filled_split_coconut"),
                CommonClass.SWEET_BERRIES_FILLED_SPLIT_COCONUT_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.SWEET_BERRIES_FILLED_SPLIT_COCONUT);
        CommonClass.APPLE_FILLED_SPLIT_COCONUT = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "apple_filled_split_coconut"),
                CommonClass.APPLE_FILLED_SPLIT_COCONUT_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.APPLE_FILLED_SPLIT_COCONUT);
        CommonClass.SPORE_BLOSSOM_FILLED_SPLIT_COCONUT = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "spore_blossom_filled_split_coconut"),
                CommonClass.SPORE_BLOSSOM_FILLED_SPLIT_COCONUT_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.SPORE_BLOSSOM_FILLED_SPLIT_COCONUT);
        CommonClass.POTION_FILLED_SPLIT_COCONUT = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "potion_filled_split_coconut"),
                CommonClass.POTION_FILLED_SPLIT_COCONUT_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.POTION_FILLED_SPLIT_COCONUT);

        //--------------------------------Soda Bottle-------------------------------------

        CommonClass.BAMBOO_FILLED_SODA_BOTTLE = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "bamboo_filled_soda_bottle"),
                CommonClass.BAMBOO_FILLED_SODA_BOTTLE_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.BAMBOO_FILLED_SODA_BOTTLE);
        CommonClass.CHERRY_FILLED_SODA_BOTTLE = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "cherry_filled_soda_bottle"),
                CommonClass.CHERRY_FILLED_SODA_BOTTLE_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.CHERRY_FILLED_SODA_BOTTLE);
        CommonClass.CHORUS_FILLED_SODA_BOTTLE = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "chorus_filled_soda_bottle"),
                CommonClass.CHORUS_FILLED_SODA_BOTTLE_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.CHORUS_FILLED_SODA_BOTTLE);
        CommonClass.GLOW_BERRIES_FILLED_SODA_BOTTLE = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "glow_berries_filled_soda_bottle"),
                CommonClass.GLOW_BERRIES_FILLED_SODA_BOTTLE_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.GLOW_BERRIES_FILLED_SODA_BOTTLE);
        CommonClass.PUMPKIN_FILLED_SODA_BOTTLE = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "pumpkin_filled_soda_bottle"),
                CommonClass.PUMPKIN_FILLED_SODA_BOTTLE_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.PUMPKIN_FILLED_SODA_BOTTLE);
        CommonClass.MELON_FILLED_SODA_BOTTLE = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "melon_filled_soda_bottle"),
                CommonClass.MELON_FILLED_SODA_BOTTLE_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.MELON_FILLED_SODA_BOTTLE);
        CommonClass.SCULK_FILLED_SODA_BOTTLE = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "sculk_filled_soda_bottle"),
                CommonClass.SCULK_FILLED_SODA_BOTTLE_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.SCULK_FILLED_SODA_BOTTLE);
        CommonClass.MATCHA_FILLED_SODA_BOTTLE = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "matcha_filled_soda_bottle"),
                CommonClass.MATCHA_FILLED_SODA_BOTTLE_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.MATCHA_FILLED_SODA_BOTTLE);
        CommonClass.SWEET_BERRIES_FILLED_SODA_BOTTLE = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "sweet_berries_filled_soda_bottle"),
                CommonClass.SWEET_BERRIES_FILLED_SODA_BOTTLE_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.SWEET_BERRIES_FILLED_SODA_BOTTLE);
        CommonClass.APPLE_FILLED_SODA_BOTTLE = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "apple_filled_soda_bottle"),
                CommonClass.APPLE_FILLED_SODA_BOTTLE_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.APPLE_FILLED_SODA_BOTTLE);
        CommonClass.SPORE_BLOSSOM_FILLED_SODA_BOTTLE = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "spore_blossom_filled_soda_bottle"),
                CommonClass.SPORE_BLOSSOM_FILLED_SODA_BOTTLE_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.SPORE_BLOSSOM_FILLED_SODA_BOTTLE);
        CommonClass.POTION_FILLED_SODA_BOTTLE = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "potion_filled_soda_bottle"),
                CommonClass.POTION_FILLED_SODA_BOTTLE_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.POTION_FILLED_SODA_BOTTLE);

        //--------------------------------Glass Can-------------------------------------

        CommonClass.BAMBOO_FILLED_GLASS_CAN = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "bamboo_filled_glass_can"),
                CommonClass.BAMBOO_FILLED_GLASS_CAN_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.BAMBOO_FILLED_GLASS_CAN);
        CommonClass.CHERRY_FILLED_GLASS_CAN = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "cherry_filled_glass_can"),
                CommonClass.CHERRY_FILLED_GLASS_CAN_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.CHERRY_FILLED_GLASS_CAN);
        CommonClass.CHORUS_FILLED_GLASS_CAN = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "chorus_filled_glass_can"),
                CommonClass.CHORUS_FILLED_GLASS_CAN_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.CHORUS_FILLED_GLASS_CAN);
        CommonClass.GLOW_BERRIES_FILLED_GLASS_CAN = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "glow_berries_filled_glass_can"),
                CommonClass.GLOW_BERRIES_FILLED_GLASS_CAN_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.GLOW_BERRIES_FILLED_GLASS_CAN);
        CommonClass.PUMPKIN_FILLED_GLASS_CAN = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "pumpkin_filled_glass_can"),
                CommonClass.PUMPKIN_FILLED_GLASS_CAN_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.PUMPKIN_FILLED_GLASS_CAN);
        CommonClass.MELON_FILLED_GLASS_CAN = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "melon_filled_glass_can"),
                CommonClass.MELON_FILLED_GLASS_CAN_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.MELON_FILLED_GLASS_CAN);
        CommonClass.SCULK_FILLED_GLASS_CAN = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "sculk_filled_glass_can"),
                CommonClass.SCULK_FILLED_GLASS_CAN_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.SCULK_FILLED_GLASS_CAN);
        CommonClass.MATCHA_FILLED_GLASS_CAN = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "matcha_filled_glass_can"),
                CommonClass.MATCHA_FILLED_GLASS_CAN_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.MATCHA_FILLED_GLASS_CAN);
        CommonClass.SWEET_BERRIES_FILLED_GLASS_CAN = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "sweet_berries_filled_glass_can"),
                CommonClass.SWEET_BERRIES_FILLED_GLASS_CAN_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.SWEET_BERRIES_FILLED_GLASS_CAN);
        CommonClass.APPLE_FILLED_GLASS_CAN = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "apple_filled_glass_can"),
                CommonClass.APPLE_FILLED_GLASS_CAN_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.APPLE_FILLED_GLASS_CAN);
        CommonClass.SPORE_BLOSSOM_FILLED_GLASS_CAN = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "spore_blossom_filled_glass_can"),
                CommonClass.SPORE_BLOSSOM_FILLED_GLASS_CAN_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.SPORE_BLOSSOM_FILLED_GLASS_CAN);
        CommonClass.POTION_FILLED_GLASS_CAN = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "potion_filled_glass_can"),
                CommonClass.POTION_FILLED_GLASS_CAN_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.POTION_FILLED_GLASS_CAN);

        //--------------------------------Cocktail Glass-------------------------------------

        CommonClass.BAMBOO_FILLED_COCKTAIL_GLASS = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "bamboo_filled_cocktail_glass"),
                CommonClass.BAMBOO_FILLED_COCKTAIL_GLASS_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.BAMBOO_FILLED_COCKTAIL_GLASS);
        CommonClass.CHERRY_FILLED_COCKTAIL_GLASS = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "cherry_filled_cocktail_glass"),
                CommonClass.CHERRY_FILLED_COCKTAIL_GLASS_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.CHERRY_FILLED_COCKTAIL_GLASS);
        CommonClass.CHORUS_FILLED_COCKTAIL_GLASS = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "chorus_filled_cocktail_glass"),
                CommonClass.CHORUS_FILLED_COCKTAIL_GLASS_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.CHORUS_FILLED_COCKTAIL_GLASS);
        CommonClass.GLOW_BERRIES_FILLED_COCKTAIL_GLASS = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "glow_berries_filled_cocktail_glass"),
                CommonClass.GLOW_BERRIES_FILLED_COCKTAIL_GLASS_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.GLOW_BERRIES_FILLED_COCKTAIL_GLASS);
        CommonClass.PUMPKIN_FILLED_COCKTAIL_GLASS = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "pumpkin_filled_cocktail_glass"),
                CommonClass.PUMPKIN_FILLED_COCKTAIL_GLASS_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.PUMPKIN_FILLED_COCKTAIL_GLASS);
        CommonClass.MELON_FILLED_COCKTAIL_GLASS = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "melon_filled_cocktail_glass"),
                CommonClass.MELON_FILLED_COCKTAIL_GLASS_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.MELON_FILLED_COCKTAIL_GLASS);
        CommonClass.SCULK_FILLED_COCKTAIL_GLASS = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "sculk_filled_cocktail_glass"),
                CommonClass.SCULK_FILLED_COCKTAIL_GLASS_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.SCULK_FILLED_COCKTAIL_GLASS);
        CommonClass.MATCHA_FILLED_COCKTAIL_GLASS = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "matcha_filled_cocktail_glass"),
                CommonClass.MATCHA_FILLED_COCKTAIL_GLASS_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.MATCHA_FILLED_COCKTAIL_GLASS);
        CommonClass.SWEET_BERRIES_FILLED_COCKTAIL_GLASS = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "sweet_berries_filled_cocktail_glass"),
                CommonClass.SWEET_BERRIES_FILLED_COCKTAIL_GLASS_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.SWEET_BERRIES_FILLED_COCKTAIL_GLASS);
        CommonClass.APPLE_FILLED_COCKTAIL_GLASS = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "apple_filled_cocktail_glass"),
                CommonClass.APPLE_FILLED_COCKTAIL_GLASS_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.APPLE_FILLED_COCKTAIL_GLASS);
        CommonClass.SPORE_BLOSSOM_FILLED_COCKTAIL_GLASS = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "spore_blossom_filled_cocktail_glass"),
                CommonClass.SPORE_BLOSSOM_FILLED_COCKTAIL_GLASS_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.SPORE_BLOSSOM_FILLED_COCKTAIL_GLASS);
        CommonClass.POTION_FILLED_COCKTAIL_GLASS = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "potion_filled_cocktail_glass"),
                CommonClass.POTION_FILLED_COCKTAIL_GLASS_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.POTION_FILLED_COCKTAIL_GLASS);

        //--------------------------------Glass Cup-------------------------------------

        CommonClass.BAMBOO_FILLED_GLASS_CUP = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "bamboo_filled_glass_cup"),
                CommonClass.BAMBOO_FILLED_GLASS_CUP_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.BAMBOO_FILLED_GLASS_CUP);
        CommonClass.CHERRY_FILLED_GLASS_CUP = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "cherry_filled_glass_cup"),
                CommonClass.CHERRY_FILLED_GLASS_CUP_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.CHERRY_FILLED_GLASS_CUP);
        CommonClass.CHORUS_FILLED_GLASS_CUP = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "chorus_filled_glass_cup"),
                CommonClass.CHORUS_FILLED_GLASS_CUP_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.CHORUS_FILLED_GLASS_CUP);
        CommonClass.GLOW_BERRIES_FILLED_GLASS_CUP = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "glow_berries_filled_glass_cup"),
                CommonClass.GLOW_BERRIES_FILLED_GLASS_CUP_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.GLOW_BERRIES_FILLED_GLASS_CUP);
        CommonClass.PUMPKIN_FILLED_GLASS_CUP = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "pumpkin_filled_glass_cup"),
                CommonClass.PUMPKIN_FILLED_GLASS_CUP_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.PUMPKIN_FILLED_GLASS_CUP);
        CommonClass.MELON_FILLED_GLASS_CUP = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "melon_filled_glass_cup"),
                CommonClass.MELON_FILLED_GLASS_CUP_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.MELON_FILLED_GLASS_CUP);
        CommonClass.SCULK_FILLED_GLASS_CUP = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "sculk_filled_glass_cup"),
                CommonClass.SCULK_FILLED_GLASS_CUP_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.SCULK_FILLED_GLASS_CUP);
        CommonClass.MATCHA_FILLED_GLASS_CUP = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "matcha_filled_glass_cup"),
                CommonClass.MATCHA_FILLED_GLASS_CUP_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.MATCHA_FILLED_GLASS_CUP);
        CommonClass.SWEET_BERRIES_FILLED_GLASS_CUP = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "sweet_berries_filled_glass_cup"),
                CommonClass.SWEET_BERRIES_FILLED_GLASS_CUP_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.SWEET_BERRIES_FILLED_GLASS_CUP);
        CommonClass.APPLE_FILLED_GLASS_CUP = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "apple_filled_glass_cup"),
                CommonClass.APPLE_FILLED_GLASS_CUP_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.APPLE_FILLED_GLASS_CUP);
        CommonClass.SPORE_BLOSSOM_FILLED_GLASS_CUP = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "spore_blossom_filled_glass_cup"),
                CommonClass.SPORE_BLOSSOM_FILLED_GLASS_CUP_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.SPORE_BLOSSOM_FILLED_GLASS_CUP);
        CommonClass.POTION_FILLED_GLASS_CUP = Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "potion_filled_glass_cup"),
                CommonClass.POTION_FILLED_GLASS_CUP_SUPPLIER.get()
        );
        registerColorProperty(CommonClass.POTION_FILLED_GLASS_CUP);

        //----------------------------------------------------------------------------------

        CommonClass.CUP_COLOR = Registry.register(
                BuiltInRegistries.DATA_COMPONENT_TYPE,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "cup_color"),
                CommonClass.CUP_COLOR_SUPPLIER.get()
        );

        CommonClass.WOODEN_COASTER = Registry.register(
                BuiltInRegistries.ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "wooden_coaster"),
                CommonClass.WOODEN_COASTER_SUPPLIER.get()
        );
        CommonClass.BAMBOO_COASTER = Registry.register(
                BuiltInRegistries.ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "bamboo_coaster"),
                CommonClass.BAMBOO_COASTER_SUPPLIER.get()
        );

        CommonClass.MIXING_CUP = Registry.register(
                BuiltInRegistries.ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "mixing_cup"),
                CommonClass.MIXING_CUP_SUPPLIER.get()
        );

        CommonClass.DYE_VAT = Registry.register(
                BuiltInRegistries.ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "dye_vat"),
                CommonClass.DYE_VAT_SUPPLIER.get()
        );

        CommonClass.JUICER = Registry.register(
                BuiltInRegistries.ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "juicer"),
                CommonClass.JUICER_SUPPLIER.get()
        );

        CommonClass.COCONUT = Registry.register(
                BuiltInRegistries.ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "coconut"),
                CommonClass.COCONUT_SUPPLIER.get()
        );

        //-------------------------------------Block------------------------------------------

        CommonClass.DECORATIVE_BAMBOO = Registry.register(
                BuiltInRegistries.BLOCK,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "decorative_bamboo"),
                CommonClass.DECORATIVE_BAMBOO_SUPPLIER.get()
        );
        Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "decorative_bamboo"),
                new BlockItem(CommonClass.DECORATIVE_BAMBOO, new Item.Properties())
        );

        CommonClass.INGREDIENT_SHELF = Registry.register(
                BuiltInRegistries.BLOCK,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "ingredient_shelf"),
                CommonClass.INGREDIENT_SHELF_SUPPLIER.get()
        );
        Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "ingredient_shelf"),
                new BlockItem(CommonClass.INGREDIENT_SHELF, new Item.Properties())
        );
        CommonClass.INGREDIENT_SHELF_BLOCK_ENTITY = Registry.register(
                BuiltInRegistries.BLOCK_ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "ingredient_shelf"),
                BlockEntityType.Builder.of(
                        IngredientShelfBlockEntity::new,
                        CommonClass.INGREDIENT_SHELF
                ).build(null)
        );

        CommonClass.BLUE_MIXING_PARTICLE = Registry.register(
                BuiltInRegistries.PARTICLE_TYPE,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "blue_mixing"),
                FabricParticleTypes.simple()
        );
        CommonClass.GREEN_MIXING_PARTICLE = Registry.register(
                BuiltInRegistries.PARTICLE_TYPE,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "green_mixing"),
                FabricParticleTypes.simple()
        );
        CommonClass.RED_MIXING_PARTICLE = Registry.register(
                BuiltInRegistries.PARTICLE_TYPE,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "red_mixing"),
                FabricParticleTypes.simple()
        );

        CommonClass.MIXING = Registry.register(
                BuiltInRegistries.SOUND_EVENT,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "mixing"),
                SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "mixing"))
        );
        CommonClass.POURING = Registry.register(
                BuiltInRegistries.SOUND_EVENT,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "pouring"),
                SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "pouring"))
        );
        CommonClass.DRINKING = Registry.register(
                BuiltInRegistries.SOUND_EVENT,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "drinking"),
                SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "drinking"))
        );

        //-------------------------------------------Loot---------------------------------------------------------------

        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            String path = key.location().toString();

            if (path.equals("minecraft:blocks/jungle_leaves")) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(CommonClass.COCONUT_ITEM))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f));

                tableBuilder.pool(poolBuilder.build());
            }

            if (path.startsWith("minecraft:chests/village/")) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(2))
                        .add(LootItem.lootTableItem(CommonClass.MATCHA_TEA_BAG)
                                .apply(SetItemCountFunction.setCount(
                                        UniformGenerator.between(1, 3)
                                ))
                        )
                        .when(LootItemRandomChanceCondition.randomChance(0.5f));

                tableBuilder.pool(poolBuilder.build());
            }
        });

        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (entity instanceof WanderingTrader trader) {
                trader.getOffers().add(new MerchantOffer(
                        new ItemCost(Items.EMERALD, 2),
                        new ItemStack(CommonClass.MATCHA_TEA_BAG, 1),
                        32, 2, 0.05f
                ));
            }
        });

        HudRenderCallback.EVENT.register(DrinkInfoOverlay::render);

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(content -> {
            content.accept(CommonClass.GLASS_TUMBLER);
            content.accept(CommonClass.SPLIT_COCONUT);
            content.accept(CommonClass.SODA_BOTTLE);
            content.accept(CommonClass.GLASS_CAN);
            content.accept(CommonClass.COCKTAIL_GLASS);
            content.accept(CommonClass.GLASS_CUP);
            content.accept(CommonClass.CHOCOLATE_MILK);
            content.accept(CommonClass.EMPTY_BOTTLE);
            content.accept(CommonClass.WATER_BOTTLE);
            content.accept(CommonClass.MILK_BOTTLE);
            content.accept(CommonClass.COCONUT_MILK_BOTTLE);
            content.accept(CommonClass.SPARKLING_WATER);
            content.accept(CommonClass.CHERRY_JUICE);
            content.accept(CommonClass.BAMBOO_MILK);
            content.accept(CommonClass.CHORUS_JUICE);
            content.accept(CommonClass.GLOW_BERRIES_JUICE);
            content.accept(CommonClass.SCULK_JUICE);
            content.accept(CommonClass.PUMPKIN_JUICE);
            content.accept(CommonClass.MELON_JUICE);
            content.accept(CommonClass.SWEET_BERRIES_JUICE);
            content.accept(CommonClass.APPLE_JUICE);
            content.accept(CommonClass.SPORE_BLOSSOM_JUICE);
            content.accept(CommonClass.MATCHA_TEA_BAG);
            content.accept(CommonClass.COCONUT_ITEM);

            content.accept(CommonClass.WOODEN_COASTER_ITEM);
            content.accept(CommonClass.BAMBOO_COASTER_ITEM);

            content.accept(CommonClass.MIXING_CUP_ITEM);
            content.accept(CommonClass.DYE_VAT_ITEM);
            content.accept(CommonClass.JUICER_ITEM);

            content.accept(CommonClass.BAMBOO_FILLED_GLASS_TUMBLER);
            content.accept(CommonClass.CHERRY_FILLED_GLASS_TUMBLER);
            content.accept(CommonClass.CHORUS_FILLED_GLASS_TUMBLER);
            content.accept(CommonClass.GLOW_BERRIES_FILLED_GLASS_TUMBLER);
            content.accept(CommonClass.PUMPKIN_FILLED_GLASS_TUMBLER);
            content.accept(CommonClass.MELON_FILLED_GLASS_TUMBLER);
            content.accept(CommonClass.SCULK_FILLED_GLASS_TUMBLER);
            content.accept(CommonClass.MATCHA_FILLED_GLASS_TUMBLER);
            content.accept(CommonClass.SWEET_BERRIES_FILLED_GLASS_TUMBLER);
            content.accept(CommonClass.APPLE_FILLED_GLASS_TUMBLER);
            content.accept(CommonClass.SPORE_BLOSSOM_FILLED_GLASS_TUMBLER);
            content.accept(CommonClass.POTION_FILLED_GLASS_TUMBLER);

            content.accept(CommonClass.BAMBOO_FILLED_SPLIT_COCONUT);
            content.accept(CommonClass.CHERRY_FILLED_SPLIT_COCONUT);
            content.accept(CommonClass.CHORUS_FILLED_SPLIT_COCONUT);
            content.accept(CommonClass.GLOW_BERRIES_FILLED_SPLIT_COCONUT);
            content.accept(CommonClass.PUMPKIN_FILLED_SPLIT_COCONUT);
            content.accept(CommonClass.MELON_FILLED_SPLIT_COCONUT);
            content.accept(CommonClass.SCULK_FILLED_SPLIT_COCONUT);
            content.accept(CommonClass.MATCHA_FILLED_SPLIT_COCONUT);
            content.accept(CommonClass.SWEET_BERRIES_FILLED_SPLIT_COCONUT);
            content.accept(CommonClass.APPLE_FILLED_SPLIT_COCONUT);
            content.accept(CommonClass.SPORE_BLOSSOM_FILLED_SPLIT_COCONUT);
            content.accept(CommonClass.POTION_FILLED_SPLIT_COCONUT);

            content.accept(CommonClass.BAMBOO_FILLED_SODA_BOTTLE);
            content.accept(CommonClass.CHERRY_FILLED_SODA_BOTTLE);
            content.accept(CommonClass.CHORUS_FILLED_SODA_BOTTLE);
            content.accept(CommonClass.GLOW_BERRIES_FILLED_SODA_BOTTLE);
            content.accept(CommonClass.PUMPKIN_FILLED_SODA_BOTTLE);
            content.accept(CommonClass.MELON_FILLED_SODA_BOTTLE);
            content.accept(CommonClass.SCULK_FILLED_SODA_BOTTLE);
            content.accept(CommonClass.MATCHA_FILLED_SODA_BOTTLE);
            content.accept(CommonClass.SWEET_BERRIES_FILLED_SODA_BOTTLE);
            content.accept(CommonClass.APPLE_FILLED_SODA_BOTTLE);
            content.accept(CommonClass.SPORE_BLOSSOM_FILLED_SODA_BOTTLE);
            content.accept(CommonClass.POTION_FILLED_SODA_BOTTLE);

            content.accept(CommonClass.BAMBOO_FILLED_GLASS_CAN);
            content.accept(CommonClass.CHERRY_FILLED_GLASS_CAN);
            content.accept(CommonClass.CHORUS_FILLED_GLASS_CAN);
            content.accept(CommonClass.GLOW_BERRIES_FILLED_GLASS_CAN);
            content.accept(CommonClass.PUMPKIN_FILLED_GLASS_CAN);
            content.accept(CommonClass.MELON_FILLED_GLASS_CAN);
            content.accept(CommonClass.SCULK_FILLED_GLASS_CAN);
            content.accept(CommonClass.MATCHA_FILLED_GLASS_CAN);
            content.accept(CommonClass.SWEET_BERRIES_FILLED_GLASS_CAN);
            content.accept(CommonClass.APPLE_FILLED_GLASS_CAN);
            content.accept(CommonClass.SPORE_BLOSSOM_FILLED_GLASS_CAN);
            content.accept(CommonClass.POTION_FILLED_GLASS_CAN);

            content.accept(CommonClass.BAMBOO_FILLED_COCKTAIL_GLASS);
            content.accept(CommonClass.CHERRY_FILLED_COCKTAIL_GLASS);
            content.accept(CommonClass.CHORUS_FILLED_COCKTAIL_GLASS);
            content.accept(CommonClass.GLOW_BERRIES_FILLED_COCKTAIL_GLASS);
            content.accept(CommonClass.PUMPKIN_FILLED_COCKTAIL_GLASS);
            content.accept(CommonClass.MELON_FILLED_COCKTAIL_GLASS);
            content.accept(CommonClass.SCULK_FILLED_COCKTAIL_GLASS);
            content.accept(CommonClass.MATCHA_FILLED_COCKTAIL_GLASS);
            content.accept(CommonClass.SWEET_BERRIES_FILLED_COCKTAIL_GLASS);
            content.accept(CommonClass.APPLE_FILLED_COCKTAIL_GLASS);
            content.accept(CommonClass.SPORE_BLOSSOM_FILLED_COCKTAIL_GLASS);
            content.accept(CommonClass.POTION_FILLED_COCKTAIL_GLASS);

            content.accept(CommonClass.BAMBOO_FILLED_GLASS_CUP);
            content.accept(CommonClass.CHERRY_FILLED_GLASS_CUP);
            content.accept(CommonClass.CHORUS_FILLED_GLASS_CUP);
            content.accept(CommonClass.GLOW_BERRIES_FILLED_GLASS_CUP);
            content.accept(CommonClass.PUMPKIN_FILLED_GLASS_CUP);
            content.accept(CommonClass.MELON_FILLED_GLASS_CUP);
            content.accept(CommonClass.SCULK_FILLED_GLASS_CUP);
            content.accept(CommonClass.MATCHA_FILLED_GLASS_CUP);
            content.accept(CommonClass.SWEET_BERRIES_FILLED_GLASS_CUP);
            content.accept(CommonClass.APPLE_FILLED_GLASS_CUP);
            content.accept(CommonClass.SPORE_BLOSSOM_FILLED_GLASS_CUP);
            content.accept(CommonClass.POTION_FILLED_GLASS_CUP);

            content.accept(CommonClass.DECORATIVE_BAMBOO);
            content.accept(CommonClass.INGREDIENT_SHELF);
        });
    }

    private void registerColorProperty(Item item) {
        ItemProperties.register(item,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "color"),
                (stack, level, entity, seed) -> {
                    CupColorComponent comp = stack.get(CommonClass.CUP_COLOR);
                    if (comp == null) return 0.0f;
                    return comp.color().ordinal() / (float) ColoredItem.Color.values().length;
                }
        );
    }
    private void registerMixingProperty(Item item) {
        ItemProperties.register(item,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "mixing"),
                (stack, level, entity, seed) -> {
                    CustomData data = stack.get(DataComponents.CUSTOM_DATA);

                    return data != null && data.copyTag().getBoolean("Mixing") ? 1.0f : 0.0f;
                }
        );
    }
    private void registerPressingProperty(Item item) {
        ItemProperties.register(item,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "pressing"),
                (stack, level, entity, seed) -> {
                    CustomData data = stack.get(DataComponents.CUSTOM_DATA);

                    return data != null && data.copyTag().getBoolean("Pressing") ? 1.0f : 0.0f;
                }
        );
    }
}
