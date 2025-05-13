package com.kisaraginoah.atamanikita.event;

import com.kisaraginoah.atamanikita.Config;
import com.kisaraginoah.atamanikita.init.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

import java.util.List;

@EventBusSubscriber
public class MusicDiscDropEvent {

    private static final List<Holder<Item>> DROP_CANDIDATES = List.of(
            ModItems.OHANABATAKE_MUSIC_DISC,
            ModItems.ASURETIKKU_MUSIC_DISC,
            ModItems.MEGALOVANIA_MUSIC_DISC
    );

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        LivingEntity entity = event.getEntity();

        if (event.getSource().getEntity() instanceof Player && !(entity instanceof Player)) {
            if (entity.level().random.nextFloat() < Config.MUSIC_DISC_DROP_CHANGE.get().floatValue()) {
                int index = entity.level().random.nextInt(DROP_CANDIDATES.size());
                Item item = DROP_CANDIDATES.get(index).value();
                ItemStack drop = new ItemStack(item);
                ItemEntity itemEntity = new ItemEntity(
                        entity.level(),
                        entity.getX(),
                        entity.getY(),
                        entity.getZ(),
                        drop
                );
                event.getDrops().add(itemEntity);
            }
        }
    }
}
