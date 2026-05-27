package com.xirosum.xiros.border.block.screen.hoarder.widgets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.EntryListWidget;

public class ItemListWidget extends EntryListWidget<ItemEntry> {

    public ItemListWidget(MinecraftClient client, int x, int width, int height, int top, int bottom, int itemHeight) {
        super(client, width, height, top, bottom, itemHeight);
        this.setLeftPos(x);
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {

    }

    public void addBlockEntry(ItemEntry entry) {
        this.addEntry(entry);
    }


}
