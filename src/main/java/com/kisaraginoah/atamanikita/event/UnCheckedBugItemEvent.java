package com.kisaraginoah.atamanikita.event;

import com.kisaraginoah.atamanikita.util.UnCheckedBug;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber
public class UnCheckedBugItemEvent {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        Class<?> itemClass = item.getClass();
        if (itemClass.isAnnotationPresent(UnCheckedBug.class)) {
            event.getToolTip().add(Component.translatable("item.atamanikita.unchecked").withStyle(ChatFormatting.RED));
        }
    }
}
