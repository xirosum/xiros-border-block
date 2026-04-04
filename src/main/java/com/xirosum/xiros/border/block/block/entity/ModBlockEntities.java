package com.xirosum.xiros.border.block.block.entity;

import com.xirosum.xiros.border.block.XirosBorderBlock;
import com.xirosum.xiros.border.block.block.ModBlocks;
import com.xirosum.xiros.border.block.block.geo.block.entity.CorruptedBorderBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<BorderBlockEntity> BORDER_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(XirosBorderBlock.MOD_ID, "border_be"),
                    FabricBlockEntityTypeBuilder.create(BorderBlockEntity::new, ModBlocks.BORDER_BLOCK).build());

    public static final BlockEntityType<CorruptedBorderBlockEntity> CORRUPTED_BORDER_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(XirosBorderBlock.MOD_ID, "corrupted_border_be"),
                    FabricBlockEntityTypeBuilder.create(CorruptedBorderBlockEntity::new, ModBlocks.CORRUPTED_BORDER_BLOCK).build());

    public static void registerBlockEntities() {
        XirosBorderBlock.LOGGER.info("Registering block entities for: " + XirosBorderBlock.MOD_ID);
    }
}
