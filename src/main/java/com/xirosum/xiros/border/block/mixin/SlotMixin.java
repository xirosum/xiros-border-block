/* (C)2026 */
package com.xirosum.xiros.border.block.mixin;

import com.xirosum.xiros.border.block.XirosBorderBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Slot.class)
public class SlotMixin {
    @Inject(method = "onTakeItem", at = @At("HEAD"), cancellable = true)
    private void onTakeItem(PlayerEntity player, ItemStack itemStack, CallbackInfo ci) {
        if (player.getWorld().isClient) {
            return;
        }

        XirosBorderBlock.hoarder.registerNewItem(player, itemStack);
    }
}
