package com.kisaraginoah.atamanikita.item.drink;

import com.kisaraginoah.atamanikita.config.CommonConfig;
import com.kisaraginoah.atamanikita.util.UnCheckedBug;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
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
@UnCheckedBug
public class OrangeJuice extends JuiceBaseItem {

    public OrangeJuice() {
        super(new Properties().stacksTo(16));
    }

    @Override
    protected void execute(LivingEntity livingEntity) {
        Registry<MobEffect> mobEffectRegistry = livingEntity.level().registryAccess()
                .registryOrThrow(Registries.MOB_EFFECT);
        List<Holder<MobEffect>> holders = new ArrayList<>(
                mobEffectRegistry.holders()
                        .filter(holder -> holder.value().isBeneficial())
                        .filter(holder -> !holder.value().isInstantenous())
                        .toList()
        );
        if (!livingEntity.level().isClientSide && !holders.isEmpty()) {
            RandomSource random = livingEntity.level().random;
            Collections.shuffle(holders, new Random(random.nextLong()));
            int count = Math.min(CommonConfig.ORANGE_JUICE_EFFECT_VALUE.get(), holders.size());
            int duration = CommonConfig.ORANGE_JUICE_EFFECT_DURATION.get();
            int amplifier = CommonConfig.ORANGE_JUICE_EFFECT_AMPLIFIER.get();
            for (int i = 0; i < count; i++) {
                Holder<MobEffect> effectHolder = holders.get(i);
                livingEntity.addEffect(new MobEffectInstance(effectHolder, duration, amplifier));
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        int count = CommonConfig.ORANGE_JUICE_EFFECT_VALUE.get();
        int duration = CommonConfig.ORANGE_JUICE_EFFECT_DURATION.get() / 20;
        int amplifier = CommonConfig.ORANGE_JUICE_EFFECT_AMPLIFIER.get();
        tooltipComponents.add(Component.translatable("item.atamanikita.orange_juice.tooltip1", count).withStyle(ChatFormatting.YELLOW));
        tooltipComponents.add(Component.translatable("item.atamanikita.orange_juice.tooltip2", duration, amplifier).withStyle(ChatFormatting.YELLOW));
    }
}
