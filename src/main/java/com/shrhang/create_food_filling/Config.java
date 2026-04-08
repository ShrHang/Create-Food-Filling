package com.shrhang.create_food_filling;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
    public static final Common COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;

    static {
        final Pair<Common, ForgeConfigSpec> specPair =
                new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    public enum TooltipMode {
        UNABLE, CLIENT, SERVER
    }

    public static class Common {
        public final ForgeConfigSpec.BooleanValue isFoodFilling;
        public final ForgeConfigSpec.IntValue foodFillingAmount;
        public final ForgeConfigSpec.BooleanValue isEatingApplyEffects;
        public final ForgeConfigSpec.EnumValue<TooltipMode> isPotionTooltip;

        Common(ForgeConfigSpec.Builder builder) {
            isFoodFilling = builder
                    .comment("When enabled, allows filling foods with potion effects using the mod's mechanics.")
                    .define("isFoodFilling", true);
            foodFillingAmount = builder
                    .comment("The amount of food fillings to apply to the food filling.")
                    .defineInRange("foodFillingAmount", 250, 1, 1000);
            isEatingApplyEffects = builder
                    .comment("When enabled, eating foods with potion effects will apply those effects to the player.")
                    .define("isEatingApplyEffects", true);
            isPotionTooltip = builder
                    .comment(
                            "Only active when 'isEatingApplyEffects' is enabled.",
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
