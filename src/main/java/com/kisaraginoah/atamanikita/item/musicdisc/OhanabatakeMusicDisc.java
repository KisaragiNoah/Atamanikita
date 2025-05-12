package com.kisaraginoah.atamanikita.item.musicdisc;

import com.kisaraginoah.atamanikita.init.ModSounds;
import net.minecraft.world.item.Item;

public class OhanabatakeMusicDisc extends Item {
    public OhanabatakeMusicDisc() {
        super(new Properties().stacksTo(1).jukeboxPlayable(ModSounds.OHANABATAKE_KEY));
    }
}
