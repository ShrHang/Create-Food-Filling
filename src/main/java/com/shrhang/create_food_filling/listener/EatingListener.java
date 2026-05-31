package com.shrhang.create_food_filling.listener;

import com.shrhang.create_food_filling.Config;
import com.shrhang.create_food_filling.CreateFoodFilling;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;

import static com.shrhang.create_food_filling.util.FoodFillingUtil.isFood;
import static net.minecraft.core.component.DataComponents.POTION_CONTENTS;

public class EatingListener {

    public static void init() {
        NeoForge.EVENT_BUS.addListener(EatingListener::onEat);
    }

    public static void onEat(LivingEntityUseItemEvent.Finish event) {
        if (!Config.COMMON.isEatingApplyEffects.get()) return;
        LivingEntity entity = event.getEntity();
        ItemStack itemStack = event.getItem();
        if (!entity.isAlive() || itemStack.isEmpty() || entity.level().isClientSide) return;
        if (isFood(itemStack)) {
            var contents = itemStack.get(POTION_CONTENTS);
            if (contents != null) {
                for (MobEffectInstance effect : contents.getAllEffects()) {
                    entity.addEffect(effect, entity);
                }
            }
        }
    }
}
