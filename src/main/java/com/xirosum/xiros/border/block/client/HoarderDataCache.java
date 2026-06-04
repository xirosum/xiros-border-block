/* (C)2026 */
package com.xirosum.xiros.border.block.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HoarderDataCache {
    private static List<String> foundItems = new ArrayList<>();
    private static Map<String, Integer> playerItemCounts = new HashMap<>();
    private static boolean active = false;
    private static long revision = 0L;

    public static synchronized void update(
            List<String> newFoundItems,
            Map<String, Integer> newPlayerItemCounts,
            boolean newActive) {
        foundItems = new ArrayList<>(newFoundItems);
        playerItemCounts = new HashMap<>(newPlayerItemCounts);
        active = newActive;
        revision++;
    }

    public static synchronized List<String> getFoundItems() {
        return new ArrayList<>(foundItems);
    }

    public static synchronized Map<String, Integer> getPlayerItemCounts() {
        return new HashMap<>(playerItemCounts);
    }

    public static synchronized boolean isActive() {
        return active;
    }

    public static synchronized long getRevision() {
        return revision;
    }

    public static synchronized void clear() {
        foundItems.clear();
        playerItemCounts.clear();
        active = false;
        revision++;
    }
}
