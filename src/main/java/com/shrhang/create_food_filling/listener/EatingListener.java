package com.shrhang.create_food_filling.listener;

import com.shrhang.create_food_filling.Config;
import com.shrhang.create_food_filling.CreateFoodFilling;
import com.shrhang.create_food_filling.util.FoodFillingUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.shrhang.create_food_filling.util.FoodFillingUtil.isFood;

@Mod.EventBusSubscriber(modid = CreateFoodFilling.MODID)
public class EatingListener {

    @SubscribeEvent
    public static void onEat(LivingEntityUseItemEvent.Finish event) {
        if (!Config.COMMON.isEatingApplyEffects.get()) return;
        LivingEntity entity = event.getEntity();
        ItemStack itemStack = event.getItem();
        if (!entity.isAlive() || itemStack.isEmpty() || entity.level().isClientSide()) return;

        var effects = PotionUtils.getAllEffects(itemStack.getTag());
        if (!effects.isEmpty() && isFood(itemStack)) {
            effects.forEach(entity::addEffect);
        }
    }

}
