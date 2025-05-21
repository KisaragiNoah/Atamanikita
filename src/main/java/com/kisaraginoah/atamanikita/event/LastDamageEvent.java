package com.kisaraginoah.atamanikita.event;

import com.kisaraginoah.atamanikita.init.ModDataComponents;
import com.kisaraginoah.atamanikita.init.ModItems;
import net.minecraft.world.InteractionHand;
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
            for (InteractionHand hand : InteractionHand.values()) {
                if (event.getEntity().getItemInHand(hand).is(ModItems.REVENGE_ORB)) {
                    event.getEntity().getItemInHand(hand).set(ModDataComponents.REVENGE_DAMAGE, event.getNewDamage());
                    break;
                }
            }
         }
    }
}
