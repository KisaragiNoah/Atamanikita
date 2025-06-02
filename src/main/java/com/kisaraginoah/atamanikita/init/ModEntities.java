package com.kisaraginoah.atamanikita.init;

import com.kisaraginoah.atamanikita.Atamanikita;
import com.kisaraginoah.atamanikita.entity.projectile.MagicalProjectile;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> REGISTER = DeferredRegister.create(Registries.ENTITY_TYPE, Atamanikita.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<MagicalProjectile>> MAGICAL_PROJECTILE = REGISTER.register("magical_projectile", () -> EntityType.Builder.<MagicalProjectile>of(MagicalProjectile::new, MobCategory.MISC)
            .sized(0.25F, 0.25F)
            .clientTrackingRange(100)
            .updateInterval(10)
            .build("magical_projectile"));
}
