package com.kisaraginoah.atamanikita.init;

import com.kisaraginoah.atamanikita.Atamanikita;
import com.kisaraginoah.atamanikita.item.drink.AppleJuice;
import com.kisaraginoah.atamanikita.item.drink.GrapeJuice;
import com.kisaraginoah.atamanikita.item.drink.OrangeJuice;
import com.kisaraginoah.atamanikita.item.magic.MagicWand;
import com.kisaraginoah.atamanikita.item.magic.MagicalWand;
import com.kisaraginoah.atamanikita.item.magic.RevengeOrb;
import com.kisaraginoah.atamanikita.item.magic.WarpStone;
import com.kisaraginoah.atamanikita.item.musicdisc.AsuretikkuMusicDisc;
import com.kisaraginoah.atamanikita.item.musicdisc.MegalovaniaMusicDisc;
import com.kisaraginoah.atamanikita.item.musicdisc.OhanabatakeMusicDisc;
import com.kisaraginoah.atamanikita.item.other.LoveItem;
import com.kisaraginoah.atamanikita.item.other.PoopItem;
import com.kisaraginoah.atamanikita.item.tool.LevelUpPickaxe;
import com.kisaraginoah.atamanikita.item.tool.RemoteActivator;
import com.kisaraginoah.atamanikita.item.tool.UniversalTool;
import com.kisaraginoah.atamanikita.item.weapon.ParticleSword;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(Registries.ITEM, Atamanikita.MOD_ID);

    public static final Holder<Item> OHANABATAKE_MUSIC_DISC = REGISTER.register("ohanabatake_music_disc", OhanabatakeMusicDisc::new);
    public static final Holder<Item> ASURETIKKU_MUSIC_DISC = REGISTER.register("asuretikku_music_disc", AsuretikkuMusicDisc::new);
    public static final Holder<Item> MEGALOVANIA_MUSIC_DISC = REGISTER.register("megalovania_music_disc", MegalovaniaMusicDisc::new);

    public static final Holder<Item> GRAPE_JUICE = REGISTER.register("grape_juice", GrapeJuice::new);
    public static final Holder<Item> APPLE_JUICE = REGISTER.register("apple_juice", AppleJuice::new);
    public static final Holder<Item> ORANGE_JUICE = REGISTER.register("orange_juice", OrangeJuice::new);

    public static final Holder<Item> UNIVERSAL_TOOL = REGISTER.register("universal_tool", UniversalTool::new);
    public static final Holder<Item> LEVEL_UP_PICKAXE = REGISTER.register("level_up_pickaxe", LevelUpPickaxe::new);

    public static final Holder<Item> REVENGE_ORB = REGISTER.register("revenge_orb", RevengeOrb::new);
    public static final Holder<Item> WARP_STONE = REGISTER.register("warp_stone", WarpStone::new);
    public static final Holder<Item> REMOTE_ACTIVATOR = REGISTER.register("remote_activator", RemoteActivator::new);

    public static final Holder<Item> POOP = REGISTER.register("poop", PoopItem::new);

    public static final Holder<Item> MAGIC_WAND = REGISTER.register("magic_wand", MagicWand::new);
    public static final Holder<Item> MAGICAL_WAND = REGISTER.register("magical_wand", MagicalWand::new);

    public static final Holder<Item> PARTICLE_SWORD = REGISTER.register("particle_sword", ParticleSword::new);
    public static final Holder<Item> LOVE_ITEM = REGISTER.register("love_item", LoveItem::new);
}
