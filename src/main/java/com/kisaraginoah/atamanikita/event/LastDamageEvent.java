package com.kisaraginoah.atamanikita.event;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber
public class LastDamageEvent {
    @SubscribeEvent
    public static void onEntityHurt(LivingDamageEvent.Post event) {
        if (event.getSource().getEntity() instanceof LivingEntity && !(event.getSource().getEntity() instanceof Player)) {
            event.getEntity().getPersistentData().putFloat("LastDamageTaken", event.getNewDamage());
        }
    }
}
