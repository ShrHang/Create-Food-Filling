package com.shrhang.create_food_filling.util;

import com.shrhang.create_food_filling.Config;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;

import static com.shrhang.create_food_filling.util.FoodFillingUtil.isFood;

public class ApplyEffectUtil {
    public static boolean preFilter(ItemStack stack, LivingEntity entity) {
        if (!Config.SERVER.isPreFilterEvent.get()) return true;

        return Config.COMMON.isEatingApplyEffects.get()
                && entity != null
                && !entity.level().isClientSide
                && stack != null
                && !stack.isEmpty()
                && !PotionUtils.getAllEffects(stack.getTag()).isEmpty()
                && isFood(stack);
    }

    public static void tryToApplyEffect(ItemStack stack, LivingEntity entity) {
        if (!Config.COMMON.isEatingApplyEffects.get()
                || entity == null
                || !entity.isAlive()
                || entity.level().isClientSide
                || stack == null
                || stack.isEmpty()
                || !isFood(stack)) return;

        PotionUtils.getAllEffects(stack.copy().getTag())
                .forEach(entity::addEffect);
    }
}
