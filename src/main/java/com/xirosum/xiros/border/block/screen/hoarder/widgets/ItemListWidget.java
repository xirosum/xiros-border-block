package com.xirosum.xiros.border.block.screen.hoarder.widgets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.EntryListWidget;

public class ItemListWidget extends EntryListWidget<ItemEntry> {

    private final TextRenderer textRenderer;

    public ItemListWidget(MinecraftClient client, int width, int height, int top, int bottom, int itemHeight) {
        super(client, width, height, top, bottom, itemHeight);

        this.textRenderer = client.textRenderer;
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {

    }

    public void addBlockEntry(ItemEntry entry) {
        this.addEntry(entry);
    }

}
