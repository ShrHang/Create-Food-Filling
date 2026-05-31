package com.shrhang.create_food_filling.content.listener;

import com.shrhang.create_food_filling.api.event.AnimalFeedingEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;

import static com.shrhang.create_food_filling.content.util.FoodFillingUtil.tryToApplyEffect;

public class EatingListener {

    public static void init() {
        NeoForge.EVENT_BUS.addListener(EatingListener::onEat);
        NeoForge.EVENT_BUS.addListener(EatingListener::onAnimalFeeding);
    }

    public static void onEat(final LivingEntityUseItemEvent.Finish event) {
        tryToApplyEffect(event.getItem(), event.getEntity());
    }

    public static void onAnimalFeeding(final AnimalFeedingEvent event) {
        if (event.isCanceled()) return;
        tryToApplyEffect(event.getItemStack(), event.getEntity());
    }
}
