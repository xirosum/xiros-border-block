package com.xirosum.xiros.border.block.screen.hoarder.widgets;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.text.OrderedText;

import java.util.List;

public class ItemEntry extends ElementListWidget.Entry<ItemEntry> {
    private OrderedText text;
    private int indent;
    private boolean updateTextEntry = false;
    private final TextRenderer textRenderer;


    public ItemEntry(OrderedText text, int indent, TextRenderer textRenderer) {
        this.textRenderer = textRenderer;
        this.text = text;
        this.indent = indent;
    }

    @Override
    public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        context.drawTextWithShadow(textRenderer, text, x + indent, y, 0xFFFFFF);
    }

    @Override
    public List<? extends Selectable> selectableChildren() {
        return List.of();
    }

    @Override
    public List<? extends Element> children() {
        return List.of();
    }


}
