package com.kisaraginoah.atamanikita.init;

import com.kisaraginoah.atamanikita.Atamanikita;
import com.kisaraginoah.atamanikita.item.drink.BudouJusu;
import com.kisaraginoah.atamanikita.item.drink.RingoJusu;
import com.kisaraginoah.atamanikita.item.musicdisc.AsuretikkuMusicDisc;
import com.kisaraginoah.atamanikita.item.musicdisc.OhanabatakeMusicDisc;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(Registries.ITEM, Atamanikita.MOD_ID);

    public static final Holder<Item> OHANABATAKE_MUSIC_DISC = REGISTER.register("ohanabatake_music_disc", OhanabatakeMusicDisc::new);
    public static final Holder<Item> ASURETIKKU_MUSIC_DISC = REGISTER.register("asuretikku_music_disc", AsuretikkuMusicDisc::new);

    public static final Holder<Item> BUDOU_JUSU = REGISTER.register("budou_jusu", BudouJusu::new);
    public static final Holder<Item> RINGO_JUSU = REGISTER.register("ringo_jusu", RingoJusu::new);
}
