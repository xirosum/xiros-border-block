package com.xirosum.xiros.border.block.block.entity;

import com.xirosum.xiros.border.block.XirosBorderBlock;
import com.xirosum.xiros.border.block.item.ModItems;
import com.xirosum.xiros.border.block.screen.BorderBlockScreenHandler;
import com.xirosum.xiros.border.block.utils.BorderBlockStage;
import com.xirosum.xiros.border.block.utils.StageUtil;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.minecraft.item.Items.DIAMOND;

public class BorderBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);

    // input will be for the desired item hopefully soon will be able to make it configurable
    private static final int INPUT_SLOT = 0;
    // random chance to return a prize when inputting a resource
    private static final int OUTPUT_SLOT = 1;

    protected PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;

    private Item inputItem = DIAMOND;
    private Item outputItem = ModItems.BORDER_BUNDLE;

    private int endingBorderSize = 1000;

    private boolean initComplete = false;


    public BorderBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BORDER_BLOCK_ENTITY, pos, state);

        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> BorderBlockEntity.this.progress;
                    case 1 -> BorderBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> BorderBlockEntity.this.progress = value;
                    case 1 -> BorderBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int size() {
                return 0;
            }
        };

        BorderBlockStage stage = StageUtil.getStage(1);
    }

    // might want progress per tick so that way we can control how fast it's used.
    // or ticks to progress either way want it to be config


    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {
        packetByteBuf.writeBlockPos(this.pos);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);

        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("border_block.progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("border_block.progress");
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Border Block");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new BorderBlockScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) {
            return;
        }

        if (!initComplete) {
            initData(world);
            initComplete = true;
        }

        if(isOutputEmptyOrReceivable()) {
            if(this.hasRecipe()) {
                this.increaseCraftProgress();
                markDirty(world, pos, state);

                if(hasCraftingFinished()) {
                    increaseBorderSize(world);

                    this.craftItem();
                    this.resetProgress();
                }
            } else {
                this.resetProgress();
                markDirty(world, pos, state);
            }
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private void craftItem() {
        this.removeStack(INPUT_SLOT, 1);

        ItemStack result = new ItemStack(outputItem);

        this.setStack(OUTPUT_SLOT,  new ItemStack(result.getItem(), getOutputStack().getCount() + result.getCount()));
    }

    private boolean hasCraftingFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftProgress() {
        progress++;
    }

    private boolean hasRecipe() {
        // Barrier block for if information is lost, and we need to reset without restarting the server.
        ItemStack result = new ItemStack(outputItem);
        boolean hasInput = List.of(inputItem, Items.BARRIER).contains(getStack(INPUT_SLOT).getItem());

        return hasInput && canInsertAmountIntoOutputSlot(result) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return getOutputStack().getItem() == item || getOutputStack().isEmpty();
    }

    private boolean canInsertAmountIntoOutputSlot(ItemStack result) {
        return getOutputStack().getCount() + result.getCount() <= getOutputStack().getMaxCount();
    }

    private boolean isOutputEmptyOrReceivable() {
        return getOutputStack().isEmpty() || getOutputStack().getCount() < getOutputStack().getMaxCount();
    }

    private ItemStack getOutputStack() {
        return this.getStack(OUTPUT_SLOT);
    }

    private void increaseBorderSize(World world) {
        if(world == null) {return;}
        WorldBorder border = world.getWorldBorder();

        // if there is no world border do nothing here
        if(border.getSize() > 30_000_000) {
            return;
        }
        border.setSize(border.getSize() + 1);

        // running it everytime to detect if border was changed by command
        initData(world);

    }

    private void initData (World world) {
        if (world == null) {return;}

        BorderBlockStage  stage = StageUtil.getStage((int) world.getWorldBorder().getSize());
        endingBorderSize = stage.endingBorderSize();
        inputItem = Registries.ITEM.get(new Identifier(stage.requiredItem()));

        XirosBorderBlock.LOGGER.debug("Setting endingBorderSize: {} and inputItem: {}", endingBorderSize, inputItem);
    }
}
