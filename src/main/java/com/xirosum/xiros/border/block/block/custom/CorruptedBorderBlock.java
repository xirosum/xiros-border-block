package com.xirosum.xiros.border.block.block.custom;

import com.xirosum.xiros.border.block.block.geo.block.entity.CorruptedBorderBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class CorruptedBorderBlock extends BlockWithEntity implements BlockEntityProvider {
    public CorruptedBorderBlock(Settings settings) {
        super(settings);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CorruptedBorderBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        // BlockRenderType = RenderShape in mojmap
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(0, 0, 0, 16, 16, 16);
    }

}
