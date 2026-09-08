package io.github.shrhang.create_food_filling;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
    public static final Common COMMON;
    public static final ModConfigSpec COMMON_SPEC;

    public static final Server SERVER;
    public static final ModConfigSpec SERVER_SPEC;

    static {
        Pair<Common, ModConfigSpec> commonSpecPair =
                new ModConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = commonSpecPair.getRight();
        COMMON = commonSpecPair.getLeft();

        Pair<Server, ModConfigSpec> serverSpecPair =
                new ModConfigSpec.Builder().configure(Server::new);
        SERVER_SPEC = serverSpecPair.getRight();
        SERVER = serverSpecPair.getLeft();
    }

    static void init(ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC);
        modContainer.registerConfig(ModConfig.Type.SERVER, Config.SERVER_SPEC);
    }

    public enum TooltipMode {
        UNABLE, CLIENT, SERVER
    }

    public static class Common {
        public final ModConfigSpec.BooleanValue isFoodFilling;
        public final ModConfigSpec.BooleanValue isAnimalsFoodFilled;
        public final ModConfigSpec.IntValue foodFillingAmount;
        public final ModConfigSpec.BooleanValue isEatingApplyEffects;
        public final ModConfigSpec.EnumValue<TooltipMode> tooltipMode;

        Common(ModConfigSpec.Builder builder) {

            builder.comment(
                    """
                                  `.                  `..      `..                  `.. ..                   \s
                                 `. ..                 `..    `..                 `..    `..                 \s
                                `.  `..    `....        `.. `..`..   `..  `..      `..        `..      `..   \s
                               `..   `..  `..             `..`..  `..`..  `..        `..    `.   `.. `.   `..\s
                              `...... `..   `...          `.`..    `.`..  `..           `..`..... `.`..... `..
                             `..       `..    `..         `..`..  `..`..  `..     `..    `.`.       `.       \s
                            `..         `.`.. `..         `..  `..     `..`..       `.. ..   `....    `....  \s
                                                                                                             \s"""
            );
            isFoodFilling = builder
                    .comment("When enabled, allows filling foods with potion effects using the mod's mechanics.")
                    .define("isFoodFilling", true);
            isAnimalsFoodFilled = builder
                    .comment("When enabled, items in the \"create_food_filling:animals_food\" tag are considered fillable, with higher priority than allow_filled.")
                    .define("isAnimalsFoodFilled", false);
            foodFillingAmount = builder
                    .comment("The amount of fluid required to fill a food item with potion effects.")
                    .defineInRange("foodFillingAmount", 250, 1, 1000);
            isEatingApplyEffects = builder
                    .comment("When enabled, eating foods with potion effects will apply those effects to the player.")
                    .define("isEatingApplyEffects", true);
            tooltipMode = builder
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
                    .defineEnum("tooltipMode", TooltipMode.CLIENT);
        }
    }

    public static class Server {
        public final ModConfigSpec.BooleanValue isPreFilterEvent;
        public final ModConfigSpec.BooleanValue enableFeedAnimalFoodEffects;
        public final ModConfigSpec.BooleanValue enableFoxFoodEffects;
        public final ModConfigSpec.BooleanValue enablePandaFoodEffects;
        public final ModConfigSpec.BooleanValue enableVillagerFoodEffects;
        
        Server(ModConfigSpec.Builder builder) {
            isPreFilterEvent = builder
                    .comment("When enabled, mixins check basic conditions before posting eating-related events, such as server-side execution, non-empty stacks, potion contents, and valid food items.")
                    .define("isPreFilterEvent", true);
            enableFeedAnimalFoodEffects = builder
                    .comment("When enabled, animals will receive potion effects when fed with filled foods by players.")
                    .define("enableFeedAnimalFoodEffects", true);
            enableFoxFoodEffects = builder
                    .comment("When enabled, foxes will receive potion effects when eating filled foods themselves.")
                    .define("enableFoxFoodEffects", true);
            enablePandaFoodEffects = builder
                    .comment("When enabled, pandas will receive potion effects when eating filled foods themselves.")
                    .define("enablePandaFoodEffects", true);
            enableVillagerFoodEffects = builder
                    .comment("When enabled, villagers will receive potion effects when eating filled foods during breeding behavior.")
                    .define("enableVillagerFoodEffects", true);
        }
    }
}
