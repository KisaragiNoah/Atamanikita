package com.kisaraginoah.atamanikita.item.drink;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class JuiceBaseItem extends Item {

    public JuiceBaseItem(Properties properties) {
        super(properties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 32;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        return ItemUtils.startUsingInstantly(level, player, usedHand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        Player player = livingEntity instanceof Player ? (Player) livingEntity : null;
        if (player instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
        }
        execute(livingEntity);
        if (player != null) {
            player.awardStat(Stats.ITEM_USED.get(this));
            stack.consume(1, player);
        }
        ItemStack result = stack;
        if (player == null || !player.hasInfiniteMaterials()) {
            if (stack.isEmpty()) {
                result = new ItemStack(Items.GLASS_BOTTLE);
            } else if (player != null) {
                player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
            } else {
                livingEntity.level().addFreshEntity(new ItemEntity(livingEntity.level(), livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), new ItemStack(Items.GLASS_BOTTLE)));
            }
        }
        livingEntity.gameEvent(GameEvent.DRINK);
        return result;
    }

    protected abstract void execute(LivingEntity livingEntity);
}
