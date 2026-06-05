/* (C)2026 */
package com.xirosum.xiros.border.block.logic;

import com.xirosum.xiros.border.block.XirosBorderBlock;
import com.xirosum.xiros.border.block.logic.persistance.HoarderData;
import com.xirosum.xiros.border.block.logic.score.CompletionPercentage;
import com.xirosum.xiros.border.block.logic.unobtainable.UnobtainableItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class Hoarder {
    private final HoarderData data = XirosBorderBlock.hoarderData;
    private final int BORDER_INCREASE_AMOUNT = 2;

    // one way to do it might be useful to use callbacks for block mined or entered inventory
    public void registerNewItem(PlayerEntity player, ItemStack itemStack) {
        if (!XirosBorderBlock.hoarderData.isActive()) {
            return;
        }

        Identifier itemId = Registries.ITEM.getId(itemStack.getItem());
        String itemIdStr = itemId.toString();

        // Check the player's inventory for specific items and update the block's state accordingly
        if (!data.addFoundItem(itemIdStr)) {
            XirosBorderBlock.LOGGER.debug(
                    "Player {} found item {} but it has already been found before, no border"
                            + " increase",
                    player.getName(),
                    itemIdStr);
            return;
        }

        data.incrementPlayerItemCount(player.getUuidAsString());

        displayFoundItems(player, itemIdStr);

        increaseBorder(player.getWorld(), itemIdStr);

        if (XirosBorderBlock.hoarderScoreBoard.scoreboardActive()) {
            XirosBorderBlock.hoarderScoreBoard.updatePlayerScore(
                    player.getName().getString(),
                    data.playerItemCounts().get(player.getUuidAsString()));
        }
    }

    private void increaseBorder(World world, String item) {
        if (AchievementItems.achievementItems.containsKey(item)) {
            XirosBorderBlock.LOGGER.debug(
                    "Player found achievement item {}, increasing border size by 5", item);
            world.getWorldBorder()
                    .setSize(
                            world.getWorldBorder().getSize()
                                    + AchievementItems.achievementItems.get(item));
            XirosBorderBlock.LOGGER.debug(
                    "World border size increased to {} due to player finding achievement item {}",
                    world.getWorldBorder().getSize(),
                    item);
        } else {
            world.getWorldBorder()
                    .setSize(world.getWorldBorder().getSize() + BORDER_INCREASE_AMOUNT);
            XirosBorderBlock.LOGGER.debug(
                    "World border size increased to {} due to player finding new item",
                    world.getWorldBorder().getSize());
        }
    }

    public void clear() {
        data.clearProgress();

        XirosBorderBlock.LOGGER.info(
                "Hoarder data cleared, all found items and player counts reset");
    }

    public void displayCompletion(PlayerEntity player) {
        displayToAllPlayers(player, CompletionPercentage.getCompletion());
    }

    private void displayFoundItems(PlayerEntity player, String item) {
        displayToAllPlayers(
                player,
                Text.of("Player " + player.getName().getString() + " found a new item: " + item));
    }

    public void displayScore(PlayerEntity player) {
        int foundItems = data.foundItems().size();
        int totalItems = (int) Registries.ITEM.stream().count();
        double percentage = (double) foundItems / totalItems * 100;

        displayToAllPlayers(
                player,
                Text.of(
                        "Player "
                                + player.getName().getString()
                                + " has found "
                                + foundItems
                                + " out of "
                                + totalItems
                                + " items ("
                                + String.format("%.2f", percentage)
                                + "%)"));
    }

    private void displayToAllPlayers(PlayerEntity player, Text text) {
        XirosBorderBlock.serverShared.getPlayerManager().broadcast(text, false);
    }
}
