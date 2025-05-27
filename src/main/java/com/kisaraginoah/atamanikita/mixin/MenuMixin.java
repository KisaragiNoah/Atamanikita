package com.kisaraginoah.atamanikita.mixin;

import com.kisaraginoah.atamanikita.init.ModDataComponents;
import com.kisaraginoah.atamanikita.item.tool.RemoteActivator;
import com.kisaraginoah.atamanikita.util.StateWithPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractContainerMenu.class)
public class MenuMixin {
    @Inject(method = "stillValid(Lnet/minecraft/world/inventory/ContainerLevelAccess;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/level/block/Block;)Z", at = @At("RETURN"), cancellable = true)
    private static void stillValid(ContainerLevelAccess access, Player player, Block targetBlock, CallbackInfoReturnable<Boolean> cir) {
        for (InteractionHand hand : InteractionHand.values()) {
            if (player.getItemInHand(hand).getItem() instanceof RemoteActivator) {
                StateWithPos stateWithPos = player.getItemInHand(hand).get(ModDataComponents.STATE_WITH_POS);
                if (stateWithPos != null) {
                    if (stateWithPos.state().getBlock() == targetBlock) {
                        cir.setReturnValue(true);
                    }
                }
            }
        }
    }
}
