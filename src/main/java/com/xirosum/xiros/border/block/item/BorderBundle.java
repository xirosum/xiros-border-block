package com.xirosum.xiros.border.block.item;

/**
 *  Inspiration: https://github.com/fonnymunkey/SimpleHats/blob/Fabric-1.20.1/src/main/java/fonnymunkey/simplehats/common/item/BagItem.java
 */

import com.xirosum.xiros.border.block.XirosBorderBlock;
import com.xirosum.xiros.border.block.config.BorderBlockConfigAccess;
import com.xirosum.xiros.border.block.utils.RewardFunction;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BorderBundle extends Item {
    public BorderBundle(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        itemStack.decrement(1);

        if (world.isClient) return TypedActionResult.success(itemStack);

        /**
         * Configs should allow for something like simplyswords:sword
         * Should check to see if it can be loaded if not catch the error and give dirt.
         * May need to validate Blocks and other types of Registries but for now only items are fine.
         */

        RewardFunction.setLootTable(world);

        String reward = XirosBorderBlock.rewardLootTable.roll().getReward();

        Item item = Registries.ITEM.get(new Identifier(reward));
        player.dropItem(item);

        XirosBorderBlock.LOGGER.debug("Config stuff: {}", BorderBlockConfigAccess.get().stages);

        return TypedActionResult.success(itemStack);
    }
}
