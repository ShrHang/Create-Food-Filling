package com.shrhang.create_food_filling;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
    public static final Common COMMON;
    public static final ModConfigSpec COMMON_SPEC;

    static {
        final Pair<Common, ModConfigSpec> specPair =
                new ModConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }
    public static class Common {
        public final ModConfigSpec.BooleanValue isFoodFilling;
        public final ModConfigSpec.IntValue foodFillingAmount;
        public final ModConfigSpec.BooleanValue EatingApplyEffects;

        Common(ModConfigSpec.Builder builder) {
            isFoodFilling = builder.translation("config.is_food_filling").define("isFoodFilling", true);
            foodFillingAmount = builder.defineInRange("foodFillingAmount", 250, 1, 1000);
            EatingApplyEffects = builder.translation("config.eating_apply_effects").define("EatingApplyEffects", true);
        }
    }
}
