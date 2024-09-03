package com.clonz.blastfromthepast.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class CustomLogBlock extends RotatedPillarBlock {
    private final @Nullable Supplier<? extends RotatedPillarBlock> stripped;
    private final boolean isFlammable;
    private final int flammability;
    private final int fireSpreadSpeed;

    public CustomLogBlock(Properties properties, @Nullable Supplier<? extends RotatedPillarBlock> stripped, boolean isFlammable, int flammability, int fireSpreadSpeed) {
        super(properties);
        this.stripped = stripped;
        this.isFlammable = isFlammable;
        this.flammability = flammability;
        this.fireSpreadSpeed = fireSpreadSpeed;
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return isFlammable;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return flammability;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return fireSpreadSpeed;
    }

    @Override
    public @Nullable BlockState getToolModifiedState(@NotNull BlockState state, @NotNull UseOnContext context, @NotNull ItemAbility toolAction, boolean simulate) {
        if (stripped == null) return super.getToolModifiedState(state, context, toolAction, simulate);
        return toolAction == ItemAbilities.AXE_STRIP ? stripped.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)) : null;
    }
}
