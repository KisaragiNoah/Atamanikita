package com.kisaraginoah.atamanikita.item.magic;

import com.kisaraginoah.atamanikita.config.CommonConfig;
import com.kisaraginoah.atamanikita.init.ModDataComponents;
import com.kisaraginoah.atamanikita.util.EntityUtils;
import com.kisaraginoah.atamanikita.util.UnCheckedBug;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Optional;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@UnCheckedBug
public class RevengeOrb extends Item {

    public RevengeOrb() {
        super(new Properties().stacksTo(1).component(ModDataComponents.REVENGE_DAMAGE, 0.0F));
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 60;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        player.startUsingItem(usedHand);
        return InteractionResultHolder.consume(player.getItemInHand(usedHand));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (!level.isClientSide && livingEntity instanceof Player player) {
            if (Optional.ofNullable(stack.get(ModDataComponents.REVENGE_DAMAGE)).orElse(0.0F) > 0.0F) {
                if (EntityUtils.getNearbyNonPlayerLivingEntities(player, CommonConfig.REVENGE_ORB_RADIUS.get()).isEmpty()) {
                    player.sendSystemMessage(Component.translatable("item.atamanikita.revenge_orb.fail"));
                    player.getCooldowns().addCooldown(stack.getItem(), 20);
                    return stack;
                } else {
                    for (Entity entity : EntityUtils.getNearbyAllLivingEntities(player, CommonConfig.REVENGE_ORB_RADIUS.get())) {
                        if (entity instanceof LivingEntity target) {
                            target.hurt(player.damageSources().playerAttack(player), Optional.ofNullable(stack.get(ModDataComponents.REVENGE_DAMAGE)).orElse(0.0F) * CommonConfig.REVENGE_ORB_MULTIPLIER.get().floatValue());
                            double dx = player.getX() - target.getX();
                            double dz = player.getZ() - target.getZ();
                            double distance = Math.sqrt(dx * dx + dz * dz);
                            if (distance != 0) {
                                dx /= distance;
                                dz /= distance;
                                target.knockback(1.0F, dx, dz);
                                target.hasImpulse = true;
                            }
                            level.playSound(player, target.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 1.0F, 1.0F);
                            if (level instanceof ServerLevel serverLevel) {
                                AABB aabb = entity.getBoundingBox().inflate(0.3);
                                RandomSource randomSource = serverLevel.getRandom();
                                for (int i = 0; i < 20; i++) {
                                    double x = aabb.minX + (aabb.maxX - aabb.minX) * randomSource.nextDouble();
                                    double y = aabb.minY + (aabb.maxY - aabb.minY) * randomSource.nextDouble();
                                    double z = aabb.minZ + (aabb.maxZ - aabb.minZ) * randomSource.nextDouble();
                                    serverLevel.sendParticles(ParticleTypes.FIREWORK, x, y, z, 1, 0, 0.01, 0, 0);
                                }
                                for (int i = 0; i < 20; i++) {
                                    double x = aabb.minX + (aabb.maxX - aabb.minX) * randomSource.nextDouble();
                                    double y = aabb.minY + (aabb.maxY - aabb.minY) * randomSource.nextDouble();
                                    double z = aabb.minZ + (aabb.maxZ - aabb.minZ) * randomSource.nextDouble();
                                    serverLevel.sendParticles(ParticleTypes.DAMAGE_INDICATOR, x, y, z, 1, 0, 0.01, 0, 0);
                                }
                            }
                        }
                    }
                    stack.set(ModDataComponents.REVENGE_DAMAGE, 0.0F);
                    player.getCooldowns().addCooldown(stack.getItem(), 100);
                }
            } else {
                player.sendSystemMessage(Component.translatable("item.atamanikita.revenge_orb.fail1"));
                player.getCooldowns().addCooldown(stack.getItem(), 20);
            }
        }
        return super.finishUsingItem(stack, level, livingEntity);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.atamanikita.revenge_orb.tooltip1", CommonConfig.REVENGE_ORB_MULTIPLIER.get(), CommonConfig.REVENGE_ORB_RADIUS.get()).withStyle(ChatFormatting.AQUA));
        tooltipComponents.add(Component.translatable("item.atamanikita.revenge_orb.tooltip2").withStyle(ChatFormatting.AQUA));
        tooltipComponents.add(Component.translatable("item.atamanikita.revenge_orb.tooltip3", stack.get(ModDataComponents.REVENGE_DAMAGE)).withStyle(ChatFormatting.AQUA));
    }
}
