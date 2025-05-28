package com.kisaraginoah.atamanikita.item.drink;

import com.kisaraginoah.atamanikita.config.CommonConfig;
import net.minecraft.ChatFormatting;
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
public class GrapeJuice extends JuiceBaseItem {

    public GrapeJuice() {
        super(new Properties().stacksTo(16));
    }

    @Override
    protected void execute(LivingEntity livingEntity) {
        if (!livingEntity.level().isClientSide) {
            for (MobEffectInstance effectInstance : livingEntity.getActiveEffects()) {
                if (effectInstance.getEffect().value().isBeneficial()) {
                    livingEntity.addEffect(new MobEffectInstance(effectInstance.getEffect(), effectInstance.getDuration() + CommonConfig.GRAPE_JUICE_DURATION_EXTENDS.get(), effectInstance.getAmplifier(), effectInstance.isAmbient(), effectInstance.isVisible(), effectInstance.showIcon()));
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.atamanikita.grape_juice.tooltip1", CommonConfig.GRAPE_JUICE_DURATION_EXTENDS.get().floatValue() / 1200).withStyle(ChatFormatting.DARK_PURPLE));
    }
}
