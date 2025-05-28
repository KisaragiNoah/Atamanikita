package com.kisaraginoah.atamanikita.event;

import com.kisaraginoah.atamanikita.config.CommonConfig;
import com.kisaraginoah.atamanikita.init.ModItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.WeakHashMap;

public class AnimalPoopEvent {

    private static final WeakHashMap<LivingEntity, Integer> animalCooldown = new WeakHashMap<>();

    @SubscribeEvent
    public static void onLivingTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof Animal animal) || event.getEntity().level().isClientSide() || event.getEntity().level().getGameTime() % 20 != 0) {
            return;
        }

        int current = animalCooldown.getOrDefault(animal, 0) + 1;
        if (current >= CommonConfig.ANIMAL_SPAWN_POOP_TIME.get()) {
            animalCooldown.put(animal, 0);

            if (animal.level().random.nextDouble() < CommonConfig.ANIMAL_SPAWN_POOP_RATE.get() / 100) {
                animal.level().addFreshEntity(new ItemEntity(animal.level(), animal.getX(), animal.getY(), animal.getZ(), new ItemStack(ModItems.POOP)));
            }
        }
    }
}
