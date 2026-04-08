package com.shrhang.create_food_filling.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import com.shrhang.create_food_filling.CreateFoodFilling;

public class TagRegistry {
    public static final TagKey<Item> ALLOW_FILLED = tag("allow_filled");
    public static final TagKey<Item> DISALLOW_FILLED = tag("disallow_filled");

    private static TagKey<Item> tag(String name) {
        // Use the mod id constant so namespace stays consistent
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath(CreateFoodFilling.MODID, name));
    }

    public static void init() {
    }
}
