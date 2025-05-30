package com.kisaraginoah.atamanikita.mixin;

import com.kisaraginoah.atamanikita.item.tool.RemoteActivator;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerMixin {
    @Inject(method = "handleUseItem", at = @At("HEAD"), cancellable = true)
    private void handleUseItem(ServerboundUseItemPacket packet, CallbackInfo ci) {
        InteractionHand hand = packet.getHand();
        ServerPlayer player = ((ServerGamePacketListenerImpl) (Object) this).player;
        if (hand == InteractionHand.OFF_HAND) {
            ItemStack main = player.getMainHandItem();
            if (main.getItem() instanceof RemoteActivator && !RemoteActivator.using.get()) {
                ci.cancel();
            }
        }
    }
}
