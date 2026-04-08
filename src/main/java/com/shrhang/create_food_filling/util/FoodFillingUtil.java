package com.shrhang.create_food_filling.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.fluids.FluidStack;
import net.minecraft.nbt.Tag;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static com.shrhang.create_food_filling.registry.TagRegistry.*;

public class FoodFillingUtil {
    public static boolean isFood(ItemStack itemStack){
        if (itemStack.is(DISALLOW_FILLED)) return false;
        return itemStack.isEdible() || itemStack.is(ALLOW_FILLED);
    }

    public static List<MobEffectInstance> getUpdatedPotions(ItemStack itemStack, FluidStack fluidStack){

        CompoundTag fluidStackTag = fluidStack.getTag();
        List<MobEffectInstance> fluidEffects = PotionUtils.getAllEffects(fluidStackTag);

        CompoundTag itemStackTag = itemStack.getTag();
        List<MobEffectInstance> itemEffects = PotionUtils.getAllEffects(itemStackTag);

        return fluidEffects.stream()
                .filter(fluidEffect -> !itemEffects.contains(fluidEffect))
                .collect(java.util.stream.Collectors.toList());
    }

    public static void updateFoodLore(ItemStack newStack, @Nullable ItemStack oldStack) {
        List<MobEffectInstance> newEffects = PotionUtils.getAllEffects(newStack.getTag());
        List<Component> linesToAdd = new ArrayList<>();
        if (!newEffects.isEmpty()) {
            PotionUtils.addPotionTooltip(newEffects, linesToAdd, 1.0F);
        }

        List<Component> linesToRemove = new ArrayList<>();
        if (oldStack != null) {
            List<MobEffectInstance> oldEffects = PotionUtils.getAllEffects(oldStack.getTag());
            if (!oldEffects.isEmpty()) {
                PotionUtils.addPotionTooltip(oldEffects, linesToRemove, 1.0F);
            }
        }

        CompoundTag display = newStack.getOrCreateTagElement("display");
        ListTag lore = display.contains("Lore", Tag.TAG_LIST) ? display.getList("Lore", Tag.TAG_STRING) : new ListTag();

        if (!linesToRemove.isEmpty() && !lore.isEmpty()) {
            for (Component toRemove : linesToRemove) {
                String removeJson = Component.Serializer.toJson(toRemove);
                for (int i = lore.size() - 1; i >= 0; i--) {
                    if (lore.getString(i).equals(removeJson)) {
                        lore.remove(i);
                        break;
                    }
                }
            }
        }

        for (Component component : linesToAdd) {
            lore.add(StringTag.valueOf(Component.Serializer.toJson(component)));
        }

        if (!lore.isEmpty()) {
            display.put("Lore", lore);
        } else if (display.isEmpty() && newStack.hasTag()) {
            CompoundTag tag = newStack.getTag();
            if (tag != null) {
                tag.remove("display");
            }
        }
    }
}
