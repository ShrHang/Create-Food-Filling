package com.shrhang.create_food_filling.content.listener;

import com.shrhang.create_food_filling.api.event.AnimalFeedingEvent;
import com.shrhang.create_food_filling.api.event.FoxEatingEvent;
import com.shrhang.create_food_filling.api.event.PandaEatingEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;

import static com.shrhang.create_food_filling.content.util.ApplyEffectUtil.tryToApplyEffect;

public class EatingListener {

    public static void init() {
        NeoForge.EVENT_BUS.addListener(EatingListener::onEat);
        NeoForge.EVENT_BUS.addListener(EatingListener::onAnimalFeeding);
        NeoForge.EVENT_BUS.addListener(EatingListener::onPandaEating);
        NeoForge.EVENT_BUS.addListener(EatingListener::onFoxEating);
    }

    public static void onEat(final LivingEntityUseItemEvent.Finish event) {
        tryToApplyEffect(event.getItem(), event.getEntity());
    }

    public static void onAnimalFeeding(final AnimalFeedingEvent event) {
        tryToApplyEffect(event.getItemStack(), event.getEntity());
    }

    public static void onPandaEating(final PandaEatingEvent event) {
        tryToApplyEffect(event.getItemStack(), event.getEntity());
    }

    public static void onFoxEating(final FoxEatingEvent event) {
        tryToApplyEffect(event.getItemStack(), event.getEntity());
    }
}
