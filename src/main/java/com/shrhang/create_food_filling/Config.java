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

    public enum TooltipMode {
        UNABLE, CLIENT, SERVER
    }

    public static class Common {
        public final ModConfigSpec.BooleanValue isFoodFilling;
        public final ModConfigSpec.IntValue foodFillingAmount;
        public final ModConfigSpec.BooleanValue isEatingApplyEffects;
        public final ModConfigSpec.EnumValue<TooltipMode> isPotionTooltip;

        Common(ModConfigSpec.Builder builder) {
            isFoodFilling = builder.translation("config.is_food_filling").define("isFoodFilling", true);
            foodFillingAmount = builder.defineInRange("foodFillingAmount", 250, 1, 1000);
            isEatingApplyEffects = builder.translation("config.is_eating_apply_effects").define("isEatingApplyEffects", true);
            isPotionTooltip = builder
                    .translation("config.is_potion_tooltip")
                    .comment(
                            "PotionTooltip on food display mode:",
                            "UNABLE: Disable potion effect tooltips completely.",
                            "CLIENT: Render tooltips on the client side.",
                            "    Requires the mod to be installed on the client, otherwise tooltips will not be shown.",
                            "SERVER: Bake tooltips into item Lore on the server.",
                            "    Only requires the mod to be installed on the server.",
                            "    (Note: Foods filled before enabling SERVER mode must be refilled to show tooltips.",
                            "     Foods filled in SERVER mode will retain their tooltips even if switched to CLIENT mode later.)"
                    )
                    .defineEnum("isPotionTooltip", TooltipMode.CLIENT);
        }
    }
}
