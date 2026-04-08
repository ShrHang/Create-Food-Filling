package com.shrhang.create_food_filling.registry;

import com.shrhang.create_food_filling.CreateFoodFilling;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class TagRegistry {
    public static void init() {}
    public static final TagKey<Item> ALLOW_FILLED = tag("allow_filled");
    public static final TagKey<Item> DISALLOW_FILLED = tag("disallow_filled");
    private static TagKey<Item> tag(String name) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath(CreateFoodFilling.MODID, name));
    }
}
