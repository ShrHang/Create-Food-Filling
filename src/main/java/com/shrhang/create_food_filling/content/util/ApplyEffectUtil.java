package com.shrhang.create_food_filling.content.util;

import com.shrhang.create_food_filling.Config;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import static com.shrhang.create_food_filling.content.util.FoodFillingUtil.isFood;
import static net.minecraft.core.component.DataComponents.POTION_CONTENTS;

public class ApplyEffectUtil {
    public static boolean preFilter(ItemStack stack, LivingEntity entity) {
        if (!Config.COMMON.prefilterEvent.get()) return true;

        return Config.COMMON.isEatingApplyEffects.get()
                && !entity.level().isClientSide
                && !stack.isEmpty()
                && stack.has(POTION_CONTENTS)
                && isFood(stack);
    }
    
    public static void tryToApplyEffect(ItemStack stack, LivingEntity entity) {
        if (!Config.COMMON.isEatingApplyEffects.get()
                || !entity.isAlive()
                || entity.level().isClientSide
                || stack.isEmpty()
                || !isFood(stack)) return;

        var contents = stack.get(POTION_CONTENTS);
        if (contents != null) {
            contents.getAllEffects().forEach(effect -> entity.addEffect(effect, entity));
        }
    }
}
