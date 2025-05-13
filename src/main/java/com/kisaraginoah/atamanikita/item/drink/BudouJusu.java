package com.kisaraginoah.atamanikita.item.drink;

import com.kisaraginoah.atamanikita.Config;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BudouJusu extends JusuBase {

    @Override
    protected void processEffects(LivingEntity livingEntity) {
        if (!livingEntity.level().isClientSide) {
            for (MobEffectInstance effectInstance : livingEntity.getActiveEffects()) {
                if (effectInstance.getEffect().value().isBeneficial()) {
                    livingEntity.addEffect(new MobEffectInstance(effectInstance.getEffect(), effectInstance.getDuration() + Config.BUDOU_JUSU_DURATION_EXTENDS.get(), effectInstance.getAmplifier(), effectInstance.isAmbient(), effectInstance.isVisible(), effectInstance.showIcon()));
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.atamanikita.budou_jusu.desc1"));
    }
}
