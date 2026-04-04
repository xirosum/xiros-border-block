package com.xirosum.xiros.border.block.utils;

import com.xirosum.xiros.border.block.XirosBorderBlock;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class RewardLootTable {
    private final NavigableMap<Integer, Reward> map= new TreeMap<>();
    private final Random random = new Random();
    private int totalWeight = 0;
    public int stage = 0;

    public void addLoot(Reward reward, int weight) {
        if (weight <= 0 ) return;

        totalWeight += weight;
        map.put(totalWeight, reward);
    }

    public Reward roll() {
        XirosBorderBlock.LOGGER.info("loot table: {}", map);

        if (totalWeight <= 0) return null;
        int roll = random.nextInt(totalWeight) + 1;
        return map.ceilingEntry(roll).getValue();
    }
}
