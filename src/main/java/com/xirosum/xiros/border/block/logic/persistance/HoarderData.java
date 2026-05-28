package com.xirosum.xiros.border.block.logic.persistance;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.xirosum.xiros.border.block.XirosBorderBlock;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class HoarderData extends PersistentState {

    private final ArrayList<String> foundItems;
    private final Map<String, Integer> playerItemCounts;
    private boolean active;
    private static final Codec<HoarderData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.listOf().fieldOf("foundItems").forGetter(HoarderData::foundItems),
            Codec.unboundedMap(Codec.STRING, Codec.INT).fieldOf("playerItemCounts").forGetter(HoarderData::playerItemCounts),
            Codec.BOOL.fieldOf("active").forGetter(HoarderData::isActive)
        ).apply(instance, HoarderData::new
    ));
    private static final String ID = "hoarder_data";

    public HoarderData() {
        foundItems = new ArrayList<>();
        playerItemCounts = new HashMap<>();
        active = false;
    }

    public HoarderData(List<String> foundItems, Map<String, Integer> playerItemCounts, boolean active) {
        this.foundItems = new ArrayList<>();
        this.foundItems.addAll(foundItems);
        this.playerItemCounts = new HashMap<>();
        this.playerItemCounts.putAll(playerItemCounts);
        this.active = active;
    }

    public List<String> foundItems() {
        return foundItems;
    }

    public Map<String, Integer> playerItemCounts() {
        return playerItemCounts;
    }

    public boolean isActive() {
        return active;
    }

    public boolean addFoundItem(String itemId) {
        if (foundItems.contains(itemId)) {
            return false;
        }

        foundItems.add(itemId);
        markDirty();
        return true;
    }

    public void incrementPlayerItemCount(String playerUuid) {
        playerItemCounts.put(playerUuid, playerItemCounts.getOrDefault(playerUuid, 0) + 1);
        markDirty();
    }

    public void clearProgress() {
        if (foundItems.isEmpty() && playerItemCounts.isEmpty()) {
            return;
        }

        foundItems.clear();
        playerItemCounts.clear();
        markDirty();
    }

    public void activateHoarder() {
        if (active) {
            return;
        }

        active = true;
        markDirty();
        XirosBorderBlock.LOGGER.info("Hoarder activated, players can now find items to increase the world border size");
    }

    public void deactivateHoarder() {
        if (!active) {
            return;
        }

        active = false;
        markDirty();
        XirosBorderBlock.LOGGER.info("Hoarder deactivated");
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.put(ID, CODEC.encodeStart(NbtOps.INSTANCE, this).getOrThrow(false, HoarderData::logError));
        return nbt;
    }

    public static HoarderData loadFromPersistentStateManager(PersistentStateManager manager) {
        return manager.getOrCreate(HoarderData::fromNbt, HoarderData::new, ID);
    }

    private static HoarderData fromNbt(NbtCompound nbt) {
        // Support both the current nested key and a direct-root format for compatibility.
        NbtCompound payload = nbt.contains(ID) ? nbt.getCompound(ID) : nbt;
        DataResult<HoarderData> result = CODEC.parse(NbtOps.INSTANCE, payload);
        Optional<HoarderData> parsed = result.resultOrPartial(HoarderData::logError);

        if (parsed.isPresent()) {
            HoarderData loaded = parsed.get();
            XirosBorderBlock.LOGGER.info(
                "Loaded hoarder data: foundItems={}, players={}, active={}",
                loaded.foundItems().size(),
                loaded.playerItemCounts().size(),
                loaded.isActive()
            );
            return loaded;
        }

        XirosBorderBlock.LOGGER.warn("Failed to parse hoarder data, starting with empty state");
        return new HoarderData();
    }

    private static void logError(String error) {
        System.err.println("Error encoding HoarderData: " + error);
    }


}
