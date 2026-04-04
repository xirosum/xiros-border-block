package com.xirosum.xiros.border.block.block;

import com.xirosum.xiros.border.block.XirosBorderBlock;
import com.xirosum.xiros.border.block.block.custom.BorderBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block BORDER_BLOCK = registerBlock(("border_block"), new BorderBlock(FabricBlockSettings.copyOf(Blocks.BEDROCK).nonOpaque()));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);

        return Registry.register(Registries.BLOCK, new Identifier(XirosBorderBlock.MOD_ID, name), block);
    }

    public static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(XirosBorderBlock.MOD_ID, name), new BlockItem(block, new FabricItemSettings()));
    }

    public static void addItemsToRedstoneTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(BORDER_BLOCK);
    }

    public static void registerModBlocks() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(ModBlocks::addItemsToRedstoneTabItemGroup);
    }
}
