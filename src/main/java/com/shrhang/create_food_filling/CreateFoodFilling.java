package com.shrhang.create_food_filling;

import com.shrhang.create_food_filling.api.registry.TagRegistry;
import net.minecraftforge.fml.common.Mod;

@Mod(CreateFoodFilling.MODID)
public class CreateFoodFilling {

    public static final String MODID = "create_food_filling";

    public CreateFoodFilling() {
        TagRegistry.init();
        Config.init();
    }

}
