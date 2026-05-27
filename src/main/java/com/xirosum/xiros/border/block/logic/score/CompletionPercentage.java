package com.xirosum.xiros.border.block.logic.score;

import com.xirosum.xiros.border.block.XirosBorderBlock;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;

public class CompletionPercentage {
    private static double calculate(long foundBlocks, long totalBlocks) {
        if (totalBlocks == 0) {
            return 0.0; // Avoid division by zero
        }
        return (double) foundBlocks / totalBlocks * 100;
    }

    private static Text completionText(int foundBlocks, int totalBlocks) {
        double percentage = calculate(foundBlocks, totalBlocks);
        return Text.of(String.format("Completion: %.2f%% (%d/%d)", percentage, foundBlocks, totalBlocks));
    }

    public static Text getCompletion() {
        long block_count = Registries.BLOCK.stream().count();
        long items_count = Registries.ITEM.stream().count();
        long total_count = block_count + items_count;

        double percentage = calculate(XirosBorderBlock.hoarderData.foundItems().size(), total_count);

        return completionText(XirosBorderBlock.hoarderData.foundItems().size(), (int) total_count);
    }
}
