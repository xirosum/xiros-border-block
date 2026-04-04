package com.xirosum.xiros.border.block;

import com.xirosum.xiros.border.block.block.ModBlocks;
import com.xirosum.xiros.border.block.block.entity.ModBlockEntities;
import com.xirosum.xiros.border.block.config.BorderBlockConfigAccess;
import com.xirosum.xiros.border.block.item.ModItems;
import com.xirosum.xiros.border.block.screen.ModScreenHandlers;
import com.xirosum.xiros.border.block.utils.RewardLootTable;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XirosBorderBlock implements ModInitializer {
	public static final String MOD_ID = "xiros-border-block";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static RewardLootTable rewardLootTable = new RewardLootTable();

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		// Load config early
		BorderBlockConfigAccess.bootstrap();

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerBlockEntities();
		ModScreenHandlers.registerScreenHandlers();
	}
}