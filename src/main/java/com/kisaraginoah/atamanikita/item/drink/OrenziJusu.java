package com.kisaraginoah.atamanikita.item.drink;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.stream.Collectors;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class OrenziJusu extends JusuBase {

    @Override
    protected void processEffects(LivingEntity livingEntity) {
        if (!livingEntity.level().isClientSide) {
            List<Holder<MobEffect>> goodEffects = BuiltInRegistries.MOB_EFFECT.holders()
                    .filter(holder -> holder.value().isBeneficial())
                    .collect(Collectors.toList());

            if (!goodEffects.isEmpty()) {
                livingEntity.addEffect(new MobEffectInstance(goodEffects.get(livingEntity.level().random.nextInt(goodEffects.size())), 20 * 60));
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.atamanikita.orenzi_jusu.desc1"));
    }
}
