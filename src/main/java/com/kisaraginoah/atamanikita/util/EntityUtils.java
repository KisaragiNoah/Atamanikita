package com.kisaraginoah.atamanikita.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class EntityUtils {

    public static List<Entity> getNearbyEntities(Player player, double radius) {
        Level level = player.level();
        AABB box = new AABB(
                player.getX() - radius, player.getY() - radius, player.getZ() - radius,
                player.getX() + radius, player.getY() + radius, player.getZ() + radius
        );
        return level.getEntities(player, box, entity -> entity != player);
    }
}
