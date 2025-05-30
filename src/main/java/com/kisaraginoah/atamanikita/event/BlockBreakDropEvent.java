package com.kisaraginoah.atamanikita.event;

import com.kisaraginoah.atamanikita.config.CommonConfig;
import com.kisaraginoah.atamanikita.item.tool.UniversalTool;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.Random;

public class BlockBreakDropEvent {
    private static final Random RANDOM = new Random();

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) {
            return;
        }
        if (RANDOM.nextFloat() < CommonConfig.UNIVERSAL_TOOL_DROP_CHANCE.get().floatValue() / 100) {
            BlockPos pos = event.getPos();
            ItemStack drop = new ItemStack(UniversalTool::new);
            ItemEntity entity = new ItemEntity(serverLevel, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, drop);
            serverLevel.addFreshEntity(entity);
            event.getPlayer().sendSystemMessage(Component.translatable("item.atamanikita.universal_tool.drop").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD));
        }
    }
}
