package com.kisaraginoah.atamanikita.item.drink;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RingoJusu extends JusuBase {

    @Override
    protected void processEffects(LivingEntity livingEntity) {
        if (!livingEntity.level().isClientSide) {
            for (MobEffectInstance effectInstance : livingEntity.getActiveEffects()) {
                if (effectInstance.getEffect().value().getCategory().equals(MobEffectCategory.HARMFUL)) {
                    livingEntity.removeEffect(effectInstance.getEffect());
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.atamanikita.ringo_jusu.desc"));
    }
}
