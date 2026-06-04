/* (C)2026 */
package com.xirosum.xiros.border.block.screen.hoarder.widgets;

import java.util.List;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ItemEntry extends ElementListWidget.Entry<ItemEntry> {
    private final ItemStack stack;
    private final Text itemName;
    private final Text modLine;
    private final TextRenderer textRenderer;

    public ItemEntry(ItemStack stack, TextRenderer textRenderer) {
        this.textRenderer = textRenderer;
        this.stack = stack.copy();
        this.stack.setCount(1);
        this.itemName = this.stack.getName();

        Identifier itemId = Registries.ITEM.getId(this.stack.getItem());
        String namespace = itemId.getNamespace();
        String modName =
                FabricLoader.getInstance()
                        .getModContainer(namespace)
                        .map(container -> container.getMetadata().getName())
                        .orElse(namespace);
        this.modLine = Text.literal(modName + " (" + namespace + ")");
    }

    @Override
    public void render(
            DrawContext context,
            int index,
            int y,
            int x,
            int entryWidth,
            int entryHeight,
            int mouseX,
            int mouseY,
            boolean hovered,
            float tickDelta) {
        int iconX = x + 2;
        int iconY = y + 2;
        int textX = iconX + 20;

        context.drawItem(stack, iconX, iconY);
        context.drawTextWithShadow(textRenderer, itemName, textX, y + 2, 0xFFFFFF);
        context.drawTextWithShadow(textRenderer, modLine, textX, y + 12, 0xA0A0A0);
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
