package com.kisaraginoah.atamanikita.item.musicdisc;

import com.kisaraginoah.atamanikita.init.ModSounds;
import net.minecraft.world.item.Item;

public class MegalovaniaMusicDisc extends Item {

    public MegalovaniaMusicDisc() {
        super(new Properties().stacksTo(1).jukeboxPlayable(ModSounds.MEGALOVANIA_KEY));
    }
}
