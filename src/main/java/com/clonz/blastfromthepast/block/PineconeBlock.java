package com.clonz.blastfromthepast.block;

import com.clonz.blastfromthepast.init.ModBlocks;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.neoforge.common.util.TriState;

public class PineconeBlock extends SaplingBlock {
    public static final MapCodec<PineconeBlock> CODEC = RecordCodecBuilder.mapCodec((p_308831_) -> p_308831_.group(TreeGrower.CODEC.fieldOf("tree").forGetter((p_304527_) -> p_304527_.treeGrower), propertiesCodec()).apply(p_308831_, PineconeBlock::new));
    public static final BooleanProperty HANGING;

    public MapCodec<PineconeBlock> codec() {
        return CODEC;
    }

    public PineconeBlock(TreeGrower treeGrower, BlockBehaviour.Properties properties) {
        super(treeGrower, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HANGING, false));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{STAGE}).add(HANGING);
    }

    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return super.mayPlaceOn(state, level, pos) || state.is(Blocks.CLAY);
    }

    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if (isHanging(state)) {
            TriState soilDecision = level.getBlockState(pos.above()).canSustainPlant(level, pos.above(), Direction.DOWN, state);
            return !soilDecision.isDefault() ? soilDecision.isTrue() : level.getBlockState(pos.above()).is(ModBlocks.CEDAR.LEAVES);
        } else {
            return super.canSurvive(state, level, pos);
        }
    }

    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return !isHanging(state);
    }

    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return !isHanging(state) && super.isBonemealSuccess(level, random, pos, state);
    }

    private static boolean isHanging(BlockState state) {
        return state.getValue(HANGING);
    }

    static {
        HANGING = BlockStateProperties.HANGING;
    }
}

