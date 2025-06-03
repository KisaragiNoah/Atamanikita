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
        this.level().addAlwaysVisibleParticle(ParticleTypes.END_ROD, true, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
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
        if (this.life >= 1200) {
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

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        double d0 = packet.getXa();
        double d1 = packet.getYa();
        double d2 = packet.getZa();
        this.setDeltaMovement(d0, d1, d2);
    }
}
