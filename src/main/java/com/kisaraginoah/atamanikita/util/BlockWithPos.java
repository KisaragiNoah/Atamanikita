package com.kisaraginoah.atamanikita.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public record BlockWithPos(BlockPos pos, BlockState state) {
    public static final Codec<BlockWithPos> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockPos.CODEC.fieldOf("pos").forGetter(BlockWithPos::pos),
            BlockState.CODEC.fieldOf("block").forGetter(BlockWithPos::state)
    ).apply(instance, BlockWithPos::new));
}
