package com.kisaraginoah.atamanikita.item.drink;

import com.kisaraginoah.atamanikita.config.CommonConfig;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RingoJusu extends JusuBase {

    @Override
    protected void processEffects(LivingEntity livingEntity) {
        List<MobEffectInstance> effects = new ArrayList<>(
                livingEntity.getActiveEffects().stream()
                        .filter(mobEffectInstance -> mobEffectInstance.getEffect().value().getCategory() == MobEffectCategory.HARMFUL)
                        .toList()
        );

        if (!livingEntity.level().isClientSide && !effects.isEmpty()) {
            int maxRemoveCount = CommonConfig.RINGO_JUSU_REMOVE_EFFECT_VALUE.get();
            int removeCount = Math.min(maxRemoveCount, effects.size());

            Random javaRandom = new Random(livingEntity.level().random.nextLong());
            Collections.shuffle(effects, javaRandom);

            for (int i = 0; i < removeCount; i++) {
                livingEntity.removeEffect(effects.get(i).getEffect());
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.atamanikita.ringo_jusu.desc1"));
    }
}
