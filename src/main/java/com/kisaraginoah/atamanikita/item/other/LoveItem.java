package com.kisaraginoah.atamanikita.item.other;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class LoveItem extends Item {
    private static final int COOLDOWN_TICKS = 20;

    public LoveItem() {
        super(new Properties().stacksTo(64));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(stack);
        }
        if (!level.isClientSide) {
            AABB area = new AABB(player.blockPosition()).inflate(10);
            List<Animal> animals = level.getEntitiesOfClass(Animal.class, area);
            for (Animal animal : animals) {
                animal.setAge(0);
                animal.setInLove(player);
                ((ServerLevel) level).sendParticles(
                        ParticleTypes.HEART,
                        animal.getX(), animal.getY() + 0.5D, animal.getZ(),
                        5, 0.3D, 0.3D, 0.3D, 0.02D
                );
            }
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.NOTE_BLOCK_CHIME.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
            player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);
            if (!player.hasInfiniteMaterials()) {
                stack.shrink(1);
            }
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}
