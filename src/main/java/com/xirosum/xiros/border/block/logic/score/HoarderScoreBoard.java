package com.xirosum.xiros.border.block.logic.score;

import com.xirosum.xiros.border.block.XirosBorderBlock;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;

public class HoarderScoreBoard {
    private final MinecraftServer SERVER;
    private final String OBJECTIVE_NAME = "hoarder_score";
    private final String DISPLAY_NAME = "Hoarder Score";

    public HoarderScoreBoard(MinecraftServer SERVER) {
        this.SERVER = SERVER;
    }

    public void addHoarderToScoreboard() {
        SERVER.execute(() -> {
            if (SERVER.getScoreboard().getObjective(OBJECTIVE_NAME) == null) {
                SERVER.getScoreboard().addObjective(OBJECTIVE_NAME, ScoreboardCriterion.DUMMY, Text.of(DISPLAY_NAME), ScoreboardCriterion.RenderType.INTEGER);
            }
        });
    }

    public void updatePlayerScore(String playerName, int score) {
        SERVER.execute(() -> {
            if (SERVER.getScoreboard().getObjective(OBJECTIVE_NAME) != null) {
                ScoreboardPlayerScore playerScore = SERVER.getScoreboard().getPlayerScore(playerName, SERVER.getScoreboard().getObjective(OBJECTIVE_NAME));
                playerScore.setScore(score);
                SERVER.getScoreboard().updateScore(playerScore);

                XirosBorderBlock.LOGGER.info("Updated score for player {}: {}", playerName, score);
            }
        });
    }

    public boolean scoreboardActive() {
        return SERVER.getScoreboard().getObjective(OBJECTIVE_NAME) != null;
    }

    public void setScoreboardDisplayPosition(int slot) {
        SERVER.getScoreboard().setObjectiveSlot(slot, SERVER.getScoreboard().getObjective(OBJECTIVE_NAME)); // 1 is the sidebar
    }

}
