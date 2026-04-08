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

public class FoodFillingUtil {
    public static boolean isFood(ItemStack itemStack){
        return itemStack.isEdible();
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
        // 1. 获取新物品上的药水效果，生成我们要添加的新Lore
        List<MobEffectInstance> newEffects = PotionUtils.getAllEffects(newStack.getTag());
        List<Component> linesToAdd = new ArrayList<>();
        if (!newEffects.isEmpty()) {
            PotionUtils.addPotionTooltip(newEffects, linesToAdd, 1.0F);
        }

        // 2. 如果提供了老物品，获取原有的药水效果并生成我们要移除的旧Lore
        List<Component> linesToRemove = new ArrayList<>();
        if (oldStack != null) {
            List<MobEffectInstance> oldEffects = PotionUtils.getAllEffects(oldStack.getTag());
            if (!oldEffects.isEmpty()) {
                PotionUtils.addPotionTooltip(oldEffects, linesToRemove, 1.0F);
            }
        }

        // 3. 读取物品现有的LoreNBT数据
        CompoundTag display = newStack.getOrCreateTagElement("display");
        ListTag lore = display.contains("Lore", Tag.TAG_LIST) ? display.getList("Lore", Tag.TAG_STRING) : new ListTag();

        // 4. 将旧文本移除（避免Lore无限重复叠加，且保留原本具有的其他非药水的Lore文本）
        if (!linesToRemove.isEmpty() && !lore.isEmpty()) {
            for (Component toRemove : linesToRemove) {
                String removeJson = Component.Serializer.toJson(toRemove);
                for (int i = lore.size() - 1; i >= 0; i--) {
                    if (lore.getString(i).equals(removeJson)) {
                        lore.remove(i);
                        break; // 删除匹配的第一条即跳出
                    }
                }
            }
        }

        // 5. 追加最新合并后的药水文本Lore
        for (Component component : linesToAdd) {
            lore.add(StringTag.valueOf(Component.Serializer.toJson(component)));
        }

        if (!lore.isEmpty()) {
            display.put("Lore", lore);
        } else if (display.isEmpty() && newStack.hasTag()) {
            CompoundTag tag = newStack.getTag();
            if (tag != null) {
                tag.remove("display"); // 如果为空顺便清理
            }
        }
    }
}
