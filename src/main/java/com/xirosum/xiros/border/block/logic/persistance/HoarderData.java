package com.xirosum.xiros.border.block.logic.persistance;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.state.StateManager;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;

import java.util.List;
import java.util.Map;


public class HoarderData extends PersistentState {

    private final List<String> foundItems;
    private final Map<String, Integer> playerItemCounts;
    private static final Codec<HoarderData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.listOf().fieldOf("foundItems").forGetter(HoarderData::foundItems),
            Codec.unboundedMap(Codec.STRING, Codec.INT).fieldOf("playerItemCounts").forGetter(HoarderData::playerItemCounts)
        ).apply(instance, HoarderData::new
    ));

    public HoarderData() {
        foundItems = List.of();
        playerItemCounts = Map.of();
    }

    public HoarderData(List<String> foundItems, Map<String, Integer> playerItemCounts) {
        this.foundItems = foundItems;
        this.playerItemCounts = playerItemCounts;
    }

    public List<String> foundItems() {
        return foundItems;
    }

    public Map<String, Integer> playerItemCounts() {
        return playerItemCounts;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.put("hoarderData", CODEC.encodeStart(NbtOps.INSTANCE, this).getOrThrow(false, HoarderData::logError));
        return nbt;
    }

    public static HoarderData loadFromPersistentStateManager(PersistentStateManager manager) {
        return manager.getOrCreate(HoarderData::fromNbt, HoarderData::new, "hoarder_data");
    }

    private static HoarderData fromNbt(NbtCompound nbt) {
        DataResult<HoarderData> result = CODEC.parse(NbtOps.INSTANCE, nbt.getCompound("hoarderData"));
        NbtCompound nbtFrom = result.getOrThrow(false, HoarderData::logError).writeNbt(new NbtCompound());

        return new HoarderData(
            nbtFrom.getList("foundItems", 8).stream().map(NbtElement::asString).toList(),
            nbtFrom.getCompound("playerItemCounts").getKeys().stream().collect(
                java.util.stream.Collectors.toMap(
                    key -> key,
                    key -> nbtFrom.getCompound("playerItemCounts").getInt(key)
                )
            )
        );
    }

    private static void logError(String error) {
        System.err.println("Error encoding HoarderData: " + error);
    }
}
