package com.xirosum.xiros.border.block.logic;

import com.xirosum.xiros.border.block.XirosBorderBlock;
import com.xirosum.xiros.border.block.logic.persistance.HoarderData;
import com.xirosum.xiros.border.block.logic.score.CompletionPercentage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;


public class Hoarder {
    private final HoarderData data = XirosBorderBlock.hoarderData;
    private final int BORDER_INCREASE_AMOUNT = 2;

    // one way to do it might be useful to use callbacks for block mined or entered inventory
    public void registerNewItem(PlayerEntity player, ItemStack itemStack, World world) {
        if (!XirosBorderBlock.hoarderData.isActive()) {
            return;
        }

        // Check the player's inventory for specific items and update the block's state accordingly
        if (data.foundItems().contains(itemStack.getItem().toString())) {
            XirosBorderBlock.LOGGER.info("Player {} found item {} but it has already been found before, no border increase", player.getName(), itemStack.getItem());
            return;
        }

        // If foundItems does not contain the item, append and increase border size
        data.foundItems().add(itemStack.getItem().toString());
        data.playerItemCounts().put(player.getUuidAsString(), data.playerItemCounts().getOrDefault(player.getUuidAsString(), 0) + 1);

        displayFoundItems(player, itemStack.getItem().toString());

        increaseBorder(world, itemStack.getItem().toString());
    }

    private void increaseBorder(World world, String item) {
        if (AchievementItems.achievementItems.containsKey(item)) {
            XirosBorderBlock.LOGGER.debug("Player found achievement item {}, increasing border size by 5", item);
            world.getWorldBorder().setSize(world.getWorldBorder().getSize() + AchievementItems.achievementItems.get(item));
            XirosBorderBlock.LOGGER.debug("World border size increased to {} due to player finding achievement item {}", world.getWorldBorder().getSize(), item);
        } else {
            world.getWorldBorder().setSize(world.getWorldBorder().getSize() + BORDER_INCREASE_AMOUNT);
            XirosBorderBlock.LOGGER.debug("World border size increased to {} due to player finding new item", world.getWorldBorder().getSize());
        }
    }

    public void clear() {
        data.foundItems().clear();
        data.playerItemCounts().clear();

        XirosBorderBlock.LOGGER.info("Hoarder data cleared, all found items and player counts reset");
    }

    public void displayCompletion(PlayerEntity player) {
        displayToAllPlayers(player, CompletionPercentage.getCompletion());
    }

    private void displayFoundItems(PlayerEntity player, String item) {
        // Display the found items to the players
        // this may only display to players in the same world, but that should be fine for now
        for (PlayerEntity p : player.getWorld().getPlayers()) {
            p.sendMessage(Text.of("Player " + player.getName() + " found a new item: " + item), false);
        }
    }

    private void displayToAllPlayers(PlayerEntity player, Text text) {
        for (PlayerEntity p : player.getWorld().getPlayers()) {
            p.sendMessage(text, false);
        }
    }

}
