package io.github.velaliilunalii.cozy_cafe.entity.coaster;

import io.github.velaliilunalii.cozy_cafe.item.ColoredItem;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class CoasterEntity extends Entity {
    private static final EntityDataAccessor<ItemStack> DATA_ITEM =
            SynchedEntityData.defineId(CoasterEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<Integer> DATA_COLOR =
            SynchedEntityData.defineId(CoasterEntity.class, EntityDataSerializers.INT);

    private double lerpX;
    private double lerpY;
    private double lerpZ;
    private double lerpYRot;
    private double lerpXRot;
    private int lerpSteps;

    public CoasterEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.blocksBuilding = true;
    }

    public CoasterEntity(EntityType<?> entityType, Level level, Vec3 pos, float angle, ColoredItem.Color color) {
        this(entityType, level);
        this.setPos(pos);
        this.setYRot(angle);
        this.setDataColor(color);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_ITEM, ItemStack.EMPTY);
        builder.define(DATA_COLOR, 0);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide && this.lerpSteps > 0) {
            this.lerpPositionAndRotationStep(
                    this.lerpSteps,
                    this.lerpX,
                    this.lerpY,
                    this.lerpZ,
                    this.lerpYRot,
                    this.lerpXRot
            );
            this.lerpSteps--;
        }

        if (!this.level().isClientSide) {
            this.xOld = this.getX();
            this.yOld = this.getY();
            this.zOld = this.getZ();

            Vec3 motion = this.getDeltaMovement();

            if (!this.isNoGravity()) {
                motion = motion.add(0.0, -0.04, 0.0);
            }

            this.setDeltaMovement(motion);
            this.move(net.minecraft.world.entity.MoverType.SELF, this.getDeltaMovement());

            if (this.onGround()) {
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.9, 0.0, 0.9));
            }
        }
    }

    @Override
    public void lerpTo(double x, double y, double z, float yRot, float xRot, int steps) {
        this.lerpX = x;
        this.lerpY = y;
        this.lerpZ = z;
        this.lerpYRot = yRot;
        this.lerpXRot = xRot;
        this.lerpSteps = steps;
    }

    @Override
    protected void lerpPositionAndRotationStep(int steps, double x, double y, double z, double yRot, double xRot) {
        double invSteps = 1.0 / (double) steps;

        double newX = Mth.lerp(invSteps, this.getX(), x);
        double newY = Mth.lerp(invSteps, this.getY(), y);
        double newZ = Mth.lerp(invSteps, this.getZ(), z);

        float newYRot = (float) Mth.rotLerp(invSteps, this.getYRot(), yRot);
        float newXRot = (float) Mth.lerp(invSteps, this.getXRot(), xRot);

        this.setPos(newX, newY, newZ);
        this.setRot(newYRot, newXRot);
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return EntityDimensions.scalable(4.0F / 16.0F, 1.0F / 16.0F);
    }

    public ItemStack getDisplayedItem() {
        return this.entityData.get(DATA_ITEM);
    }

    public void setDisplayedItem(ItemStack stack) {
        this.entityData.set(DATA_ITEM, stack.copy());
    }

    public ColoredItem.Color getDataColor() {
        int intColor = this.entityData.get(DATA_COLOR);
        if (intColor < 0 || intColor >= ColoredItem.Color.values().length) {
            return ColoredItem.Color.DEFAULT;
        }
        return ColoredItem.Color.values()[intColor];
    }

    public void setDataColor(ColoredItem.Color color) {
        this.entityData.set(DATA_COLOR, color.ordinal());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains("Item", 10)) {
            ItemStack stack = ItemStack.parse(this.registryAccess(), tag.getCompound("Item"))
                    .orElse(ItemStack.EMPTY);
            this.setDisplayedItem(stack);
        }
        if (tag.contains("Color", 8)) {
            String colorName = tag.getString("Color");
            try {
                ColoredItem.Color color = ColoredItem.Color.valueOf(colorName);
                this.setDataColor(color);
            } catch (IllegalArgumentException e) {
                this.setDataColor(ColoredItem.Color.DEFAULT);
            }
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        if (!this.getDisplayedItem().isEmpty()) {
            tag.put("Item", this.getDisplayedItem().save(this.registryAccess()));
        }
        tag.putString("Color", this.getDataColor().name());
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isRemoved();
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    public boolean skipAttackInteraction(Entity attacker) {
        if (attacker instanceof Player player) {
            return !this.level().mayInteract(player, this.blockPosition());
        }
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getSourcePosition() != null) {
            Vec3 delta = this.position().subtract(source.getSourcePosition());
            delta = delta.multiply(1, 0, 1).normalize().scale(0.4);
            Vec3 motion = this.getDeltaMovement();
            this.setDeltaMovement(motion.add(delta));
        }
        return false;
    }

    public boolean remove(DamageSource source){
        if (this.isInvulnerableTo(source) || this.isRemoved()) {
            return false;
        }

        if (!this.level().isClientSide) {
            this.playSound(SoundEvents.WOOD_BREAK, 1.0F, 1.0F);

            ItemStack displayed = this.getDisplayedItem();
            if (!displayed.isEmpty()) {
                ItemEntity itemEntity = new ItemEntity(
                        this.level(),
                        this.getX(),
                        this.getY() + 0.2,
                        this.getZ(),
                        displayed.copy()
                );
                itemEntity.setDefaultPickUpDelay();
                this.level().addFreshEntity(itemEntity);
            }

            this.dropCoasterItem();

            this.markHurt();
            this.discard();
        }

        return true;
    }

    public void dropCoasterItem() {

    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        ItemStack handStack = player.getItemInHand(hand);
        ItemStack displayedStack = this.getDisplayedItem();

        if (!this.level().isClientSide) {
            if (player.isShiftKeyDown()) {
                this.remove(this.damageSources().playerAttack(player));
                return InteractionResult.SUCCESS;
            } else if (!handStack.isEmpty() && displayedStack.isEmpty()) {
                ItemStack toDisplay = handStack.copy();
                toDisplay.setCount(1);
                this.setDisplayedItem(toDisplay);

                if (!player.isCreative()) {
                    handStack.shrink(1);
                }

                this.playSound(SoundEvents.ITEM_FRAME_ADD_ITEM, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            } else if (!displayedStack.isEmpty() && handStack.isEmpty()) {
                player.setItemInHand(hand, displayedStack.copy());
                this.setDisplayedItem(ItemStack.EMPTY);

                this.playSound(SoundEvents.ITEM_FRAME_REMOVE_ITEM, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            } else if (!displayedStack.isEmpty() && !handStack.isEmpty()) {
                ItemStack temp = displayedStack.copy();

                ItemStack toDisplay = handStack.copy();
                toDisplay.setCount(1);
                this.setDisplayedItem(toDisplay);

                if (!player.isCreative()) {
                    handStack.shrink(1);
                }

                player.getInventory().add(temp);

                this.playSound(SoundEvents.ITEM_FRAME_ROTATE_ITEM, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.sidedSuccess(this.level().isClientSide);
    }

    @Override
    public void push(double x, double y, double z) {}

    @Override
    protected void checkFallDamage(double y, boolean onGround, net.minecraft.world.level.block.state.BlockState state, BlockPos pos) {}
}