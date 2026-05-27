package com.xirosum.xiros.border.block.screen.hoarder;

import com.xirosum.xiros.border.block.screen.hoarder.widgets.ItemEntry;
import com.xirosum.xiros.border.block.screen.hoarder.widgets.ItemListWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;

public class HoarderMenuScreen extends Screen {
    public HoarderMenuScreen(Text title) {
        super(title);
    }


    @Override
    protected void init() {
        super.init();

        TextWidget titleWidget = new TextWidget(width/2, 20, width/20, height/40, Text.literal("Hoarder Menu"), client.textRenderer);
        titleWidget.alignCenter();

        TextWidget foundBlocksTitleWidget = new TextWidget(width/4, 40, width/20, height/40, Text.literal("Found Blocks"), client.textRenderer);
        titleWidget.alignCenter();
        TextWidget missingBlocksTitleWidget = new TextWidget( 3 * (width/4), 40, width/20, height/40, Text.literal("Missing Blocks"), client.textRenderer);
        titleWidget.alignCenter();

        ItemListWidget itemListWidget = new ItemListWidget(client, width, height, 40, height - 40, 20);
        OrderedText entryText = OrderedText.of(Text.literal("Example Block").asOrderedText());
        itemListWidget.addBlockEntry(new ItemEntry(entryText, 0, client.textRenderer));
        itemListWidget.addBlockEntry(new ItemEntry(entryText, 0, client.textRenderer));
        itemListWidget.addBlockEntry(new ItemEntry(entryText, 0, client.textRenderer));
        itemListWidget.addBlockEntry(new ItemEntry(entryText, 0, client.textRenderer));
        itemListWidget.addBlockEntry(new ItemEntry(entryText, 0, client.textRenderer));
        itemListWidget.addBlockEntry(new ItemEntry(entryText, 0, client.textRenderer));
        itemListWidget.addBlockEntry(new ItemEntry(entryText, 0, client.textRenderer));
        itemListWidget.addBlockEntry(new ItemEntry(entryText, 0, client.textRenderer));


        addDrawable(titleWidget);
        addDrawable(foundBlocksTitleWidget);
        addDrawable(missingBlocksTitleWidget);
        addDrawable(itemListWidget);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        this.renderBackground(context);
        // render background texture actually blocks out the screen


    }


}
