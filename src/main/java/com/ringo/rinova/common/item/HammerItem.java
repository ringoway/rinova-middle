package com.ringo.rinova.common.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class HammerItem extends PickaxeItem {
    public HammerItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Item.Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state,
                             BlockPos centerPos, LivingEntity miner) {
        super.mineBlock(stack, level, state, centerPos, miner);

        if (!level.isClientSide && !miner.isShiftKeyDown()) {
            Direction facing = miner.getDirection();
            int brokenCount = 0;

            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    if (x == 0 && y == 0) continue; // Пропускаем центр

                    BlockPos targetPos = getTargetPos(centerPos, facing, x, y);
                    BlockState targetState = level.getBlockState(targetPos);

                    // Упрощённая проверка блока
                    if (!targetState.isAir() && targetState.getDestroySpeed(level, targetPos) > 0) {
                        level.destroyBlock(targetPos, true, miner);
                        brokenCount++;
                    }
                }
            }
            stack.hurt(brokenCount, level.getRandom(), null);
        }
        return true;
    }

    private BlockPos getTargetPos(BlockPos center, Direction facing, int offsetX, int offsetY) {
        // Определяем базовые направления
        Direction.Axis axis = facing.getAxis();
        boolean isVertical = axis == Direction.Axis.Y;

        // Определяем направления для смещения
        Direction rightDir = isVertical ?
                Direction.EAST : // Для UP/DOWN смещаем по X
                facing.getClockWise(); // Для горизонтальных - поворот по часовой

        Direction upDir = isVertical ?
                Direction.NORTH : // Для UP/DOWN смещаем по Z
                Direction.UP; // Для горизонтальных - смещаем по Y

        // Рассчитываем итоговое смещение
        int xOffset = rightDir.getStepX() * offsetX + upDir.getStepX() * offsetY;
        int yOffset = rightDir.getStepY() * offsetX + upDir.getStepY() * offsetY;
        int zOffset = rightDir.getStepZ() * offsetX + upDir.getStepZ() * offsetY;

        return center.offset(xOffset, yOffset, zOffset);
    }
}
