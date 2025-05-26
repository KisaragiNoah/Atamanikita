package com.kisaraginoah.atamanikita.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public record StateWithPos(BlockPos pos, BlockState state) {
    public static final Codec<StateWithPos> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockPos.CODEC.fieldOf("pos").forGetter(StateWithPos::pos),
            BlockState.CODEC.fieldOf("state").forGetter(StateWithPos::state)
    ).apply(instance, StateWithPos::new));
}
