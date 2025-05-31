package com.kisaraginoah.atamanikita.init;

import com.kisaraginoah.atamanikita.Atamanikita;
import com.kisaraginoah.atamanikita.util.MathUtils;
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
                            output.accept(ModItems.GRAPE_JUICE.value());
                            output.accept(ModItems.APPLE_JUICE.value());
                            output.accept(ModItems.ORANGE_JUICE.value());
                            output.accept(ModItems.UNIVERSAL_TOOL.value());
                            output.accept(ModItems.REVENGE_ORB.value());
                            ItemStack levelUpPickaxeStack = new ItemStack(ModItems.LEVEL_UP_PICKAXE);
                            levelUpPickaxeStack.set(ModDataComponents.MINING, 0L);
                            levelUpPickaxeStack.set(ModDataComponents.MINING_LEVEL, 1L);
                            int maxDamage = (int) (MathUtils.customPow(10, 1) - MathUtils.customPow(10, 0));
                            levelUpPickaxeStack.setDamageValue(maxDamage);
                            output.accept(levelUpPickaxeStack);
                            output.accept(ModItems.WARP_STONE.value());
                            output.accept(ModItems.REMOTE_ACTIVATOR.value());
                            output.accept(ModItems.POOP.value());
                            output.accept(ModItems.MAGIC_WAND.value());
                            output.accept(ModItems.MAGICAL_WAND.value());
                        }).withSearchBar().build()
        );
    }
}
