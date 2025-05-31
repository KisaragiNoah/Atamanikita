package com.kisaraginoah.atamanikita;

import com.kisaraginoah.atamanikita.config.ClientConfig;
import com.kisaraginoah.atamanikita.config.CommonConfig;
import com.kisaraginoah.atamanikita.event.AnimalPoopEvent;
import com.kisaraginoah.atamanikita.event.BlockBreakDropEvent;
import com.kisaraginoah.atamanikita.event.MusicDiscDropEvent;
import com.kisaraginoah.atamanikita.event.PlayerPoopEvent;
import com.kisaraginoah.atamanikita.init.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(Atamanikita.MOD_ID)
public class Atamanikita {
    public static final String MOD_ID = "atamanikita";

    public Atamanikita(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        ModSounds.REGISTER.register(modEventBus);
        ModItems.REGISTER.register(modEventBus);
        ModCreativeTabs.REGISTER.register(modEventBus);
        ModBlocks.REGISTER.register(modEventBus);
        ModDataComponents.REGISTER.register(modEventBus);
        ModEntities.REGISTER.register(modEventBus);
        modContainer.registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC);
        modContainer.registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        if (CommonConfig.MUSIC_DISC_DROP.get()) {
            NeoForge.EVENT_BUS.register(MusicDiscDropEvent.class);
        }
        if (CommonConfig.UNIVERSAL_TOOL_DROP.get()) {
            NeoForge.EVENT_BUS.register(BlockBreakDropEvent.class);
        }
        if (CommonConfig.SHIFT_SPAWN_POOP.get()) {
            NeoForge.EVENT_BUS.register(PlayerPoopEvent.class);
        }
        if (CommonConfig.ANIMAL_SPAWN_POOP.get()) {
            NeoForge.EVENT_BUS.register(AnimalPoopEvent.class);
        }
    }
}
