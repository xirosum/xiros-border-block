package com.xirosum.xiros.border.block.config;


import com.xirosum.xiros.border.block.XirosBorderBlock;
import com.xirosum.xiros.border.block.utils.BorderBlockStage;
import com.xirosum.xiros.border.block.utils.Reward;
import infinituum.fastconfigapi.api.annotations.FastConfig;
import infinituum.fastconfigapi.api.annotations.Loader;

import java.util.List;

@FastConfig(subdirectory = XirosBorderBlock.MOD_ID, fileName = "xiros-border-block-config", loader = @Loader(type = Loader.Type.DEFAULT))
public class BorderBlockConfig {
    public List<BorderBlockStage> stages = List.of(
            new BorderBlockStage(0, 0, 1000, "minecraft:diamond", 1, 1,
                    List.of( new Reward(List.of("minecraft:acacia_sapling"), 10))));
    public String defaultReward = "minecraft:dirt";
}
