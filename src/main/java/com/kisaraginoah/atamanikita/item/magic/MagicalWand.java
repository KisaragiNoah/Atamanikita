package com.kisaraginoah.atamanikita.item.magic;

import com.kisaraginoah.atamanikita.entity.projectile.MagicalProjectile;
import com.kisaraginoah.atamanikita.util.UnCheckedBug;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@UnCheckedBug
public class MagicalWand extends Item {

    public MagicalWand() {
        super(new Properties().stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        player.startUsingItem(usedHand);
        return InteractionResultHolder.consume(player.getItemInHand(usedHand));
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        int chargingTime = this.getUseDuration(stack, livingEntity) - timeCharged;
        float damage = Math.min(chargingTime / 20.0F * 2, 40F);
        damage = Math.max(damage, 0.5F);
        float minVelocity = 0.1F;
        float maxVelocity = 3.0F;
        float minDamage = 0.5F;
        float maxDamage = 40.0F;
        damage = Math.min(Math.max(damage, minDamage), maxDamage);
        float velocity = maxVelocity - (damage - minDamage) * (maxVelocity - minVelocity) / (maxDamage - minDamage);
        MagicalProjectile magicalProjectile = new MagicalProjectile(level, livingEntity, damage);
        magicalProjectile.setPos(livingEntity.getEyePosition(1.0F));
        Vec3 look = livingEntity.getLookAngle();
        magicalProjectile.shoot(look.x, look.y, look.z, velocity, 1.0F);
        level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 1.0F, 1.0F);
        level.addFreshEntity(magicalProjectile);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }
}

