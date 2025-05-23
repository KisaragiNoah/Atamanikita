package com.kisaraginoah.atamanikita.event;

import com.kisaraginoah.atamanikita.config.CommonConfig;
import com.kisaraginoah.atamanikita.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class PlayerPoopEvent {
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (event.getEntity().level().isClientSide) {
            return;
        }
        Player player = event.getEntity();
        Level level = player.level();
        RandomSource random = level.random;

        if (player.isCrouching() && player.tickCount % 20 == 0) {
            if (random.nextDouble() < CommonConfig.SHIFT_SPAWN_POOP_RATE.get() / 100) {
                BlockPos pos = player.blockPosition();
                ItemEntity poop = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.POOP));
                level.addFreshEntity(poop);
            }
        }
    }
}
