package com.shrhang.create_food_filling;

import com.shrhang.create_food_filling.content.listener.EatingListener;
import com.shrhang.create_food_filling.api.registry.TagRegistry;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(CreateFoodFilling.MODID)
public class CreateFoodFilling {
    public static final String MODID = "create_food_filling";
    public CreateFoodFilling(ModContainer modContainer) {
        Config.init(modContainer);
        registry();
        EatingListener.init();
    }

    private void registry() {
        TagRegistry.init();
    }
}
