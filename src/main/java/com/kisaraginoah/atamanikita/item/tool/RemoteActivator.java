package com.kisaraginoah.atamanikita.item.tool;

import com.kisaraginoah.atamanikita.init.ModDataComponents;
import com.kisaraginoah.atamanikita.util.StateWithPos;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RemoteActivator extends Item {
    public static ThreadLocal<Boolean> using = ThreadLocal.withInitial(() -> false);

    public RemoteActivator() {
        super(new Properties().stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack mainHandItem = player.getMainHandItem();
        ItemStack offhandItem = player.getOffhandItem();
        if (!level.isClientSide && !player.isShiftKeyDown()) {
            if (usedHand != InteractionHand.MAIN_HAND) {
                player.sendSystemMessage(Component.translatable("item.atamanikita.remote_activator.nomainhanduse"));
                return InteractionResultHolder.pass(offhandItem);
            }
            using.set(true);
            StateWithPos stateWithPos = mainHandItem.get(ModDataComponents.STATE_WITH_POS);
            BlockPos pos;
            BlockState state;
            if (stateWithPos != null) {
                pos = stateWithPos.pos();
                state = stateWithPos.state();
            } else {
                player.sendSystemMessage(Component.translatable("item.atamanikita.fail"));
                return InteractionResultHolder.pass(mainHandItem);
            }
            if (pos != null) {
                BlockHitResult hit = new BlockHitResult(
                        Vec3.atCenterOf(pos),
                        player.getDirection(),
                        pos,
                        false
                );
                if (offhandItem.isEmpty()) {
                    state.useWithoutItem(level, player, hit);
                } else {
                    offhandItem.useOn(new UseOnContext(level, player, InteractionHand.MAIN_HAND, offhandItem, hit));
                    state = level.getBlockState(pos);
                    mainHandItem.remove(ModDataComponents.STATE_WITH_POS);
                    mainHandItem.set(ModDataComponents.STATE_WITH_POS, new StateWithPos(pos, state));
                }
                using.set(false);
                player.getCooldowns().addCooldown(this, 10);
                return InteractionResultHolder.success(mainHandItem);
            } else {
                player.displayClientMessage(Component.translatable("item.atamanikita.remote_activator.fail1"), true);
            }
        }
        return InteractionResultHolder.pass(player.getItemInHand(usedHand));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null || context.getLevel().isClientSide || !player.isShiftKeyDown()) {
            return InteractionResult.PASS;
        }
        if (context.getHand() != InteractionHand.OFF_HAND) {
            BlockPos pos = context.getClickedPos();
            BlockState state = context.getLevel().getBlockState(pos);
            ItemStack stack = context.getItemInHand();
            stack.remove(ModDataComponents.STATE_WITH_POS);
            stack.set(ModDataComponents.STATE_WITH_POS, new StateWithPos(pos, state));
            player.displayClientMessage(
                    Component.translatable("item.atamanikita.remote_activator.pos", pos.toShortString()),
                    true
            );
            return InteractionResult.SUCCESS;
        } else {
            player.sendSystemMessage(Component.translatable("item.atamanikita.remote_activator.nomainhanduse"));
        }
        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        StateWithPos stateWithPos = stack.get(ModDataComponents.STATE_WITH_POS);
        BlockPos pos = null;
        BlockState state = null;
        if (stateWithPos != null) {
            pos = stateWithPos.pos();
            state = stateWithPos.state();
        }
        tooltipComponents.add(Component.translatable("item.atamanikita.remote_activator.tooltip1").withStyle(ChatFormatting.GREEN));
        tooltipComponents.add(Component.translatable("item.atamanikita.remote_activator.tooltip2").withStyle(ChatFormatting.GREEN));
        if (pos != null && state != null) {
            tooltipComponents.add(Component.translatable("item.atamanikita.remote_activator.tooltip3", pos.toShortString()).withStyle(ChatFormatting.BLUE));
            tooltipComponents.add(Component.translatable("item.atamanikita.remote_activator.tooltip4", state.getBlock().getName()).withStyle(ChatFormatting.BLUE));
        } else {
            tooltipComponents.add(Component.translatable("item.atamanikita.remote_activator.tooltip5").withStyle(ChatFormatting.BLUE));
        }
    }
}
