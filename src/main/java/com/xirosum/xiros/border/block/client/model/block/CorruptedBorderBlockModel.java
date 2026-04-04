package com.xirosum.xiros.border.block.client.model.block;

import com.xirosum.xiros.border.block.XirosBorderBlock;
import com.xirosum.xiros.border.block.block.geo.block.entity.CorruptedBorderBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class CorruptedBorderBlockModel extends DefaultedBlockGeoModel<CorruptedBorderBlockEntity> {
    public CorruptedBorderBlockModel() {
        super(new Identifier(XirosBorderBlock.MOD_ID, "corrupted_border_block"));
    }

}
