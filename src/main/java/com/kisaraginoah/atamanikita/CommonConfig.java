package com.kisaraginoah.atamanikita;

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
        BUILDER.pop();
    }

    public static final ModConfigSpec SPEC = BUILDER.build();
}
