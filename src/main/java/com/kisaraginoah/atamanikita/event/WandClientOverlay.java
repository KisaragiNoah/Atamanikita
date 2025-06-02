package com.kisaraginoah.atamanikita.event;

import com.kisaraginoah.atamanikita.Atamanikita;
import com.kisaraginoah.atamanikita.item.magic.MagicalWand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

@EventBusSubscriber(modid = Atamanikita.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class WandClientOverlay {

    @SubscribeEvent
    public static void onRenderWandOverlay(RenderGuiEvent.Post event) {
        GuiGraphics graphics = event.getGuiGraphics();
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player != null && player.isUsingItem()) {
            ItemStack stack = player.getUseItem();
            if (stack.getItem() instanceof MagicalWand) {
                int useDuration = stack.getUseDuration(player);
                int remaining = player.getUseItemRemainingTicks();
                int charged = useDuration - remaining;
                float ratio = Math.min(charged / 20.0F, 20.0F);
                int barWidth = (int)(ratio * 5);
                graphics.fill(10, 10, 110, 20, 0x80000000);
                graphics.fill(10, 10, 10 + barWidth, 20, 0xFFFF00FF);
            }
        }
    }
}
