/* (C)2026 */
package com.xirosum.xiros.border.block.screen.hoarder;

import com.xirosum.xiros.border.block.XirosBorderBlock;
import com.xirosum.xiros.border.block.client.HoarderDataCache;
import com.xirosum.xiros.border.block.screen.hoarder.widgets.ItemEntry;
import com.xirosum.xiros.border.block.screen.hoarder.widgets.ItemListWidget;
import java.util.HashSet;
import java.util.Set;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class HoarderMenuScreen extends Screen {
    private ItemListWidget foundItemsListWidget;
    private ItemListWidget missingItemsListWidget;
    private TextFieldWidget searchBox;
    private String lastSearchQuery = "";
    private long lastDataRevision = -1L;
    private int foundCount = 0;
    private int missingCount = 0;

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

        TextWidget titleWidget =
                new TextWidget(
                        width / 2,
                        20,
                        width / 20,
                        height / 40,
                        Text.literal("Hoarder Menu"),
                        minecraftClient.textRenderer);
        titleWidget.alignCenter();

        // These will be updated dynamically with counts
        TextWidget foundBlocksTitleWidget =
                new TextWidget(
                        width / 4,
                        40,
                        width / 20,
                        height / 40,
                        Text.literal("Found Items"),
                        minecraftClient.textRenderer);
        titleWidget.alignCenter();
        TextWidget missingBlocksTitleWidget =
                new TextWidget(
                        3 * (width / 4),
                        40,
                        width / 20,
                        height / 40,
                        Text.literal("Missing Items"),
                        minecraftClient.textRenderer);
        titleWidget.alignCenter();

        int listWidth = (width / 2);
        int leftListX = 0;
        int rightListX = width / 2;
        int listTop = 60;
        int searchBoxHeight = 20;

        foundItemsListWidget =
                new ItemListWidget(
                        minecraftClient,
                        leftListX,
                        listWidth,
                        height,
                        listTop,
                        height - searchBoxHeight - 10,
                        26);
        missingItemsListWidget =
                new ItemListWidget(
                        minecraftClient,
                        rightListX,
                        listWidth,
                        height,
                        listTop,
                        height - searchBoxHeight - 10,
                        26);

        searchBox =
                new TextFieldWidget(
                        minecraftClient.textRenderer,
                        width / 4 - 50,
                        height - searchBoxHeight - 5,
                        100,
                        searchBoxHeight,
                        Text.literal("Search"));
        searchBox.setMaxLength(100);
        searchBox.setPlaceholder(Text.literal("Search items..."));

        // Populate lists with data
        lastDataRevision = HoarderDataCache.getRevision();
        populateLists();

        addDrawable(titleWidget);
        addDrawable(foundBlocksTitleWidget);
        addDrawable(missingBlocksTitleWidget);
        addDrawableChild(foundItemsListWidget);
        addDrawableChild(missingItemsListWidget);
        addDrawableChild(searchBox);
    }

    private void populateLists() {
        if (client == null) return;

        // Use cached client-side data instead of server data
        var foundItems = HoarderDataCache.getFoundItems();
        Set<String> foundItemsSet = new HashSet<>(foundItems);
        String searchQuery = searchBox.getText().toLowerCase();

        // Clear existing entries
        foundItemsListWidget.clearEntries();
        missingItemsListWidget.clearEntries();

        // Add found items
        for (String foundItemId : foundItems) {
            try {
                Identifier id = new Identifier(foundItemId);
                var item = Registries.ITEM.get(id);
                if (matchesSearch(item, foundItemId, searchQuery)) {
                    foundItemsListWidget.addBlockEntry(
                            new ItemEntry(new ItemStack(item), client.textRenderer));
                }
            } catch (Exception e) {
                XirosBorderBlock.LOGGER.warn("Failed to load found item: {}", foundItemId, e);
            }
        }

        // Add missing items (all items not in foundItems)
        for (var entry : Registries.ITEM.getEntrySet()) {
            String itemId = entry.getKey().getValue().toString();
            if (!foundItemsSet.contains(itemId)
                    && matchesSearch(entry.getValue(), itemId, searchQuery)) {
                missingItemsListWidget.addBlockEntry(
                        new ItemEntry(new ItemStack(entry.getValue()), client.textRenderer));
            }
        }

        // Update counts
        foundCount = foundItemsListWidget.children().size();
        missingCount = missingItemsListWidget.children().size();
    }

    private boolean matchesSearch(net.minecraft.item.Item item, String itemId, String searchQuery) {
        if (searchQuery.isEmpty()) {
            return true;
        }

        // Search by item display name
        String itemName = item.getName(new ItemStack(item)).getString().toLowerCase();
        if (itemName.contains(searchQuery)) {
            return true;
        }

        // Search by item ID
        if (itemId.toLowerCase().contains(searchQuery)) {
            return true;
        }

        // Search by mod name
        String namespace = new Identifier(itemId).getNamespace();
        String modName =
                FabricLoader.getInstance()
                        .getModContainer(namespace)
                        .map(container -> container.getMetadata().getName())
                        .orElse(namespace);
        return modName.toLowerCase().contains(searchQuery);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (searchBox == null) {
            super.render(context, mouseX, mouseY, delta);
            return;
        }

        long currentRevision = HoarderDataCache.getRevision();
        if (currentRevision != lastDataRevision) {
            lastDataRevision = currentRevision;
            populateLists();
        }

        // Check if search box text has changed
        String currentSearch = searchBox.getText();
        if (!currentSearch.equals(lastSearchQuery)) {
            lastSearchQuery = currentSearch;
            populateLists();
        }

        super.render(context, mouseX, mouseY, delta);
        this.renderBackground(context);

        // Draw item counts next to titles
        if (client != null) {
            context.drawTextWithShadow(
                    client.textRenderer, "(" + foundCount + ")", width / 4 + 30, 50, 0xFFFFFF);
            context.drawTextWithShadow(
                    client.textRenderer,
                    "(" + missingCount + ")",
                    3 * width / 4 + 30,
                    50,
                    0xFFFFFF);
        }
    }
}
