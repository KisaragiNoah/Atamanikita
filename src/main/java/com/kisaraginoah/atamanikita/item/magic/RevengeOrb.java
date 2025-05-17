package com.kisaraginoah.atamanikita.item.magic;

import com.kisaraginoah.atamanikita.util.EntityUtils;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RevengeOrb extends Item {
    public RevengeOrb() {
        super(new Properties().stacksTo(1).rarity(Rarity.EPIC));
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
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (livingEntity instanceof Player player && !level.isClientSide) {
            if (player.getPersistentData().getFloat("LastDamageTaken") > 0) {
                if (EntityUtils.getNearbyEntities(player, 5).isEmpty()) {
                    player.sendSystemMessage(Component.translatable("item.atamanikita.revenge_orb.fail"));
                    player.getCooldowns().addCooldown(stack.getItem(), 100);
                    return super.finishUsingItem(stack, level, livingEntity);
                }
                for(Entity entity : EntityUtils.getNearbyEntities(player, 5)) {
                    if (entity instanceof LivingEntity target) {
                        System.out.println(player.getPersistentData().getFloat("LastDamageTaken") * 2F);
                        target.hurt(player.damageSources().playerAttack(player), player.getPersistentData().getFloat("LastDamageTaken") * 2F);
                        level.playSound(target, target.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 1.0F, 1.0F);
                        level.addParticle(ParticleTypes.CRIT, target.getX(), target.getY(), target.getZ(), 1, 0, 1);
                    }
                }
                player.getCooldowns().addCooldown(stack.getItem(), 200);
            } else {
                player.sendSystemMessage(Component.translatable("item.atamanikita.revenge_orb.fail1"));
                player.getCooldowns().addCooldown(stack.getItem(), 100);
            }
        }
        return super.finishUsingItem(stack, level, livingEntity);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.atamanikita.revenge_orb.desc1"));
        tooltipComponents.add(Component.translatable("item.atamanikita.revenge_orb.desc2"));
    }
}
