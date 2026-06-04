/* (C)2026 */
package com.xirosum.xiros.border.block.network;

import com.xirosum.xiros.border.block.XirosBorderBlock;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;

public class RequestHoarderDataPacket implements FabricPacket {
    public static final PacketType<RequestHoarderDataPacket> TYPE =
            PacketType.create(
                    new net.minecraft.util.Identifier(
                            XirosBorderBlock.MOD_ID, "request_hoarder_data"),
                    RequestHoarderDataPacket::new);

    public RequestHoarderDataPacket() {}

    public RequestHoarderDataPacket(PacketByteBuf buf) {
        // No data to read
    }

    @Override
    public void write(PacketByteBuf buf) {
        // No data to write
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
