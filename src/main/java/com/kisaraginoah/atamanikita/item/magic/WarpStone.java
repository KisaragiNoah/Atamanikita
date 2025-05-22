package com.kisaraginoah.atamanikita.item.magic;

import com.kisaraginoah.atamanikita.init.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
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
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (!level.isClientSide) {
            if (player.isShiftKeyDown()) {
                ItemStack stack = player.getItemInHand(usedHand);
                stack.set(ModDataComponents.WARP_POSITION, player.position());
                stack.set(ModDataComponents.WARP_DIMENSION, player.level().dimension());
                player.sendSystemMessage(Component.translatable("item.atamanikita.warp_stone.set"));
                return InteractionResultHolder.success(stack);
            }
            player.startUsingItem(usedHand);
        }
        return InteractionResultHolder.consume(player.getItemInHand(usedHand));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (livingEntity instanceof Player player && !level.isClientSide) {
            Vec3 vec3 = stack.get(ModDataComponents.WARP_POSITION);
            ResourceKey<Level> targetDimension = stack.get(ModDataComponents.WARP_DIMENSION);
            MinecraftServer server = player.getServer();
            if (vec3 != null && targetDimension != null && server != null) {
                ServerLevel targetLevel = server.getLevel(targetDimension);
                if (targetLevel != null) {
                    player.teleportTo(targetLevel, vec3.x, vec3.y, vec3.z, RelativeMovement.ROTATION, player.getYRot(), player.getXRot());
                    player.getCooldowns().addCooldown(stack.getItem(), 100);
                } else {
                    player.sendSystemMessage(Component.translatable("item.atamanikita.warp_stone.failwarp"));
                    player.getCooldowns().addCooldown(stack.getItem(), 10);
                }
            } else {
                player.sendSystemMessage(Component.translatable("item.atamanikita.warp_stone.failwarp"));
                player.getCooldowns().addCooldown(stack.getItem(), 10);
            }
        }
        return super.finishUsingItem(stack, level, livingEntity);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        Vec3 vec3 = stack.get(ModDataComponents.WARP_POSITION);
        ResourceKey<Level> targetDimension = stack.get(ModDataComponents.WARP_DIMENSION);
        tooltipComponents.add(Component.translatable("item.atamanikita.warp_stone.desc1").withStyle(ChatFormatting.BLUE));
        if (vec3 == null || targetDimension == null) {
            tooltipComponents.add(Component.translatable("item.atamanikita.warp_stone.desc4").withStyle(ChatFormatting.BLUE));
        } else {
            String dimKey = "dimension." + targetDimension.location().getNamespace() + "." + targetDimension.location().getPath();
            Component dimensionName = Language.getInstance().has(dimKey)
                    ? Component.translatable(dimKey)
                    : Component.literal(targetDimension.location().toString());
            tooltipComponents.add(Component.translatable("item.atamanikita.warp_stone.desc2", Math.round(vec3.x), Math.round(vec3.y), Math.round(vec3.z)).withStyle(ChatFormatting.BLUE));
            tooltipComponents.add(Component.translatable("item.atamanikita.warp_stone.desc3", dimensionName).withStyle(ChatFormatting.BLUE));
        }
    }
}
