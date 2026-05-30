package com.xirosum.xiros.border.block.network;

import com.xirosum.xiros.border.block.XirosBorderBlock;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import java.util.Collections;

public class ServerNetworkHandler {
    public static void registerHandlers() {
        ServerPlayNetworking.registerGlobalReceiver(RequestHoarderDataPacket.TYPE, (packet, player, handler) -> {
            var hoarderData = XirosBorderBlock.hoarderData;
            if (hoarderData == null) {
                XirosBorderBlock.LOGGER.warn("Received hoarder data request before hoarder data was initialized");
                ServerPlayNetworking.send(player, new HoarderDataSyncPacket(Collections.emptyList(), Collections.emptyMap(), false));
                return;
            }

            // Send the hoarder data to the requesting player
            HoarderDataSyncPacket syncPacket = new HoarderDataSyncPacket(
                hoarderData.foundItems(),
                hoarderData.playerItemCounts(),
                hoarderData.isActive()
            );
            ServerPlayNetworking.send(player, syncPacket);
        });
    }
}


