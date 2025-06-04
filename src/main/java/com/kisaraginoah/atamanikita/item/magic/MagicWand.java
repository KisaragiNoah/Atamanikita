package com.kisaraginoah.atamanikita.item.magic;

import com.kisaraginoah.atamanikita.util.UnCheckedBug;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@UnCheckedBug
public class MagicWand extends Item {

    public MagicWand() {
        super(new Properties().stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {
            final double range = 30;
            Vec3 start = player.getEyePosition(1.0F);
            Vec3 look = player.getLookAngle();
            Vec3 end = start.add(look.scale(range));
            ClipContext context = new ClipContext(start, end, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player);
            BlockHitResult blockHit = level.clip(context);
            EntityHitResult entityHit = getEntityHitResult(level, player, start, end);
            Vec3 hitPos;
            if (entityHit != null) {
                hitPos = entityHit.getLocation();
                Entity target = entityHit.getEntity();
                target.hurt(level.damageSources().magic(), 10.0F);
                ((ServerLevel) level).sendParticles(ParticleTypes.ENCHANT,
                        target.getX(), target.getY() + 1, target.getZ(), 20, 0.5, 0.5, 0.5, 0.01);
                spawnBeamParticles(level, start, hitPos);
            } else if (blockHit.getType() == Type.BLOCK) {
                hitPos = Vec3.atLowerCornerOf(blockHit.getBlockPos());
                spawnBeamParticles(level, start, hitPos);
            } else {
                spawnBeamParticles(level, start, end);
            }
            level.playSound(null, player.blockPosition(),
                    SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 1.0F);
            player.swing(hand);
        }
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
    }

    private EntityHitResult getEntityHitResult(Level level, Player player, Vec3 start, Vec3 end) {
        AABB aabb = player.getBoundingBox().expandTowards(end.subtract(start)).inflate(1.0D);
        return ProjectileUtil.getEntityHitResult(
                level, player, start, end, aabb,
                entity -> !entity.isSpectator() && entity.isPickable() && entity != player
        );
    }

    private void spawnBeamParticles(Level level, Vec3 start, Vec3 end) {
        final double stepSize = 0.5;
        Vec3 delta = end.subtract(start);
        double length = delta.length();
        Vec3 direction = delta.normalize();
        int steps = (int) Math.ceil(length / stepSize);
        for (int i = 0; i <= steps; i++) {
            Vec3 pos = start.add(direction.scale(i * stepSize));
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.END_ROD, pos.x, pos.y, pos.z, 1, 0, 0, 0, 0.01);
            }
        }
    }
}
