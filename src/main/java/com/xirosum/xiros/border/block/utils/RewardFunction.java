package com.xirosum.xiros.border.block.utils;

import com.xirosum.xiros.border.block.XirosBorderBlock;
import com.xirosum.xiros.border.block.config.BorderBlockConfigAccess;
import net.minecraft.world.World;

import java.util.List;

/**
 *  Get the reward for the current stage
 */
public class RewardFunction {

    private static List<Reward> getRewards(World world) {
        if (world == null) {return List.of();}

        BorderBlockStage stage = StageUtil.getStage((int) world.getWorldBorder().getSize());
        List<BorderBlockStage> allStages = BorderBlockConfigAccess.get().stages;

        List<Reward> rewards = new java.util.ArrayList<>(List.of());

        for (BorderBlockStage iteratorStage: allStages) {
            if (iteratorStage.stageId() <= stage.stageId()) {
                rewards.addAll(iteratorStage.lootTable());
            }
        }

        return rewards;
    }

    public  static void setLootTable(World world) {
        if (world == null) return;

        int borderSize = (int) world.getWorldBorder().getSize();

        int stageId = StageUtil.getStage(borderSize).stageId();

        XirosBorderBlock.LOGGER.info("global stageId: {}, localStageId {}", XirosBorderBlock.rewardLootTable.stage, stageId);

        if (XirosBorderBlock.rewardLootTable.stage == stageId && XirosBorderBlock.rewardLootTable.roll() != null) return;


        List<Reward> rewards = getRewards(world);
        RewardLootTable rewardLootTable = new RewardLootTable();

        for (Reward reward: rewards) {
            rewardLootTable.addLoot(reward, reward.weight());
        }

        rewardLootTable.stage = stageId;

        XirosBorderBlock.rewardLootTable = rewardLootTable;
    }

}
