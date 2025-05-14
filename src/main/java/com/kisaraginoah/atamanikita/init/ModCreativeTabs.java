package com.kisaraginoah.atamanikita.init;

import com.kisaraginoah.atamanikita.Atamanikita;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Atamanikita.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ATAMANIKITA_TABS;

    static {
        ATAMANIKITA_TABS = REGISTER.register(
                "atamanikita_creative_tab",
                () -> CreativeModeTab.builder().title(Component.translatable("item_group.atamanikita.creativetab"))
                        .icon(() -> new ItemStack(Blocks.CHERRY_SAPLING))
                        .displayItems((parameters, output) -> {
                            output.accept(ModItems.OHANABATAKE_MUSIC_DISC.value());
                            output.accept(ModItems.ASURETIKKU_MUSIC_DISC.value());
                            output.accept(ModItems.MEGALOVANIA_MUSIC_DISC.value());
                            output.accept(ModItems.BUDOU_JUSU.value());
                            output.accept(ModItems.RINGO_JUSU.value());
                            output.accept(ModItems.ORENZI_JUSU.value());
                            output.accept(ModItems.UNIVERSAL_TOOL.value());
                        }).withSearchBar().build()
        );
    }
}
