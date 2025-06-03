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
        if (!(livingEntity instanceof Player player) || level.isClientSide) return;
        MagicalProjectile magicalProjectile = new MagicalProjectile(level, player);
        int chargingTime = this.getUseDuration(stack, player) - timeCharged;
        float chargeRatio = chargingTime / 20.0F;
        float velocity = 1.0F + Math.min(chargeRatio, 20.0F);
        float damage = 10.0F + Math.min(chargeRatio, 20.0F);
        magicalProjectile.setDamage(damage);
        Vec3 vec3 = player.getLookAngle().normalize();
        magicalProjectile.shoot(vec3.x, vec3.y, vec3.z, velocity, 0.0F);
        level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS);
        level.addFreshEntity(magicalProjectile);
        super.releaseUsing(stack, level, livingEntity, timeCharged);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }
}

