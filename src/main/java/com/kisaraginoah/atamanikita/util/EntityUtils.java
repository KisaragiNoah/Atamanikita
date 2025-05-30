package com.kisaraginoah.atamanikita.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.Comparator;
import java.util.List;

public class EntityUtils {

    /**
     * 本人以外のプレイヤーの近くにいるLivingEntityを返します。
     */
    public static List<Entity> getNearbyEntities(LivingEntity livingEntity, double radius) {
        Level level = livingEntity.level();
        AABB box = new AABB(
                livingEntity.getX() - radius, livingEntity.getY() - radius, livingEntity.getZ() - radius,
                livingEntity.getX() + radius, livingEntity.getY() + radius, livingEntity.getZ() + radius
        );
        return level.getEntities(livingEntity, box, entity -> entity != livingEntity);
    }

    /**
     * 本人以外のプレイヤーの近くにいるLivingEntityを返します（近い順に最大limit件）。
     */
    public static List<Entity> getNearbyEntities(LivingEntity livingEntity, double radius, int limit) {
        Level level = livingEntity.level();
        AABB box = new AABB(
                livingEntity.getX() - radius, livingEntity.getY() - radius, livingEntity.getZ() - radius,
                livingEntity.getX() + radius, livingEntity.getY() + radius, livingEntity.getZ() + radius
        );

        return level.getEntities(livingEntity, box, entity -> entity != livingEntity)
                .stream()
                .sorted(Comparator.comparingDouble(e -> e.distanceToSqr(livingEntity)))
                .limit(limit)
                .toList();
    }

    /**
     * 本人以外のプレイヤーの近くにいるLivingEntityを返します。
     */
    public static List<Entity> getNearbyLivingEntities(LivingEntity livingEntity, double radius) {
        Level level = livingEntity.level();
        AABB box = new AABB(
                livingEntity.getX() - radius, livingEntity.getY() - radius, livingEntity.getZ() - radius,
                livingEntity.getX() + radius, livingEntity.getY() + radius, livingEntity.getZ() + radius
        );
        return level.getEntities(livingEntity, box, entity -> entity != livingEntity && entity instanceof LivingEntity);
    }

    /**
     * 本人以外のプレイヤーの近くにいるLivingEntityを返します（近い順に最大limit件）。
     */
    public static List<Entity> getNearbyLivingEntities(LivingEntity livingEntity, double radius, int limit) {
        Level level = livingEntity.level();
        AABB box = new AABB(
                livingEntity.getX() - radius, livingEntity.getY() - radius, livingEntity.getZ() - radius,
                livingEntity.getX() + radius, livingEntity.getY() + radius, livingEntity.getZ() + radius
        );

        return level.getEntities(livingEntity, box, entity -> entity != livingEntity && entity instanceof LivingEntity)
                .stream()
                .sorted(Comparator.comparingDouble(e -> e.distanceToSqr(livingEntity)))
                .limit(limit)
                .toList();
    }

    /**
     * プレイヤーの近くにいるすべてのLivingEntityを返します。
     */
    public static List<Entity> getNearbyAllLivingEntities(LivingEntity livingEntity, double radius) {
        Level level = livingEntity.level();
        AABB box = new AABB(
                livingEntity.getX() - radius, livingEntity.getY() - radius, livingEntity.getZ() - radius,
                livingEntity.getX() + radius, livingEntity.getY() + radius, livingEntity.getZ() + radius
        );
        return level.getEntities(livingEntity, box, entity -> entity instanceof LivingEntity);
    }

    /**
     * プレイヤーの近くにいるすべてのLivingEntityを返します（近い順に最大limit件）。
     */
    public static List<Entity> getNearbyAllLivingEntities(LivingEntity livingEntity, double radius, int limit) {
        Level level = livingEntity.level();
        AABB box = new AABB(
                livingEntity.getX() - radius, livingEntity.getY() - radius, livingEntity.getZ() - radius,
                livingEntity.getX() + radius, livingEntity.getY() + radius, livingEntity.getZ() + radius
        );

        return level.getEntities(livingEntity, box, entity -> entity instanceof LivingEntity)
                .stream()
                .sorted(Comparator.comparingDouble(e -> e.distanceToSqr(livingEntity)))
                .limit(limit)
                .toList();
    }

    /**
     * プレイヤーの近くにいるすべてのプレイヤー以外のLivingEntityを返します。
     */
    public static List<Entity> getNearbyNonPlayerLivingEntities(LivingEntity livingEntity, double radius) {
        Level level = livingEntity.level();
        AABB box = new AABB(
                livingEntity.getX() - radius, livingEntity.getY() - radius, livingEntity.getZ() - radius,
                livingEntity.getX() + radius, livingEntity.getY() + radius, livingEntity.getZ() + radius
        );

        return level.getEntities(livingEntity, box, entity -> entity instanceof LivingEntity && !(entity instanceof Player));
    }

    /**
     * プレイヤーの近くにいるすべてのプレイヤー以外のLivingEntityを返します（近い順に最大limit件）。
     */
    public static List<Entity> getNearbyNonPlayerLivingEntities(LivingEntity livingEntity, double radius, int limit) {
        Level level = livingEntity.level();
        AABB box = new AABB(
                livingEntity.getX() - radius, livingEntity.getY() - radius, livingEntity.getZ() - radius,
                livingEntity.getX() + radius, livingEntity.getY() + radius, livingEntity.getZ() + radius
        );

        return level.getEntities(livingEntity, box, entity -> entity instanceof LivingEntity && !(entity instanceof Player))
                .stream()
                .sorted(Comparator.comparingDouble(e -> e.distanceToSqr(livingEntity)))
                .limit(limit)
                .toList();
    }
}
