package com.xirosum.xiros.border.block.utils;

import com.xirosum.xiros.border.block.config.BorderBlockConfigAccess;

import java.util.Comparator;
import java.util.List;

public class StageUtil {
    public static BorderBlockStage getStage(int currentBorderSize) {
        List<BorderBlockStage> stages = BorderBlockConfigAccess.get().stages;

        for (BorderBlockStage stage: stages ) {
            if (stage.inStage(currentBorderSize)) {
                return stage;
            }
        }

        return stages.get(stages.size() - 1);
    }
}
