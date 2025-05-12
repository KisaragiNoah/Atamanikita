package com.kisaraginoah.atamanikita.init;

import com.kisaraginoah.atamanikita.Atamanikita;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> REGISTER = DeferredRegister.create(Registries.SOUND_EVENT, Atamanikita.MOD_ID);

    public static final Supplier<SoundEvent> OHANABATAKE = registerSoundEvent("ohanabatake");
    public static final Supplier<SoundEvent> ASURETIKKU = registerSoundEvent("asuretikku");

    public static final ResourceKey<JukeboxSong> OHANABATAKE_KEY = createSong("ohanabatake");
    public static final ResourceKey<JukeboxSong> ASURETIKKU_KEY = createSong("asuretikku");

    private static ResourceKey<JukeboxSong> createSong(String name) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath(Atamanikita.MOD_ID, name));
    }

    private static Supplier<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(Atamanikita.MOD_ID, name);
        return REGISTER.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }
}
