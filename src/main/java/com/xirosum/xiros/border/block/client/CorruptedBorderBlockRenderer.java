package com.xirosum.xiros.border.block.client;

import com.xirosum.xiros.border.block.block.custom.CorruptedBorderBlock;
import com.xirosum.xiros.border.block.block.geo.block.entity.CorruptedBorderBlockEntity;
import com.xirosum.xiros.border.block.client.model.block.CorruptedBorderBlockModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class CorruptedBorderBlockRenderer extends GeoBlockRenderer<CorruptedBorderBlockEntity> {
    public CorruptedBorderBlockRenderer() {
        super(new CorruptedBorderBlockModel());
    }
}
