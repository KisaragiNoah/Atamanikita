package com.kisaraginoah.atamanikita.entity.projectile;

import com.kisaraginoah.atamanikita.init.ModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
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
import net.neoforged.neoforge.event.EventHooks;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class MagicalProjectile extends Projectile {
    private int life;
    private float damage;

    public MagicalProjectile(EntityType<? extends MagicalProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public MagicalProjectile(Level level, LivingEntity shooter) {
        this(ModEntities.MAGICAL_PROJECTILE.get(), level);
        this.setOwner(shooter);
        this.setPos(shooter.getX() - (double) (shooter.getBbWidth() + 1.0F) * 0.5 * (double) Mth.sin(shooter.yBodyRot * (float) (Math.PI / 180.0)),
                shooter.getEyeY() - 0.1F,
                shooter.getZ() + (double) (shooter.getBbWidth() + 1.0F) * 0.5 * (double) Mth.cos(shooter.yBodyRot * (float) (Math.PI / 180.0))
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
            this.tickDespawn();
        }
        Vec3 vec3 = this.getDeltaMovement();
        HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        if (hitResult.getType() != HitResult.Type.MISS && !EventHooks.onProjectileImpact(this, hitResult)) {
            this.hitTargetOrDeflectSelf(hitResult);
        }
        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        this.updateRotation();
        this.level().addParticle(ParticleTypes.END_ROD, this.getX(), this.getY(), this.getZ(), 0, 0.01, 0);
        float f = 0.99F;
        if (this.level().getBlockStates(this.getBoundingBox()).noneMatch(BlockBehaviour.BlockStateBase::isAir)) {
            this.discard();
        } else if (this.isInWaterOrBubble()) {
            this.discard();
        } else {
            this.setDeltaMovement(vec3.scale(f));
            this.applyGravity();
            this.setPos(d0, d1, d2);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        DamageSource damageSource = this.damageSources().magic();
        if (entity.hurt(damageSource, damage) && this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.CRIT, entity.getX(), entity.getY(), entity.getZ(), 20, 0, 0, 0 , 0.1F);
            EnchantmentHelper.doPostAttackEffects(serverLevel, entity, damageSource);
        }
        this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide) {
            this.discard();
        }
    }

    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        super.shoot(x, y, z, velocity, inaccuracy);
        this.life = 0;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    protected void tickDespawn() {
        this.life++;
        System.out.println(life);
        if (this.life >= 100) {
            this.discard();
        }
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        double d0 = packet.getXa();
        double d1 = packet.getYa();
        double d2 = packet.getZa();
        this.setDeltaMovement(d0, d1, d2);
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getDamage() {
        return damage;
    }
}
