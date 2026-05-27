package com.xirosum.xiros.border.block.screen.hoarder.widgets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.EntryListWidget;

public class ItemListWidget extends EntryListWidget<ItemEntry> {
    private static final int SCROLLBAR_PADDING = 6;
    private static final int ROW_HORIZONTAL_PADDING = 10;
    private final int leftX;
    private final int listWidth;

    public ItemListWidget(MinecraftClient client, int x, int width, int height, int top, int bottom, int itemHeight) {
        super(client, width, height, top, bottom, itemHeight);
        this.leftX = x;
        this.listWidth = width;
        this.setLeftPos(x);
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {

    }

    public void addBlockEntry(ItemEntry entry) {
        this.addEntry(entry);
    }

    public void clearEntries() {
        super.clearEntries();
    }

    @Override
    protected int getScrollbarPositionX() {
        // Keep the scrollbar anchored to this list's right edge.
        return this.leftX + this.listWidth - SCROLLBAR_PADDING;
    }

    @Override
    public int getRowWidth() {
        // Reserve a little room so row content does not collide with the scrollbar.
        return this.listWidth - ROW_HORIZONTAL_PADDING;
    }

}
