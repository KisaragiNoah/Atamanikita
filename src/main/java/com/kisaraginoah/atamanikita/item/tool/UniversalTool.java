package com.kisaraginoah.atamanikita.item.tool;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.Tool;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class UniversalTool extends Item {
    public UniversalTool() {
        super(new Properties().stacksTo(1).durability(100000).component(DataComponents.TOOL, new Tool(List.of(Tool.Rule.minesAndDrops(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("minecraft", "mineable/pickaxe")), 20.0F), Tool.Rule.minesAndDrops(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("minecraft", "mineable/axe")), 20.0F), Tool.Rule.minesAndDrops(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("minecraft", "mineable/shovel")), 20.0F)), 20.0F, 1)).rarity(Rarity.EPIC));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.atamanikita.universal_tool.desc1"));
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }
}
