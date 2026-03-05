package io.github.velaliilunalii.cozy_cafe.item;

import io.github.velaliilunalii.cozy_cafe.CommonClass;
import io.github.velaliilunalii.cozy_cafe.entity.JuicerEntity;
import io.github.velaliilunalii.cozy_cafe.entity.MixingCupEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import static io.github.velaliilunalii.cozy_cafe.util.ItemUtil.hasEntityInside;

public class JuicerItem extends Item {
    public JuicerItem(Properties properties) {
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

            pos = context.getPlayer().isCrouching() ? pos : new Vec3(((double)
                    Math.round((pos.x - (double) 2 /16) * 4) /4) + (double) 2 /16,
                    pos.y, ((double)
                    Math.round((pos.z - (double) 2 /16) * 4) /4) + (double) 2 /16);            JuicerEntity juicerEntity = new JuicerEntity(CommonClass.JUICER, level, pos, angle);
            if(hasEntityInside(juicerEntity)){
                juicerEntity.discard();
                return InteractionResult.FAIL;
            }
            level.addFreshEntity(juicerEntity);

            if (!context.getPlayer().isCreative()) {
                context.getItemInHand().shrink(1);
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
