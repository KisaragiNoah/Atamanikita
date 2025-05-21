package com.kisaraginoah.atamanikita.item.magic;

import com.kisaraginoah.atamanikita.init.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class WarpStone extends Item {
    public WarpStone() {
        super(new Properties().stacksTo(1).rarity(Rarity.RARE));
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 100;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (!level.isClientSide) {
            if (player.isShiftKeyDown()) {
                player.getItemInHand(usedHand).set(ModDataComponents.WARP_POSITION, player.position());
                player.sendSystemMessage(Component.translatable("item.atamanikita.warp_stone.set"));
                return InteractionResultHolder.success(player.getItemInHand(usedHand));
            } else {
                player.startUsingItem(usedHand);
            }
        }
        return super.use(level, player, usedHand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (livingEntity instanceof Player player && !level.isClientSide) {
            Vec3 vec3 = stack.get(ModDataComponents.WARP_POSITION);
            if (vec3 == null) {
                player.sendSystemMessage(Component.translatable("item.atamanikita.warp_stone.failwarp"));
                player.getCooldowns().addCooldown(stack.getItem(), 10);
            } else {
                player.teleportTo(vec3.x, vec3.y, vec3.z);
                player.getCooldowns().addCooldown(stack.getItem(), 100);
            }
        }
        return super.finishUsingItem(stack, level, livingEntity);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        Vec3 vec3 = stack.get(ModDataComponents.WARP_POSITION);
        tooltipComponents.add(Component.translatable("item.atamanikita.warp_stone.desc1").withStyle(ChatFormatting.BLUE));
        if (vec3 == null) {
            tooltipComponents.add(Component.translatable("item.atamanikita.warp_stone.desc3").withStyle(ChatFormatting.BLUE));
        } else {
            tooltipComponents.add(Component.translatable("item.atamanikita.warp_stone.desc2", vec3.x, vec3.y, vec3.z).withStyle(ChatFormatting.BLUE));
        }

    }
}
