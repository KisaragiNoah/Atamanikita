package com.kisaraginoah.atamanikita.item.tool;

import com.kisaraginoah.atamanikita.init.ModDataComponents;
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
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RemoteActivator extends Item {
    public RemoteActivator() {
        super(new Properties().stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack mainStack = player.getItemInHand(usedHand);

        if (usedHand != InteractionHand.MAIN_HAND) {
            return InteractionResultHolder.pass(mainStack);
        }

        ItemStack offhandStack = player.getOffhandItem();
        if (!offhandStack.isEmpty()) {
            offhandStack.use(level, player, InteractionHand.OFF_HAND);
            return InteractionResultHolder.pass(mainStack);
        }

        if (!level.isClientSide && !player.isShiftKeyDown()) {
            if (player.getCooldowns().isOnCooldown(this)) {
                player.displayClientMessage(Component.literal("クールダウン中です"), true);
                return InteractionResultHolder.pass(mainStack);
            }
            BlockPos pos = mainStack.get(ModDataComponents.INTERACTION_POS);
            if (pos != null) {
                BlockState state = level.getBlockState(pos);
                BlockHitResult hit = new BlockHitResult(
                        Vec3.atCenterOf(pos),
                        player.getDirection(),
                        pos,
                        false
                );

                InteractionResult result = state.useWithoutItem(level, player, hit);
                player.getCooldowns().addCooldown(this, 20);
                return InteractionResultHolder.success(mainStack);
            } else {
                player.displayClientMessage(Component.literal("ターゲット座標が設定されていません"), true);
            }

        }
        return InteractionResultHolder.pass(player.getItemInHand(usedHand));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (!context.getLevel().isClientSide && player != null && player.isShiftKeyDown()) {
            BlockPos pos = context.getClickedPos();
            ItemStack stack = context.getItemInHand();
            stack.set(ModDataComponents.INTERACTION_POS, pos);

            player.displayClientMessage(
                    Component.literal("ターゲット座標を記録：" + pos.toShortString()),
                    true
            );
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
