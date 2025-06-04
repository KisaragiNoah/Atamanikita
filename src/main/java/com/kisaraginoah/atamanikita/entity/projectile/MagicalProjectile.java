package com.kisaraginoah.atamanikita.entity.projectile;

import com.kisaraginoah.atamanikita.init.ModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class MagicalProjectile extends Projectile {
    public float damage;
    public int life;

    public MagicalProjectile(EntityType<? extends MagicalProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public MagicalProjectile(Level level, LivingEntity living, float damage) {
        this(ModEntities.MAGICAL_PROJECTILE.get(), level);
        this.setOwner(living);
        this.damage = damage;
        this.setPos(
                living.getX() - (double)(living.getBbWidth() + 1.0F) * 0.5 * (double) Mth.sin(living.yBodyRot * (float) (Math.PI / 180.0)),
                living.getEyeY() - 0.1F,
                living.getZ() + (double)(living.getBbWidth() + 1.0F) * 0.5 * (double)Mth.cos(living.yBodyRot * (float) (Math.PI / 180.0))
        );
    }

    @Override
    protected double getDefaultGravity() {
        return 0;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            tickDespawn();
        }
        Vec3 prev = this.position().subtract(this.getDeltaMovement());
        Vec3 curr = this.position();
        double dist = prev.distanceTo(curr);
        int steps = (int)(dist * 10) + 1;
        for (int i = 0; i < steps; i++) {
            double t = (double) i / steps;
            double x = Mth.lerp(t, prev.x, curr.x);
            double y = Mth.lerp(t, prev.y, curr.y);
            double z = Mth.lerp(t, prev.z, curr.z);
            this.level().addAlwaysVisibleParticle(ParticleTypes.END_ROD, true, x, y, z, 0, 0, 0);
        }
        Vec3 vec3 = this.getDeltaMovement();
        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        if (hitresult.getType() != HitResult.Type.MISS && !net.neoforged.neoforge.event.EventHooks.onProjectileImpact(this, hitresult)) {
            this.hitTargetOrDeflectSelf(hitresult);
        }
        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        this.updateRotation();
        if (this.level().getBlockStates(this.getBoundingBox()).noneMatch(BlockBehaviour.BlockStateBase::isAir)) {
            this.discard();
        } else if (this.isInWaterOrBubble()) {
            this.discard();
        } else {
            this.setDeltaMovement(vec3);
            this.applyGravity();
            this.setPos(d0, d1, d2);
        }
    }

    protected void tickDespawn() {
        this.life++;
        if (this.life >= 200) {
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (this.getOwner() instanceof LivingEntity) {
            Entity entity = result.getEntity();
            DamageSource damagesource = this.damageSources().magic();
            if (entity.hurt(damagesource, damage) && this.level() instanceof ServerLevel serverlevel) {
                EnchantmentHelper.doPostAttackEffects(serverlevel, entity, damagesource);
                this.discard();
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide) {
            this.discard();
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }
}
