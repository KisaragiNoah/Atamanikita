package com.kisaraginoah.atamanikita.event;

import com.kisaraginoah.atamanikita.Atamanikita;
import com.kisaraginoah.atamanikita.init.ModEntities;
import com.kisaraginoah.atamanikita.render.NoRenderRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Atamanikita.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvent {

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.MAGICAL_PROJECTILE.get(), NoRenderRenderer::new);
    }
}
