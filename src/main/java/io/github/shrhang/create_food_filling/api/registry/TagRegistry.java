package io.github.shrhang.create_food_filling.api.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import io.github.shrhang.create_food_filling.CreateFoodFilling;

public class TagRegistry {
    public static final TagKey<Item> ALLOW_FILLED = tag("allow_filled");
    public static final TagKey<Item> ANIMALS_FOOD = tag("animals_food");
    public static final TagKey<Item> DISALLOW_FILLED = tag("disallow_filled");

    private static TagKey<Item> tag(String name) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath(CreateFoodFilling.MODID, name));
    }

    public static void init() {
    }
}
