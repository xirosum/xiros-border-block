package com.xirosum.xiros.border.block.screen.hoarder;

import com.xirosum.xiros.border.block.XirosBorderBlock;
import com.xirosum.xiros.border.block.logic.persistance.HoarderData;
import com.xirosum.xiros.border.block.screen.hoarder.widgets.ItemEntry;
import com.xirosum.xiros.border.block.screen.hoarder.widgets.ItemListWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class HoarderMenuScreen extends Screen {
    public HoarderMenuScreen(Text title) {
        super(title);
    }


    @Override
    protected void init() {
        super.init();
        var minecraftClient = this.client;
        if (minecraftClient == null) {
            return;
        }

        TextWidget titleWidget = new TextWidget(width/2, 20, width/20, height/40, Text.literal("Hoarder Menu"), minecraftClient.textRenderer);
        titleWidget.alignCenter();

        TextWidget foundBlocksTitleWidget = new TextWidget(width/4, 40, width/20, height/40, Text.literal("Found Items"), minecraftClient.textRenderer);
        titleWidget.alignCenter();
        TextWidget missingBlocksTitleWidget = new TextWidget( 3 * (width/4), 40, width/20, height/40, Text.literal("Missing Items"), minecraftClient.textRenderer);
        titleWidget.alignCenter();

        int listWidth = (width / 2);
        int leftListX = 0;
        int rightListX = width / 2;
        int listTop = 60;

        HoarderData hoarderData = XirosBorderBlock.hoarderData;

        ItemListWidget foundItemsListWidget = new ItemListWidget(minecraftClient, leftListX, listWidth, height, listTop, height - 5, 26);
        // Add found items
        for (String foundItemId : hoarderData.foundItems()) {
            try {
                Identifier id = new Identifier(foundItemId);
                var item = Registries.ITEM.get(id);
                if (item != null) {
                    foundItemsListWidget.addBlockEntry(new ItemEntry(new ItemStack(item), minecraftClient.textRenderer));
                }
            } catch (Exception e) {
                XirosBorderBlock.LOGGER.warn("Failed to load found item: {}", foundItemId, e);
            }
        }

        ItemListWidget missingItemsListWidget = new ItemListWidget(minecraftClient, rightListX, listWidth, height, listTop, height - 5, 26);
        // Add missing items (all items not in foundItems)
        for (var entry : Registries.ITEM.getEntrySet()) {
            String itemId = entry.getKey().getValue().toString();
            if (!hoarderData.foundItems().contains(itemId)) {
                missingItemsListWidget.addBlockEntry(new ItemEntry(new ItemStack(entry.getValue()), minecraftClient.textRenderer));
            }
        }

        addDrawable(titleWidget);
        addDrawable(foundBlocksTitleWidget);
        addDrawable(missingBlocksTitleWidget);
        addDrawableChild(foundItemsListWidget);
        addDrawableChild(missingItemsListWidget);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        this.renderBackground(context);
        // render background texture actually blocks out the screen




    }


}
