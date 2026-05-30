package com.xirosum.xiros.border.block.mixin;


import com.xirosum.xiros.border.block.XirosBorderBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.CraftingResultSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CraftingResultSlot.class)
public class CraftingResultMixin {

    @Inject(method = "onTakeItem", at = @org.spongepowered.asm.mixin.injection.At("HEAD"), cancellable = true)
    private void _onTakeItem(PlayerEntity player, ItemStack itemStack, CallbackInfo callbackInfo) {
            XirosBorderBlock.hoarder.registerNewItem(player, itemStack);
    }
}
