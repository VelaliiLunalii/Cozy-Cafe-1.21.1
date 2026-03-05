package io.github.velaliilunalii.cozy_cafe.component;

import io.github.velaliilunalii.cozy_cafe.food.DrinkIngredient;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class DrinkIngredientInstance {
    private DrinkIngredient drinkIngredient;
    private ItemStack itemStack;

    public DrinkIngredientInstance(DrinkIngredient drinkIngredient){
        this.drinkIngredient = drinkIngredient;
        this.itemStack = null;
    }

    public DrinkIngredientInstance(DrinkIngredient drinkIngredient, @Nullable ItemStack itemStack){
        this.drinkIngredient = drinkIngredient;
        this.itemStack = itemStack;
    }

    public DrinkIngredient getDrinkIngredient() {
        return drinkIngredient;
    }

    public void setDrinkIngredient(DrinkIngredient drinkIngredient) {
        this.drinkIngredient = drinkIngredient;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
}
