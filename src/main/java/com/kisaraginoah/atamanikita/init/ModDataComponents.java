package com.kisaraginoah.atamanikita.init;

import com.kisaraginoah.atamanikita.Atamanikita;
import com.kisaraginoah.atamanikita.util.StateWithPos;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
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

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Vec3>> WARP_POSITION = register("warp_position",
            blockPosBuilder -> blockPosBuilder.persistent(Vec3.CODEC));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ResourceKey<Level>>> WARP_DIMENSION = register("warp_dimension",
            resourceKeyBuilder -> resourceKeyBuilder.persistent(ResourceKey.codec(Registries.DIMENSION)));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BlockPos>> INTERACTION_BLOCK_POS = register("interaction_block_pos",
            blockPosBuilder -> blockPosBuilder.persistent(BlockPos.CODEC));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<StateWithPos>> STATE_WITH_POS = register("state_with_pos",
            stateWithPosBuilder -> stateWithPosBuilder.persistent(StateWithPos.CODEC));

    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderUnaryOperator) {
        return REGISTER.register(name, () -> builderUnaryOperator.apply(DataComponentType.builder()).build());
    }
}
