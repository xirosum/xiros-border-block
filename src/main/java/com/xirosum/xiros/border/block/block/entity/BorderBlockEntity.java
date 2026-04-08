package com.xirosum.xiros.border.block.block.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class BorderBlockEntity extends BaseBorderBlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    public BorderBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BORDER_BLOCK_ENTITY, pos, state);
    }
}
