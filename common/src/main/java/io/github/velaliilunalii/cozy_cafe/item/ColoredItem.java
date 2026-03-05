package io.github.velaliilunalii.cozy_cafe.item;

import com.mojang.serialization.Codec;
import io.github.velaliilunalii.cozy_cafe.CommonClass;
import io.github.velaliilunalii.cozy_cafe.component.CupColorComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class ColoredItem extends Item {
    public enum Color implements StringRepresentable {
        DEFAULT("default", Items.AIR),
        BLACK("black", Items.BLACK_DYE),
        BLUE("blue", Items.BLUE_DYE),
        BROWN("brown", Items.BROWN_DYE),
        CYAN("cyan", Items.CYAN_DYE),
        GRAY("gray", Items.GRAY_DYE),
        GREEN("green", Items.GREEN_DYE),
        LIGHT_BLUE("light_blue", Items.LIGHT_BLUE_DYE),
        LIGHT_GRAY("light_gray", Items.LIGHT_GRAY_DYE),
        LIME("lime", Items.LIME_DYE),
        MAGENTA("magenta", Items.MAGENTA_DYE),
        ORANGE("orange", Items.ORANGE_DYE),
        PINK("pink", Items.PINK_DYE),
        PURPLE("purple", Items.PURPLE_DYE),
        RED("red", Items.RED_DYE),
        WHITE("white", Items.WHITE_DYE),
        YELLOW("yellow", Items.YELLOW_DYE);

        public static final Codec<Color> CODEC = StringRepresentable.fromEnum(Color::values);

        private final String name;
        private final Item item;

        Color(String name, Item item) {
            this.name = name;
            this.item = item;
        }

        public static Optional<Color> getColorFromValues(ItemStack itemStack){
            for(Color color : values()){
                if (itemStack.is(color.item)) return Optional.of(color);
            }
            return Optional.empty();
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }

    public static void setColor(ItemStack stack, Color color) {
        stack.set(CommonClass.CUP_COLOR, new CupColorComponent(color));
    }

    public static Color getColor(ItemStack stack) {
        CupColorComponent comp = stack.get(CommonClass.CUP_COLOR);
        return comp != null ? comp.color() : Color.DEFAULT;
    }

    public ColoredItem(Properties properties) {
        super(properties);
    }
}
