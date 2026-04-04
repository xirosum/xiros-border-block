package com.xirosum.xiros.border.block.screen;

import com.xirosum.xiros.border.block.XirosBorderBlock;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;


public class ModScreenHandlers {
    public static final ScreenHandlerType<BorderBlockScreenHandler> BORDER_BLOCK_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(XirosBorderBlock.MOD_ID, "border_block"),
                    new ExtendedScreenHandlerType<>(BorderBlockScreenHandler::new));

    public static void registerScreenHandlers() {
        XirosBorderBlock.LOGGER.info("Registering Screen Handlers for: " + XirosBorderBlock.MOD_ID);}

}
