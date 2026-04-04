package com.xirosum.xiros.border.block.utils;

import java.util.List;

public record BorderBlockStage(int stageId, int startingBorderSize, int endingBorderSize, String requiredItem, int rewardMultiplier, int expansionMultiplier, List<Reward> lootTable) {

    public boolean inStage(int currentBorderSize) {
        return currentBorderSize > startingBorderSize && currentBorderSize <= endingBorderSize;
    }

}
