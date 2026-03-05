package io.github.velaliilunalii.cozy_cafe.block;

import com.mojang.serialization.MapCodec;
import io.github.velaliilunalii.cozy_cafe.block.entity.IngredientShelfBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class IngredientShelfBlock extends BaseEntityBlock {
    public static final MapCodec<IngredientShelfBlock> CODEC = simpleCodec(IngredientShelfBlock::new);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    //TODO
    protected static final VoxelShape SHAPE = Block.box((double)0.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)8.0F, (double)16.0F);

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING});
    }

    public IngredientShelfBlock(Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState)(this.defaultBlockState().setValue(FACING, Direction.NORTH)));
    }

    //TODO
    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof IngredientShelfBlockEntity ingredientShelfBlockEntity) {
            if (!level.isClientSide){
                ItemStack itemstack = player.getItemInHand(hand);
                boolean handEmpty = player.getItemInHand(hand).isEmpty();
                boolean isCrouching = player.isCrouching();

                if(handEmpty){
                    if(isCrouching){
                        if(ingredientShelfBlockEntity.takeItem(player)) return ItemInteractionResult.SUCCESS;
                    }else {
                        if(ingredientShelfBlockEntity.takeAll(player)) return ItemInteractionResult.SUCCESS;
                    }
                }else{
                    if(ingredientShelfBlockEntity.placeAll(player, itemstack)) return ItemInteractionResult.SUCCESS;
                }
            }
            return ItemInteractionResult.CONSUME;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    //TODO
    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof IngredientShelfBlockEntity) {
                Containers.dropContents(level, pos, ((IngredientShelfBlockEntity)blockentity).getItems());
            }

            super.onRemove(state, level, pos, newState, isMoving);
        }

    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getHorizontalDirection();
        return (BlockState)(this.defaultBlockState().setValue(FACING, direction));
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rot) {
        return (BlockState)state.setValue(FACING, rot.rotate((Direction)state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
    }

    //TODO
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new IngredientShelfBlockEntity(blockPos, blockState);
    }

    //TODO
    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return super.getTicker(level, state, blockEntityType);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}
