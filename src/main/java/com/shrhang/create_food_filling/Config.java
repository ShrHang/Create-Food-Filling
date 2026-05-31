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
        public final ModConfigSpec.BooleanValue prefilterMobInteractEvent;
        public final ModConfigSpec.BooleanValue includeAnimalsFoodInAllowFilled;
        public final ModConfigSpec.EnumValue<TooltipMode> isPotionTooltip;

        Common(ModConfigSpec.Builder builder) {
            isFoodFilling = builder
                    .comment("When enabled, allows filling foods with potion effects using the mod's mechanics.")
                    .define("isFoodFilling", true);
            foodFillingAmount = builder
                    .comment("The amount of food fillings to apply to the food filling.")
                    .defineInRange("foodFillingAmount", 250, 1, 1000);
            isEatingApplyEffects = builder
                    .comment("When enabled, eating foods with potion effects will apply those effects to the player.")
                    .define("isEatingApplyEffects", true);
            // When true, the mixin will pre-filter mobInteract calls before posting MobInteractEvent.
            // When false, the mixin will post MobInteractEvent for all mobInteract returns and
            // listeners are responsible for filtering. Default true to reduce EventBus noise.
            prefilterMobInteractEvent = builder
                    .comment("When enabled, mixins will pre-filter mobInteract before posting MobInteractEvent.")
                    .define("prefilterMobInteractEvent", true);
            includeAnimalsFoodInAllowFilled = builder
                    .comment("When enabled, treat vanilla per-animal food tags (animals_food) as part of allow_filled at runtime.",
                            "If disabled, animals_food will be ignored unless explicitly listed in allow_filled data tag.")
                    .define("includeAnimalsFoodInAllowFilled", true);
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
