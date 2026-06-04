/* (C)2026 */
package com.xirosum.xiros.border.block.client.network;

import com.xirosum.xiros.border.block.client.HoarderDataCache;
import com.xirosum.xiros.border.block.network.HoarderDataSyncPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class ClientNetworkHandler {
    public static void registerHandlers() {
        ClientPlayNetworking.registerGlobalReceiver(
                HoarderDataSyncPacket.TYPE,
                (packet, player, handler) -> {
                    // Update the client-side cache with the received data
                    HoarderDataCache.update(
                            packet.getFoundItems(),
                            packet.getPlayerItemCounts(),
                            packet.isActive());
                });
    }
}
