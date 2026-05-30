package com.xirosum.xiros.border.block.network;

import com.xirosum.xiros.border.block.XirosBorderBlock;
import com.xirosum.xiros.border.block.client.HoarderDataCache;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.collection.DefaultedList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HoarderDataSyncPacket implements FabricPacket {
    public static final PacketType<HoarderDataSyncPacket> TYPE = PacketType.create(
        new net.minecraft.util.Identifier(XirosBorderBlock.MOD_ID, "hoarder_data_sync"),
        HoarderDataSyncPacket::new
    );

    private List<String> foundItems;
    private Map<String, Integer> playerItemCounts;
    private boolean active;

    public HoarderDataSyncPacket(List<String> foundItems, Map<String, Integer> playerItemCounts, boolean active) {
        this.foundItems = foundItems;
        this.playerItemCounts = playerItemCounts;
        this.active = active;
    }

    public HoarderDataSyncPacket(PacketByteBuf buf) {
        // Read found items
        int foundItemsCount = buf.readInt();
        this.foundItems = new ArrayList<>();
        for (int i = 0; i < foundItemsCount; i++) {
            this.foundItems.add(buf.readString());
        }

        // Read player item counts
        int playerCountsSize = buf.readInt();
        this.playerItemCounts = new HashMap<>();
        for (int i = 0; i < playerCountsSize; i++) {
            String playerUuid = buf.readString();
            int count = buf.readInt();
            this.playerItemCounts.put(playerUuid, count);
        }

        // Read active status
        this.active = buf.readBoolean();
    }

    @Override
    public void write(PacketByteBuf buf) {
        // Write found items
        buf.writeInt(foundItems.size());
        for (String item : foundItems) {
            buf.writeString(item);
        }

        // Write player item counts
        buf.writeInt(playerItemCounts.size());
        for (var entry : playerItemCounts.entrySet()) {
            buf.writeString(entry.getKey());
            buf.writeInt(entry.getValue());
        }

        // Write active status
        buf.writeBoolean(active);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    public List<String> getFoundItems() {
        return foundItems;
    }

    public Map<String, Integer> getPlayerItemCounts() {
        return playerItemCounts;
    }

    public boolean isActive() {
        return active;
    }
}

