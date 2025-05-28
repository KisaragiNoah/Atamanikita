package com.kisaraginoah.atamanikita.item.tool;

import com.kisaraginoah.atamanikita.init.ModDataComponents;
import com.kisaraginoah.atamanikita.util.MathUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Optional;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class LevelUpPickaxe extends Item {

    private static final float MINING_SPEED = 5.0F;

    public LevelUpPickaxe() {
        super(new Properties().stacksTo(1).component(DataComponents.TOOL, new Tool(List.of(
                Tool.Rule.minesAndDrops(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("minecraft", "mineable/pickaxe")), 0F)
        ), 0F, 0)).component(ModDataComponents.MINING_LEVEL, 1L).component(ModDataComponents.MINING, 0L));
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (!state.is(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("minecraft", "mineable/pickaxe"))) && !stack.isCorrectToolForDrops(state)) {
            return 0F;
        }
        long level = Optional.ofNullable(stack.get(ModDataComponents.MINING_LEVEL)).orElse(1L);
        return MINING_SPEED * (float) Math.pow(2, level - 1);
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
        int progress = (int) (mined - MathUtils.customPow(10, mining_level - 1));
        int maxProgress = (int) (MathUtils.customPow(10, mining_level) - MathUtils.customPow(10, mining_level - 1));
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
        int progress = (int) (mined - MathUtils.customPow(10, mining_level - 1));
        int maxProgress = (int) (MathUtils.customPow(10, mining_level) - MathUtils.customPow(10, mining_level - 1));
        int percent = (int) ((progress / (float) maxProgress) * 100);

        tooltipComponents.add(Component.translatable("item.atamanikita.level_up_pickaxe.desc1").withStyle(ChatFormatting.GOLD));
        tooltipComponents.add(Component.translatable("item.atamanikita.level_up_pickaxe.level", mining_level).withStyle(ChatFormatting.GOLD));
        tooltipComponents.add(Component.translatable("item.atamanikita.level_up_pickaxe.remaining", maxProgress - progress).withStyle(ChatFormatting.GOLD));
        tooltipComponents.add(Component.translatable("item.atamanikita.level_up_pickaxe.percent", percent).withStyle(ChatFormatting.GRAY));

        int barLength = 20;
        int filled = (int) ((progress / (float) maxProgress) * barLength);
        StringBuilder bar = new StringBuilder();
        for (int i = 0; i < barLength; i++) {
            bar.append(i < filled ? "■" : "□");
        }
        tooltipComponents.add(Component.literal(String.valueOf(bar)).withStyle(ChatFormatting.GRAY));
    }


    @Override
    public int getMaxDamage(ItemStack stack) {
        long level = Optional.ofNullable(stack.get(ModDataComponents.MINING_LEVEL)).orElse(1L);
        return (int) (MathUtils.customPow(10, level) - MathUtils.customPow(10, level - 1));
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return false;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (!player.level().isClientSide) {
            ItemStack stack = player.getItemInHand(usedHand);
            Registry<Enchantment> registry = level.registryAccess().registryOrThrow(Registries.ENCHANTMENT);

            registry.getHolder(Enchantments.SILK_TOUCH).ifPresent(silkTouchHolder -> {
                int silkTouchLevel = EnchantmentHelper.getTagEnchantmentLevel(silkTouchHolder, stack);
                registry.getHolder(Enchantments.FORTUNE).ifPresent(fortuneHolder -> {
                    int fortuneLevel = EnchantmentHelper.getTagEnchantmentLevel(fortuneHolder, stack);

                    if (silkTouchLevel > 0) {
                        stack.set(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
                        stack.enchant(fortuneHolder, 3);
                    } else if (fortuneLevel > 0) {
                        stack.set(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
                        stack.enchant(silkTouchHolder, 1);
                    } else {
                        stack.enchant(silkTouchHolder, 1);
                    }
                });
            });
            return InteractionResultHolder.success(stack);
        }
        return super.use(level, player, usedHand);
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
