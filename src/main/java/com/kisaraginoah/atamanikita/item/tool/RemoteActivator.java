package com.kisaraginoah.atamanikita.item.tool;

import com.kisaraginoah.atamanikita.init.ModDataComponents;
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

            BlockPos pos = mainHandItem.get(ModDataComponents.INTERACTION_POS);
            if (pos != null) {
                BlockState state = level.getBlockState(pos);
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
                }
                player.getCooldowns().addCooldown(this, 20);
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
        if (player == null || !context.getLevel().isClientSide) {
            return InteractionResult.PASS;
        }
        if (player.isShiftKeyDown() && context.getHand() != InteractionHand.OFF_HAND) {
            BlockPos pos = context.getClickedPos();
            ItemStack stack = context.getItemInHand();
            stack.set(ModDataComponents.INTERACTION_POS, pos);

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
        BlockPos pos = stack.get(ModDataComponents.INTERACTION_POS);
        tooltipComponents.add(Component.translatable("item.atamanikita.remote_activator.desc1").withStyle(ChatFormatting.GREEN));
        tooltipComponents.add(Component.translatable("item.atamanikita.remote_activator.desc2").withStyle(ChatFormatting.GREEN));
        if (pos != null) {
            tooltipComponents.add(Component.translatable("item.atamanikita.remote_activator.desc3", pos.toShortString()).withStyle(ChatFormatting.GREEN));
        } else {
            tooltipComponents.add(Component.translatable("item.atamanikita.remote_activator.desc4").withStyle(ChatFormatting.GREEN));
        }
    }
}
