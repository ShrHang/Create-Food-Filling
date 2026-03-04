package com.shrhang.create_food_filling.listener;

import com.shrhang.create_food_filling.Config;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;

@EventBusSubscriber
public class EatingListener {

    public static boolean isFood(ItemStack itemStack) {
        return itemStack.is(Tags.Items.FOODS) || itemStack.has(DataComponents.FOOD);
    }

    @SubscribeEvent
    public static void onEat(LivingEntityUseItemEvent.Finish event) {

        if (!Config.COMMON.EatingApplyEffects.get()) return;

        LivingEntity entity = event.getEntity();
        ItemStack itemStack = event.getItem();
        if (!entity.isAlive() || itemStack.isEmpty()) return;

        if (itemStack.has(DataComponents.POTION_CONTENTS) && isFood(itemStack)) {
            for (MobEffectInstance effect : itemStack.get(DataComponents.POTION_CONTENTS).getAllEffects()) {
                entity.addEffect(effect);
            }
        }
    }
}
