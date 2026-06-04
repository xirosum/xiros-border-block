/* (C)2026 */
package com.xirosum.xiros.border.block.mixin;

import com.xirosum.xiros.border.block.XirosBorderBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFurnaceScreenHandler.class)
public class AbstractFurnaceScreenHandlerMixin {

    @Inject(method = "quickMove", at = @At("HEAD"))
    private void onQuickMove(PlayerEntity player, int slot, CallbackInfoReturnable cir) {
        // This is called when the player shift-clicks an item in a screen handler
        // We can use this to track when the player moves items around in their inventory
        if (player.getWorld().isClient) {
            return;
        }

        ItemStack movedStack = (ItemStack) cir.getReturnValue();

        XirosBorderBlock.hoarder.registerNewItem(player, movedStack);
    }
}
