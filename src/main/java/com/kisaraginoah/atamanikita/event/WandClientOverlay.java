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
    private static float animatedRatio = 0.0F;

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
                float targetRatio = Math.min(charged / 20.0F, 20.0F);
                animatedRatio += (targetRatio - animatedRatio) * 0.2F;
                int barWidth = (int)(animatedRatio * 5);
                graphics.fill(10, 10, 110, 20, 0x80000000);
                int time = (int)(System.currentTimeMillis() / 50L);
                int pulse = (int)(128 + Math.sin(time * 0.3) * 127);
                int color = (pulse << 24) | 0xFF00FF;
                graphics.fill(10, 10, 10 + barWidth, 20, color);
            }
        }
    }
}
