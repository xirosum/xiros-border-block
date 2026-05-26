package com.xirosum.xiros.border.block.logic;

import com.xirosum.xiros.border.block.XirosBorderBlock;
import com.xirosum.xiros.border.block.logic.persistance.HoarderData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;



public class Hoarder {

    private static final HoarderData stats = XirosBorderBlock.hoarderData;

    // one way to do it might be useful to use callbacks for block mined or entered inventory
    public static void trigger(PlayerEntity player, ItemStack itemStack, World world) {
        // Check the player's inventory for specific items and update the block's state accordingly
        if (stats.foundItems().contains(itemStack.toString())) {
            return;
        }

        // If foundItems does not contain the item, append and increase border size
        stats.foundItems().add(itemStack.toString());
        stats.playerItemCounts().put(player.getUuidAsString(), stats.playerItemCounts().getOrDefault(player.getUuidAsString(), 0) + 1);

        displayFoundItems(player, itemStack.toString());

        world.getWorldBorder().setSize(world.getWorldBorder().getSize() + 1);
        XirosBorderBlock.LOGGER.info("World border size increased to {} due to player finding new item {}", world.getWorldBorder().getSize(), itemStack);
    }

    public static void saveFoundItems() {
        // Save the found items to a file or database
    }

    public static void loadFoundItems() {
        // Load the found items from a file or database
    }

    public static void clearFoundItems() {
        stats.foundItems().clear();
    }

    public static void displayFoundItems(PlayerEntity player, String item) {
        // Display the found items to the player
        player.sendMessage(Text.of("Found items: " + String.join(", ", item)), false);
    }
}
