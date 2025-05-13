package com.kisaraginoah.atamanikita;

import com.kisaraginoah.atamanikita.init.ModBlocks;
import com.kisaraginoah.atamanikita.init.ModCreativeTabs;
import com.kisaraginoah.atamanikita.init.ModItems;
import com.kisaraginoah.atamanikita.init.ModSounds;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(Atamanikita.MOD_ID)
public class Atamanikita {
    public static final String MOD_ID = "atamanikita";

    public Atamanikita(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        ModSounds.REGISTER.register(modEventBus);
        ModItems.REGISTER.register(modEventBus);
        ModCreativeTabs.REGISTER.register(modEventBus);
        ModBlocks.REGISTER.register(modEventBus);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }
}
