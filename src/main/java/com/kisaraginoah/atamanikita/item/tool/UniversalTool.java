package com.kisaraginoah.atamanikita.item.tool;

import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Optional;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class UniversalTool extends Item {
    private static final float BASE_DESTROY_MULTIPLIER = 10000.0F;
    private static final int MAX_DURABILITY = 100_000;
    private static final float TOOL_EFFICIENCY = 20.0F;
    private static final float BASE_ATTACK_DAMAGE = 9.0F;
    private static final float BASE_ATTACK_SPEED = -1.5F;

    public UniversalTool() {
        super(createProperties());
    }

    private static Properties createProperties() {
        Tool tool = new Tool(List.of(
                Tool.Rule.minesAndDrops(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("minecraft", "mineable/pickaxe")), TOOL_EFFICIENCY),
                Tool.Rule.minesAndDrops(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("minecraft", "mineable/axe")), TOOL_EFFICIENCY),
                Tool.Rule.minesAndDrops(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("minecraft", "mineable/shovel")), TOOL_EFFICIENCY),
                Tool.Rule.minesAndDrops(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("minecraft", "mineable/hoe")), TOOL_EFFICIENCY)
        ), TOOL_EFFICIENCY, 1);
        return new Properties()
                .stacksTo(1)
                .durability(MAX_DURABILITY)
                .component(DataComponents.TOOL, tool)
                .rarity(Rarity.EPIC)
                .attributes(ItemAttributeModifiers.builder()
                        .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, BASE_ATTACK_DAMAGE, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                        .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, BASE_ATTACK_SPEED, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                        .build());
    }

    private static boolean playerHasShieldUseIntent(UseOnContext context) {
        Player player = context.getPlayer();
        return player != null &&
                context.getHand() == InteractionHand.MAIN_HAND &&
                player.getOffhandItem().is(Items.SHIELD) &&
                !player.isSecondaryUseActive();
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("item.atamanikita.universal_tool.tooltip1").withStyle(ChatFormatting.LIGHT_PURPLE));
        tooltip.add(Component.translatable("item.atamanikita.universal_tool.tooltip2").withStyle(ChatFormatting.LIGHT_PURPLE));
        tooltip.add(Component.translatable("item.atamanikita.universal_tool.tooltip3").withStyle(ChatFormatting.LIGHT_PURPLE));
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (state.getBlock().defaultDestroyTime() == 0) {
            return 1.0F;
        }
        return BASE_DESTROY_MULTIPLIER * state.getBlock().defaultDestroyTime();
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity) {
        if (!level.isClientSide && !state.is(BlockTags.FIRE)) {
            stack.hurtAndBreak(1, entity, EquipmentSlot.MAINHAND);
        }
        return state.is(BlockTags.LEAVES)
                || state.is(Blocks.COBWEB)
                || state.is(Blocks.SHORT_GRASS)
                || state.is(Blocks.FERN)
                || state.is(Blocks.DEAD_BUSH)
                || state.is(Blocks.HANGING_ROOTS)
                || state.is(Blocks.VINE)
                || state.is(Blocks.TRIPWIRE)
                || state.is(BlockTags.WOOL);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
        if (entity instanceof net.neoforged.neoforge.common.IShearable shearable) {
            BlockPos pos = entity.blockPosition();
            Level level = entity.level();
            boolean isClient = level.isClientSide();
            if (shearable.isShearable(player, stack, level, pos)) {
                List<ItemStack> drops = shearable.onSheared(player, stack, level, pos);
                if (!isClient) {
                    drops.forEach(drop -> shearable.spawnShearedDrop(level, pos, drop));
                    stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
                }
                entity.gameEvent(GameEvent.SHEAR, player);
                return InteractionResult.sidedSuccess(isClient);
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null) return InteractionResult.PASS;
        if (context.getClickedFace() != Direction.DOWN && player.isShiftKeyDown()) {
            return tryFlatten(context);
        }
        InteractionResult tillResult = tryTill(context);
        if (tillResult != InteractionResult.PASS) return tillResult;
        if (!playerHasShieldUseIntent(context)) {
            return tryAxeActions(context);
        }
        return tryTrim(context);
    }

    private InteractionResult tryFlatten(UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null) return InteractionResult.PASS;
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState original = level.getBlockState(pos);
        BlockState flattened = original.getToolModifiedState(context, ItemAbilities.SHOVEL_FLATTEN, false);
        if (flattened != null && level.getBlockState(pos.above()).isAir()) {
            level.playSound(player, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!level.isClientSide) {
                level.setBlock(pos, flattened, 11);
                level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, flattened));
                context.getItemInHand().hurtAndBreak(1, player, LivingEntity.getSlotForHand(context.getHand()));
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.PASS;
    }

    private InteractionResult tryTill(UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null || player.isShiftKeyDown()) return InteractionResult.PASS;
        BlockState tilled = context.getLevel().getBlockState(context.getClickedPos())
                .getToolModifiedState(context, ItemAbilities.HOE_TILL, false);
        if (tilled != null) {
            Level level = context.getLevel();
            level.playSound(player, context.getClickedPos(), SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!level.isClientSide) {
                level.setBlock(context.getClickedPos(), tilled, 11);
                context.getItemInHand().hurtAndBreak(1, player, LivingEntity.getSlotForHand(context.getHand()));
                level.gameEvent(GameEvent.BLOCK_CHANGE, context.getClickedPos(), GameEvent.Context.of(player, tilled));
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    private InteractionResult tryAxeActions(UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null) return InteractionResult.PASS;
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState original = level.getBlockState(pos);
        Optional<BlockState> newState = evaluateNewBlockState(level, pos, player, original, context);
        if (newState.isPresent()) {
            BlockState newBlock = newState.get();
            level.setBlock(pos, newBlock, 11);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, newBlock));
            context.getItemInHand().hurtAndBreak(1, player, LivingEntity.getSlotForHand(context.getHand()));
            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, context.getItemInHand());
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    private InteractionResult tryTrim(UseOnContext context) {
        BlockState trimmed = context.getLevel()
                .getBlockState(context.getClickedPos())
                .getToolModifiedState(context, ItemAbilities.SHEARS_TRIM, false);
        if (trimmed != null && context.getPlayer() instanceof ServerPlayer serverPlayer) {
            context.getLevel().setBlock(context.getClickedPos(), trimmed, 11);
            context.getItemInHand().hurtAndBreak(1, serverPlayer, LivingEntity.getSlotForHand(context.getHand()));
            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, context.getClickedPos(), context.getItemInHand());
            return InteractionResult.sidedSuccess(context.getLevel().isClientSide);
        }
        return InteractionResult.PASS;
    }

    private Optional<BlockState> evaluateNewBlockState(Level level, BlockPos pos, @Nullable Player player, BlockState state, UseOnContext context) {
        return Optional.ofNullable(state.getToolModifiedState(context, ItemAbilities.AXE_STRIP, false))
                .or(() -> {
                    BlockState scrape = state.getToolModifiedState(context, ItemAbilities.AXE_SCRAPE, false);
                    if (scrape != null) {
                        level.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
                        level.levelEvent(player, 3005, pos, 0);
                    }
                    return Optional.ofNullable(scrape);
                })
                .or(() -> {
                    BlockState waxOff = state.getToolModifiedState(context, ItemAbilities.AXE_WAX_OFF, false);
                    if (waxOff != null) {
                        level.playSound(player, pos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
                        level.levelEvent(player, 3004, pos, 0);
                    }
                    return Optional.ofNullable(waxOff);
                });
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return !player.isCreative();
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility ability) {
        return ItemAbilities.DEFAULT_SHEARS_ACTIONS.contains(ability)
                || ItemAbilities.DEFAULT_HOE_ACTIONS.contains(ability)
                || ItemAbilities.DEFAULT_AXE_ACTIONS.contains(ability)
                || ItemAbilities.DEFAULT_SHOVEL_ACTIONS.contains(ability);
    }
}
