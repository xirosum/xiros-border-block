package com.xirosum.xiros.border.block.mixin;

import com.xirosum.xiros.border.block.XirosBorderBlock;
import com.xirosum.xiros.border.block.logic.Hoarder;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {


    @Inject(method = "onPlayerCollision", at = @At("HEAD"), cancellable = true)
    private void onPlayerCollision(PlayerEntity player, CallbackInfo ci) {
        // Check if the item entity is within the border block's area and if the player has the required item
        // If so, prevent the item from being picked up and update the border block's state
        ItemEntity itemEntity = (ItemEntity) (Object) this;

        // only run on server
        if (itemEntity.getWorld().isClient) {
            return;
        }

        ItemStack itemStack = itemEntity.getStack();


        //check if the item can be picked up
        if (itemEntity.cannotPickup()) {
            return;
        }

        //check if the player has the item in their inventory already
        if (player.getInventory().contains(itemStack)) {
            return;
        }

        //check if there is any empty slots in the inventory
        if (player.getInventory().getEmptySlot() == -1) {
            return;
        }

        XirosBorderBlock.LOGGER.debug("Player {} collided with item entity {} with item stack {}", player.getName(), itemEntity.getUuidAsString(), itemStack.toString());

        Hoarder.trigger(player, itemStack, itemEntity.getWorld());
    }
}
