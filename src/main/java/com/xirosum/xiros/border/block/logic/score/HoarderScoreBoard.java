package com.xirosum.xiros.border.block.logic.score;

import com.xirosum.xiros.border.block.XirosBorderBlock;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;

public class HoarderScoreBoard {
    private MinecraftServer server;
    private final String OBJECTIVE_NAME = "hoarder_score";
    private final String DISPLAY_NAME = "Hoarder Score";

    public HoarderScoreBoard(MinecraftServer server) {
        this.server = server;
    }

    public void addHoarderToScoreboard() {
        server.execute(() -> {
            if (server.getScoreboard().getObjective(OBJECTIVE_NAME) == null) {
                server.getScoreboard().addObjective(OBJECTIVE_NAME, ScoreboardCriterion.DUMMY, Text.of(DISPLAY_NAME), ScoreboardCriterion.RenderType.INTEGER);
            }
        });
    }

    public void updatePlayerScore(String playerName, int score) {
        server.execute(() -> {
            if (server.getScoreboard().getObjective(OBJECTIVE_NAME) != null) {
                ScoreboardPlayerScore playerScore = server.getScoreboard().getPlayerScore(playerName, server.getScoreboard().getObjective(OBJECTIVE_NAME));
                playerScore.setScore(score);
                server.getScoreboard().updateScore(playerScore);

                XirosBorderBlock.LOGGER.info("Updated score for player {}: {}", playerName, score);
            }
        });
    }

    public boolean scoreboardActive() {
        return server.getScoreboard().getObjective(OBJECTIVE_NAME) != null;
    }

    public void setScoreboardDisplayPosition() {
        server.getScoreboard().setObjectiveSlot(1, server.getScoreboard().getObjective(OBJECTIVE_NAME)); // 1 is the sidebar
    }

}
