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
        List<MobEffectInstance> effects = livingEntity.getActiveEffects().stream()
                .filter(mobEffectInstance -> mobEffectInstance.getEffect().value().getCategory() == MobEffectCategory.HARMFUL)
                .toList();
        if (!livingEntity.level().isClientSide) {
            if (!effects.isEmpty()) {
                livingEntity.removeEffect(effects.get(livingEntity.level().random.nextInt(effects.size())).getEffect());
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.atamanikita.ringo_jusu.desc1"));
    }
}
