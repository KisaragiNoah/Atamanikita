package com.kisaraginoah.atamanikita.init;

import com.kisaraginoah.atamanikita.Atamanikita;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> REGISTER = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, Atamanikita.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Long>> MINING_LEVEL = register("mining_level",
            integerBuilder -> integerBuilder.persistent(Codec.LONG));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Long>> MINING = register("mining",
            integerBuilder -> integerBuilder.persistent(Codec.LONG));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Float>> REVENGE_DAMAGE = register("revenge_damage",
            floatBuilder -> floatBuilder.persistent(Codec.FLOAT));

    private static <T>DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderUnaryOperator) {
        return REGISTER.register(name, () -> builderUnaryOperator.apply(DataComponentType.builder()).build());
    }
}
