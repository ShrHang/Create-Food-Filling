package com.shrhang.create_food_filling.util;

import com.shrhang.create_food_filling.Config;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.ItemLore;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FoodFillingUtil {

    static DataComponentType<PotionContents> POTION_CONTENTS = DataComponents.POTION_CONTENTS;

    public static boolean isFood(ItemStack itemStack) {
        return itemStack.is(Tags.Items.FOODS) || itemStack.has(DataComponents.FOOD);
    }

    public static boolean comparePotionContents(Set<String> seen, MobEffectInstance effect) {
        String id = effect.getEffect().unwrapKey()
                .map(k -> k.location().toString())
                .orElse("unknown");

        String key = id + "|" +
                effect.getAmplifier() + "|" +
                effect.getDuration() + "|" +
                effect.isVisible();

        return !seen.add(key);
    }

    @Nullable
    public static PotionContents getUpdatedPotionContents(ItemStack food, FluidStack fluid) {
        if (fluid.getAmount() < Config.COMMON.foodFillingAmount.get() || !fluid.has(POTION_CONTENTS)) return null;
        if (!isFood(food)) return null;

        PotionContents foodContents = food.getOrDefault(POTION_CONTENTS, PotionContents.EMPTY);
        PotionContents fluidContents = fluid.get(POTION_CONTENTS);

        Set<String> seen = new HashSet<>();
        for (MobEffectInstance effect : foodContents.getAllEffects()) {
            comparePotionContents(seen, effect);
        }
        PotionContents result = foodContents;
        boolean hasChanged = false;

        if (fluidContents != null) {
            for (MobEffectInstance effect : fluidContents.getAllEffects()) {
                boolean isDuplicate = comparePotionContents(seen, effect);

                if (!isDuplicate) {
                    result = result.withEffectAdded(effect);
                    hasChanged = true;
                }
            }
        }

        return hasChanged ? result : null;
    }

    public static void updateFoodLore(ItemStack newStack, @Nullable ItemStack oldStack) {
        if (!newStack.has(POTION_CONTENTS)) return;

        PotionContents newContents = newStack.get(POTION_CONTENTS);
        if (newContents == null) return;

        List<Component> linesToAdd = new ArrayList<>();
        if (newContents.hasEffects()) {
            newContents.addPotionTooltip(linesToAdd::add, 1.0F, 20.0F);
        }

        List<Component> linesToRemove = new ArrayList<>();
        if (oldStack != null && oldStack.has(POTION_CONTENTS)) {
            PotionContents oldContents = oldStack.get(POTION_CONTENTS);
            if (oldContents != null) {
                oldContents.addPotionTooltip(linesToRemove::add, 1.0F, 20.0F);
            }
        }

        ItemLore currentLore = newStack.getOrDefault(DataComponents.LORE, ItemLore.EMPTY);
        List<Component> loreLines = new ArrayList<>(currentLore.lines());

        if (!linesToRemove.isEmpty() && !loreLines.isEmpty()) {
            for (Component toRemove : linesToRemove) {
                String removeText = toRemove.getString();
                for (int i = 0; i < loreLines.size(); i++) {
                    if (loreLines.get(i).getString().equals(removeText)) {
                        loreLines.remove(i);
                    }
                }
            }
        }

        if (!linesToAdd.isEmpty()) {
            loreLines.addAll(linesToAdd);
        }

        if (!loreLines.isEmpty() || !currentLore.lines().isEmpty()) {
            newStack.set(DataComponents.LORE, new ItemLore(loreLines));
        }
    }
}
