package com.xirosum.xiros.border.block.utils;

import com.xirosum.xiros.border.block.config.BorderBlockConfigAccess;

import java.util.List;
import java.util.Random;

public record Reward (List<String> rewards, int weight) {

    public String getReward () {
        if (rewards.isEmpty()) {
            return BorderBlockConfigAccess.get().defaultReward;
        }

        Random random = new Random();
        return rewards.get(random.nextInt(rewards().size()));
    }
}
