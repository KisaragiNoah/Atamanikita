package com.kisaraginoah.atamanikita.item.tool;

import com.kisaraginoah.atamanikita.init.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Optional;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class LevelUpPickaxe extends Item {
    public LevelUpPickaxe() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
        long mined = Optional.ofNullable(stack.get(ModDataComponents.MINING)).orElse(0L) + 1;
        stack.set(ModDataComponents.MINING, mined);
        long mining_level = calculateLevel(mined, stack);
        int progress = (int)(mined - Math.pow(10, mining_level - 1));
        int maxProgress = (int)(Math.pow(10, mining_level) - Math.pow(10, mining_level - 1));
        stack.setDamageValue(maxProgress - progress);
        return super.mineBlock(stack, level, state, pos, miningEntity);
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level level, Player player) {
        stack.set(ModDataComponents.MINING, 0L);
        stack.set(ModDataComponents.MINING_LEVEL, 1L);
        stack.setDamageValue(0);
    }


    @Override
    public boolean isDamageable(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        long mined = Optional.ofNullable(stack.get(ModDataComponents.MINING)).orElse(0L);
        long mining_level = calculateLevel(mined, stack);
        int progress = (int)(mined - Math.pow(10, mining_level - 1));
        int maxProgress = (int)(Math.pow(10, mining_level) - Math.pow(10, mining_level - 1));
        tooltipComponents.add(Component.translatable("item.atamanikita.level_up_pickaxe.desc1").withStyle(ChatFormatting.GOLD));
        tooltipComponents.add(Component.translatable("item.atamanikita.level_up_pickaxe.desc2", mining_level).withStyle(ChatFormatting.GOLD));
        tooltipComponents.add(Component.translatable("item.atamanikita.level_up_pickaxe.desc3", maxProgress - progress).withStyle(ChatFormatting.GOLD));
        int barLength = 20;
        int filled = (int)((progress / (float)maxProgress) * barLength);
        StringBuilder bar = new StringBuilder("進捗: ");
        for (int i = 0; i < barLength; i++) {
            bar.append(i < filled ? "■" : "□");
        }
        tooltipComponents.add(Component.literal("§7" + bar));
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        long mined = Optional.ofNullable(stack.get(ModDataComponents.MINING)).orElse(0L);
        long level = calculateLevel(mined, stack);
        return (int) (Math.pow(10, level) - Math.pow(10, level - 1));
    }


    @Override
    public boolean isRepairable(ItemStack stack) {
        return false;
    }

    private long calculateLevel(long mined, ItemStack stack) {
        long level = 1;
        long threshold = 10;
        while (mined >= threshold) {
            level++;
            stack.set(ModDataComponents.MINING_LEVEL, level);
            threshold *= 10;
        }
        return level;
    }
}
