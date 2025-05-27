package com.kisaraginoah.atamanikita.mixin;

import com.kisaraginoah.atamanikita.item.tool.RemoteActivator;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void use(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        if (hand == InteractionHand.OFF_HAND) {
            ItemStack mainHand = player.getMainHandItem();
            if (mainHand.getItem() instanceof RemoteActivator) {
                cir.setReturnValue(InteractionResultHolder.fail(player.getItemInHand(hand)));
            }
        }
    }

    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    private void useOn(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        if (context.getHand() == InteractionHand.OFF_HAND) {
            Player player = context.getPlayer();
            if (player != null) {
                ItemStack mainHand = player.getMainHandItem();
                if (mainHand.getItem() instanceof RemoteActivator) {
                    cir.setReturnValue(InteractionResult.FAIL);
                }
            }
        }
    }
}
