package com.kisaraginoah.atamanikita.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class CommonConfig {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.ConfigValue<Integer> BUDOU_JUSU_DURATION_EXTENDS;
    public static final ModConfigSpec.ConfigValue<Integer> RINGO_JUSU_REMOVE_EFFECT_VALUE;
    public static final ModConfigSpec.ConfigValue<Integer> ORENZI_JUSU_EFFECT_VALUE;
    public static final ModConfigSpec.ConfigValue<Integer> ORENZI_JUSU_EFFECT_DURATION;
    public static final ModConfigSpec.ConfigValue<Integer> ORENZI_JUSU_EFFECT_AMPLIER;
    public static final ModConfigSpec.ConfigValue<Boolean> MUSIC_DISC_DROP;
    public static final ModConfigSpec.ConfigValue<Double> MUSIC_DISC_DROP_CHANGE;
    public static final ModConfigSpec.ConfigValue<Boolean> UNIVERSAL_TOOL_DROP;
    public static final ModConfigSpec.ConfigValue<Double> UNIVERSAL_TOOL_DROP_CHANCE;
    public static final ModConfigSpec.ConfigValue<Double> REVENGE_ORB_MULTIPLIER;
    public static final ModConfigSpec.ConfigValue<Double> REVENGE_ORB_RADIUS;
    public static final ModConfigSpec.ConfigValue<Integer> WARP_STONE_USE_TIME;
    public static final ModConfigSpec.ConfigValue<Integer> WARP_STONE_COOLDOWN;
    public static final ModConfigSpec.ConfigValue<Boolean> SHIFT_SPAWN_POOP;
    public static final ModConfigSpec.ConfigValue<Boolean> ANIMAL_SPAWN_POOP;
    public static final ModConfigSpec.ConfigValue<Double> SHIFT_SPAWN_POOP_RATE;
    public static final ModConfigSpec.ConfigValue<Integer> ANIMAL_SPAWN_POOP_TICK;
    public static final ModConfigSpec.ConfigValue<Double> ANIMAL_SPAWN_POOP_RATE;

    static {
        BUILDER.push("common");
        BUDOU_JUSU_DURATION_EXTENDS = BUILDER
                .comment("ぶどうジュースを飲んだ際に効果延長をする時間")
                .define("budou_jusu_duration_extends", 1200);
        RINGO_JUSU_REMOVE_EFFECT_VALUE = BUILDER
                .comment("りんごジュースを飲んだ際に削除するエフェクトの数")
                .define("ringo_jusu_remove_effect", 1);
        ORENZI_JUSU_EFFECT_VALUE = BUILDER
                .comment("オレンジジュースを飲んだ際につくエフェクトの数")
                .define("orenzi_jusu_effect_value", 1);
        ORENZI_JUSU_EFFECT_DURATION = BUILDER
                .comment("オレンジジュースを飲んだ際につくエフェクトの効果時間")
                .define("orenzi_jusu_effect_duration", 1200);
        ORENZI_JUSU_EFFECT_AMPLIER = BUILDER
                .comment("オレンジジュースを飲んだ際につくエフェクトの効果の強さ")
                .defineInRange("orenzi_jusu_effect_amplier", 0, 0, 255);
        MUSIC_DISC_DROP = BUILDER
                .comment("追加されたレコードがドロップするかどうか")
                .define("music_disc_drop", false);
        MUSIC_DISC_DROP_CHANGE = BUILDER
                .comment("追加されたレコードのドロップ確率（％）")
                .defineInRange("music_disc_drop_change", 0.01, 0, 100);
        UNIVERSAL_TOOL_DROP = BUILDER
                .comment("ユニバーサルツールがドロップするかどうか")
                .define("universal_tool_drop", true);
        UNIVERSAL_TOOL_DROP_CHANCE = BUILDER
                .comment("ユニバーサルツールのドロップ確率（％）")
                .defineInRange("universal_tool_drop_change", 0.0001, 0, 100);
        REVENGE_ORB_MULTIPLIER = BUILDER
                .comment("リベンジオーブのダメージ乗数")
                .defineInRange("revenge_orb_multiplier", 2.0F, 0.01F, Float.MAX_VALUE);
        REVENGE_ORB_RADIUS = BUILDER
                .comment("リベンジオーブの効果範囲")
                .comment("注意！！！この値を大きくしすぎると不具合が発生する場合があります！！！")
                .defineInRange("revenge_orb_radius", 5.0, 0.1, Double.MAX_VALUE);
        WARP_STONE_USE_TIME = BUILDER
                .comment("ワープストーン発動までの時間")
                .defineInRange("warp_stone_use_time", 100, 0, Integer.MAX_VALUE);
        WARP_STONE_COOLDOWN = BUILDER
                .comment("ワープストーンのクールダウン")
                .defineInRange("warp_stone_cooldown", 100, 0, Integer.MAX_VALUE);
        SHIFT_SPAWN_POOP = BUILDER
                .comment("シフトをしたときにうんちを生成")
                .define("spawn_poop", false);
        ANIMAL_SPAWN_POOP = BUILDER
                .comment("動物からうんちを生成")
                .define("animal_spawn_poop", false);
        SHIFT_SPAWN_POOP_RATE = BUILDER
                .comment("シフトからの生成率（％）")
                .defineInRange("spawn_poop_rate", 100.0, 0.0, 100.0);
        ANIMAL_SPAWN_POOP_TICK = BUILDER
                .comment("動物からうんちが出るかロールする時間（秒）")
                .defineInRange("spawn_poop_rate", 300, 1, Integer.MAX_VALUE);
        ANIMAL_SPAWN_POOP_RATE = BUILDER
                .comment("動物からの生成率（％）")
                .defineInRange("spawn_poop_rate", 100.0, 0.0, 100.0);
        BUILDER.pop();
    }

    public static final ModConfigSpec SPEC = BUILDER.build();
}
