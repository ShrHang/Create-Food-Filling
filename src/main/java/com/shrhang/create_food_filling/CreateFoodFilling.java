package com.shrhang.create_food_filling;

import com.shrhang.create_food_filling.registry.TagRegistry;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(CreateFoodFilling.MODID)
public class CreateFoodFilling {

    public static final String MODID = "create_food_filling";

    public CreateFoodFilling() {
        TagRegistry.init();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC);
    }

}
