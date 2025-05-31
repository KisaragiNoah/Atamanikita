package com.kisaraginoah.atamanikita.item.magic;

import com.kisaraginoah.atamanikita.entity.projectile.MagicalProjectile;
import com.kisaraginoah.atamanikita.util.UnCheckedBug;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
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
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (!level.isClientSide) {
            MagicalProjectile magicalProjectile = new MagicalProjectile(level, player);
            Vec3 vec3 = player.getLookAngle().normalize();
            magicalProjectile.shoot(vec3.x, vec3.y, vec3.z, 1.0F, 0.0F);
            level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS);
            level.addFreshEntity(magicalProjectile);
        }
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(usedHand), level.isClientSide);
    }
}
