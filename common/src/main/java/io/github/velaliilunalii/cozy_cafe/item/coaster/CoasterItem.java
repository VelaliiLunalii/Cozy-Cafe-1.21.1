package io.github.velaliilunalii.cozy_cafe.item.coaster;

import io.github.velaliilunalii.cozy_cafe.CommonClass;
import io.github.velaliilunalii.cozy_cafe.entity.coaster.CoasterEntity;
import io.github.velaliilunalii.cozy_cafe.item.ColoredItem;
import io.github.velaliilunalii.cozy_cafe.component.CupColorComponent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import static io.github.velaliilunalii.cozy_cafe.util.ItemUtil.hasEntityInside;

public class CoasterItem extends ColoredItem {
    public CoasterItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Vec3 pos = context.getClickLocation();
        BlockPos blockPos = context.getClickedPos();
        Direction facing = context.getClickedFace();
        float angle = context.getRotation();

        if (facing != Direction.UP) {
            return InteractionResult.FAIL;
        }

        if (!level.isClientSide) {
            BlockPos placePos = blockPos.above();

            if (!level.getBlockState(placePos).isAir()) {
                return InteractionResult.FAIL;
            }

            CupColorComponent comp = context.getItemInHand().get(CommonClass.CUP_COLOR);
            Color color = comp == null ? Color.DEFAULT : comp.color();
            pos = context.getPlayer().isCrouching() ? pos : new Vec3(((double)
                    Math.round((pos.x - (double) 2 /16) * 4) /4) + (double) 2 /16,
                    pos.y, ((double)
                    Math.round((pos.z - (double) 2 /16) * 4) /4) + (double) 2 /16);
            CoasterEntity coaster = getAssociatedEntity(level, pos, angle, color);
            if (hasEntityInside(coaster)) {
                coaster.discard();
                return InteractionResult.FAIL;
            }
            level.addFreshEntity(coaster);

            if (!context.getPlayer().isCreative()) {
                context.getItemInHand().shrink(1);
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    public CoasterEntity getAssociatedEntity(Level level, Vec3 pos, float angle, Color color){
        return null;
    }
}
