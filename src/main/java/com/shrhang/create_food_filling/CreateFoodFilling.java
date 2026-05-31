package com.shrhang.create_food_filling;

import com.shrhang.create_food_filling.listener.EatingListener;
import com.shrhang.create_food_filling.registry.TagRegistry;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(CreateFoodFilling.MODID)
public class CreateFoodFilling {
    public static final String MODID = "create_food_filling";
    public CreateFoodFilling(ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC);
        registry();
        EatingListener.init();
    }

    private void registry() {
        TagRegistry.init();
    }
}
