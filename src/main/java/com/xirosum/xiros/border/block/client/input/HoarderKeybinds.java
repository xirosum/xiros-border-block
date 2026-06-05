/* (C)2026 */
package com.xirosum.xiros.border.block.client.input;

import com.xirosum.xiros.border.block.network.RequestHoarderDataPacket;
import com.xirosum.xiros.border.block.screen.hoarder.HoarderMenuScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public final class HoarderKeybinds {
    private static final String KEY_CATEGORY = "key.categories.xiros-border-block";
    private static final String OPEN_HOARDER_MENU_KEY = "key.xiros-border-block.open_hoarder_menu";
    private static final String HOARDER_MENU_TITLE = "screen.xiros-border-block.hoarder_menu";

    private static final KeyBinding OPEN_MENU =
            KeyBindingHelper.registerKeyBinding(
                    new KeyBinding(
                            OPEN_HOARDER_MENU_KEY,
                            InputUtil.Type.KEYSYM,
                            GLFW.GLFW_KEY_H,
                            KEY_CATEGORY));

    private HoarderKeybinds() {}

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(
                client -> {
                    while (OPEN_MENU.wasPressed()) {
                        if (client.player == null) {
                            continue;
                        }

                        // Request the hoarder data from the server
                        ClientPlayNetworking.send(new RequestHoarderDataPacket());

                        // Open the screen after requesting data
                        client.setScreen(
                                new HoarderMenuScreen(Text.translatable(HOARDER_MENU_TITLE)));
                    }
                });
    }
}
