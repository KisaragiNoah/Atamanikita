package com.kisaraginoah.atamanikita.event;

import com.kisaraginoah.atamanikita.config.CommonConfig;
import com.kisaraginoah.atamanikita.init.ModItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.WeakHashMap;

public class PlayerPoopEvent {

    private static final WeakHashMap<LivingEntity, Integer> shiftCooldown = new WeakHashMap<>();

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        if (event.getEntity().level().isClientSide && player.tickCount % 20 != 0 && !player.isCrouching()) {
            return;
        }

        int current = shiftCooldown.getOrDefault(player, 0) + 1;
        if (current >= CommonConfig.SHIFT_SPAWN_POOP_TIME.get()) {
            shiftCooldown.put(player, 0);

            if (player.level().random.nextDouble() < CommonConfig.SHIFT_SPAWN_POOP_RATE.get() / 100) {
                player.level().addFreshEntity(new ItemEntity(player.level(), player.getX(), player.getY(), player.getZ(), new ItemStack(ModItems.POOP)));
            }
        }
    }
}
