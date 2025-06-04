package com.kisaraginoah.atamanikita.item.weapon;

import com.kisaraginoah.atamanikita.util.UnCheckedBug;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@UnCheckedBug
public class ParticleSword extends SwordItem {

    private static final Tier TOOLTIER = new Tier() {

        @Override
        public int getUses() {
            return 500;
        }

        @Override
        public float getSpeed() {
            return 1f;
        }

        @Override
        public float getAttackDamageBonus() {
            return 0;
        }

        @Override
        public TagKey<Block> getIncorrectBlocksForDrops() {
            return BlockTags.SWORD_EFFICIENT;
        }

        @Override
        public int getEnchantmentValue() {
            return 20;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(new ItemStack(Items.DIAMOND));
        }
    };

    public ParticleSword() {
        super(TOOLTIER, new Properties().attributes(SwordItem.createAttributes(TOOLTIER, 5, 1.0f)));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.level() instanceof ServerLevel serverLevel) {
            AABB aabb = target.getBoundingBox();
            RandomSource randomSource = target.getRandom();
            for (int i = 0; i < 50; i++) {
                double x = aabb.minX + (aabb.maxX - aabb.minX) * randomSource.nextDouble();
                double y = aabb.minY + (aabb.maxY - aabb.minY) * randomSource.nextDouble();
                double z = aabb.minZ + (aabb.maxZ - aabb.minZ) * randomSource.nextDouble();
                serverLevel.sendParticles(ParticleTypes.END_ROD, x, y, z, 1, 0, 0,0, 0.06);
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}