package com.kisaraginoah.atamanikita.init;

import com.kisaraginoah.atamanikita.Atamanikita;
import com.kisaraginoah.atamanikita.item.drink.BudouJusu;
import com.kisaraginoah.atamanikita.item.drink.OrenziJusu;
import com.kisaraginoah.atamanikita.item.drink.RingoJusu;
import com.kisaraginoah.atamanikita.item.magic.RevengeOrb;
import com.kisaraginoah.atamanikita.item.musicdisc.AsuretikkuMusicDisc;
import com.kisaraginoah.atamanikita.item.musicdisc.MegalovaniaMusicDisc;
import com.kisaraginoah.atamanikita.item.musicdisc.OhanabatakeMusicDisc;
import com.kisaraginoah.atamanikita.item.tool.UniversalTool;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(Registries.ITEM, Atamanikita.MOD_ID);

    public static final Holder<Item> OHANABATAKE_MUSIC_DISC = REGISTER.register("ohanabatake_music_disc", OhanabatakeMusicDisc::new);
    public static final Holder<Item> ASURETIKKU_MUSIC_DISC = REGISTER.register("asuretikku_music_disc", AsuretikkuMusicDisc::new);
    public static final Holder<Item> MEGALOVANIA_MUSIC_DISC = REGISTER.register("megalovania_music_disc", MegalovaniaMusicDisc::new);

    public static final Holder<Item> BUDOU_JUSU = REGISTER.register("budou_jusu", BudouJusu::new);
    public static final Holder<Item> RINGO_JUSU = REGISTER.register("ringo_jusu", RingoJusu::new);
    public static final Holder<Item> ORENZI_JUSU = REGISTER.register("orenzi_jusu", OrenziJusu::new);

    public static final Holder<Item> UNIVERSAL_TOOL = REGISTER.register("universal_tool", UniversalTool::new);
    public static final Holder<Item> REVENGE_ORB = REGISTER.register("revenge_orb", RevengeOrb::new);
}
