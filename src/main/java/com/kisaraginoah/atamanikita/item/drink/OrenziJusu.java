package com.kisaraginoah.atamanikita.item.drink;

import com.kisaraginoah.atamanikita.Config;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
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
public class OrenziJusu extends JusuBase {

    @Override
    protected void processEffects(LivingEntity livingEntity) {
        List<Holder<MobEffect>> effects = new ArrayList<>(
                BuiltInRegistries.MOB_EFFECT.holders()
                        .filter(holder -> holder.value().isBeneficial())
                        .toList()
        );

        if (livingEntity.level().isClientSide && !effects.isEmpty()) {
            RandomSource random = livingEntity.level().random;
            Collections.shuffle(effects, new Random(random.nextLong())); // シャッフル

            int count = Math.min(Config.ORENZI_JUSU_EFFECT_VALUE.get(), effects.size()); // 効果の数を超えないように

            int duration = Config.ORENZI_JUSU_EFFECT_DURATION.get();
            int amplifier = Config.ORENZI_JUSU_EFFECT_AMPLIER.get();

            for (int i = 0; i < count; i++) {
                Holder<MobEffect> effect = effects.get(i);
                livingEntity.addEffect(new MobEffectInstance(effect, duration, amplifier));
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.atamanikita.orenzi_jusu.desc1"));
    }
}
