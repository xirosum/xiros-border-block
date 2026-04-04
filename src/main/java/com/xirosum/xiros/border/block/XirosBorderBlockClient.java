package com.xirosum.xiros.border.block;

import com.xirosum.xiros.border.block.screen.BorderBlockScreen;
import com.xirosum.xiros.border.block.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class XirosBorderBlockClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.BORDER_BLOCK_SCREEN_HANDLER, BorderBlockScreen::new);
    }
}

