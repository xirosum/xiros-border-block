/* (C)2026 */
package com.xirosum.xiros.border.block.mixin;

import com.xirosum.xiros.border.block.XirosBorderBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.CraftingScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CraftingScreenHandler.class)
public class CraftingScreenHandlerMixin {
    @Inject(method = "quickMove", at = @At("TAIL"))
    private void onQuickMove(PlayerEntity player, int slot, CallbackInfoReturnable cir) {
        if (player.getWorld().isClient) {
            return;
        }

        ItemStack movedStack = (ItemStack) cir.getReturnValue();

        XirosBorderBlock.hoarder.registerNewItem(player, movedStack);
    }
}
