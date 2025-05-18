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
    public static List<Entity> getNearbyEntities(Player player, double radius) {
        Level level = player.level();
        AABB box = new AABB(
                player.getX() - radius, player.getY() - radius, player.getZ() - radius,
                player.getX() + radius, player.getY() + radius, player.getZ() + radius
        );
        return level.getEntities(player, box, entity -> entity != player && entity instanceof LivingEntity);
    }

    /**
     * 本人以外のプレイヤーの近くにいるLivingEntityを返します（近い順に最大limit件）。
     */
    public static List<Entity> getNearbyEntities(Player player, double radius, int limit) {
        Level level = player.level();
        AABB box = new AABB(
                player.getX() - radius, player.getY() - radius, player.getZ() - radius,
                player.getX() + radius, player.getY() + radius, player.getZ() + radius
        );

        return level.getEntities(player, box, entity -> entity != player && entity instanceof LivingEntity)
                .stream()
                .sorted(Comparator.comparingDouble(e -> e.distanceToSqr(player)))
                .limit(limit)
                .toList();
    }

    /**
     * プレイヤーの近くにいるすべてのLivingEntityを返します。
     */
    public static List<Entity> getNearbyAllLivingEntities(Player player, double radius) {
        Level level = player.level();
        AABB box = new AABB(
                player.getX() - radius, player.getY() - radius, player.getZ() - radius,
                player.getX() + radius, player.getY() + radius, player.getZ() + radius
        );
        return level.getEntities(player, box, entity -> entity instanceof LivingEntity);
    }

    /**
     * プレイヤーの近くにいるすべてのLivingEntityを返します（近い順に最大limit件）。
     */
    public static List<Entity> getNearbyAllLivingEntities(Player player, double radius, int limit) {
        Level level = player.level();
        AABB box = new AABB(
                player.getX() - radius, player.getY() - radius, player.getZ() - radius,
                player.getX() + radius, player.getY() + radius, player.getZ() + radius
        );

        return level.getEntities(player, box, entity -> entity instanceof LivingEntity)
                .stream()
                .sorted(Comparator.comparingDouble(e -> e.distanceToSqr(player)))
                .limit(limit)
                .toList();
    }

    /**
     * プレイヤーの近くにいるすべてのプレイヤー以外のLivingEntityを返します。
     */
    public static List<Entity> getNearbyNonPlayerEntities(Player player, double radius) {
        Level level = player.level();
        AABB box = new AABB(
                player.getX() - radius, player.getY() - radius, player.getZ() - radius,
                player.getX() + radius, player.getY() + radius, player.getZ() + radius
        );

        return level.getEntities(player, box, entity -> entity instanceof LivingEntity && !(entity instanceof Player));
    }

    /**
     * プレイヤーの近くにいるすべてのプレイヤー以外のLivingEntityを返します（近い順に最大limit件）。
     */
    public static List<Entity> getNearbyNonPlayerEntities(Player player, double radius, int limit) {
        Level level = player.level();
        AABB box = new AABB(
                player.getX() - radius, player.getY() - radius, player.getZ() - radius,
                player.getX() + radius, player.getY() + radius, player.getZ() + radius
        );

        return level.getEntities(player, box, entity -> entity instanceof LivingEntity && !(entity instanceof Player))
                .stream()
                .sorted(Comparator.comparingDouble(e -> e.distanceToSqr(player)))
                .limit(limit)
                .toList();
    }

}
