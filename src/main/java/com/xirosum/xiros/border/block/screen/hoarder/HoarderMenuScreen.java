package com.xirosum.xiros.border.block.screen.hoarder;

import com.xirosum.xiros.border.block.screen.hoarder.widgets.ItemEntry;
import com.xirosum.xiros.border.block.screen.hoarder.widgets.ItemListWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;

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

        TextWidget foundBlocksTitleWidget = new TextWidget(width/4, 40, width/20, height/40, Text.literal("Found Blocks"), minecraftClient.textRenderer);
        titleWidget.alignCenter();
        TextWidget missingBlocksTitleWidget = new TextWidget( 3 * (width/4), 40, width/20, height/40, Text.literal("Missing Blocks"), minecraftClient.textRenderer);
        titleWidget.alignCenter();

        int listWidth = (width / 2);
        int leftListX = 0;
        int rightListX = width / 2;
        int listTop = 60;

        ItemListWidget foundItemsListWidget = new ItemListWidget(minecraftClient, leftListX, listWidth, height, listTop, height - 5, 26);
        foundItemsListWidget.addBlockEntry(new ItemEntry(new ItemStack(Items.STONE), minecraftClient.textRenderer));
        foundItemsListWidget.addBlockEntry(new ItemEntry(new ItemStack(Items.OAK_PLANKS), minecraftClient.textRenderer));
        foundItemsListWidget.addBlockEntry(new ItemEntry(new ItemStack(Items.IRON_INGOT), minecraftClient.textRenderer));
        foundItemsListWidget.addBlockEntry(new ItemEntry(new ItemStack(Items.DIAMOND), minecraftClient.textRenderer));

        ItemListWidget missingItemsListWidget = new ItemListWidget(minecraftClient, rightListX, listWidth, height, listTop, height - 5, 26);
        missingItemsListWidget.addBlockEntry(new ItemEntry(new ItemStack(Items.REDSTONE), minecraftClient.textRenderer));
        missingItemsListWidget.addBlockEntry(new ItemEntry(new ItemStack(Items.GOLDEN_APPLE), minecraftClient.textRenderer));
        missingItemsListWidget.addBlockEntry(new ItemEntry(new ItemStack(Items.OBSIDIAN), minecraftClient.textRenderer));
        missingItemsListWidget.addBlockEntry(new ItemEntry(new ItemStack(Items.ENDER_PEARL), minecraftClient.textRenderer));

        addDrawable(titleWidget);
        addDrawable(foundBlocksTitleWidget);
        addDrawable(missingBlocksTitleWidget);
        addDrawable(foundItemsListWidget);
        addDrawable(missingItemsListWidget);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        this.renderBackground(context);
        // render background texture actually blocks out the screen


    }


}
