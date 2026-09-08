package io.github.shrhang.create_food_filling.content.listener;

import io.github.shrhang.create_food_filling.api.event.AnimalFeedingEvent;
import io.github.shrhang.create_food_filling.api.event.FoxEatingEvent;
import io.github.shrhang.create_food_filling.api.event.PandaEatingEvent;
import io.github.shrhang.create_food_filling.api.event.VillagerEatingEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;

import static io.github.shrhang.create_food_filling.content.util.ApplyEffectUtil.tryToApplyEffect;

public class EatingListener {

    public static void init() {
        NeoForge.EVENT_BUS.addListener(EatingListener::onEat);
        NeoForge.EVENT_BUS.addListener(EatingListener::onAnimalFeeding);
        NeoForge.EVENT_BUS.addListener(EatingListener::onFoxEating);
        NeoForge.EVENT_BUS.addListener(EatingListener::onPandaEating);
        NeoForge.EVENT_BUS.addListener(EatingListener::onVillagerEating);
    }

    public static void onEat(final LivingEntityUseItemEvent.Finish event) {
        tryToApplyEffect(event.getItem(), event.getEntity());
    }

    public static void onAnimalFeeding(final AnimalFeedingEvent event) {
        tryToApplyEffect(event.getItemStack(), event.getEntity());
    }

    public static void onFoxEating(final FoxEatingEvent event) {
        tryToApplyEffect(event.getItemStack(), event.getEntity());
    }

    public static void onPandaEating(final PandaEatingEvent event) {
        tryToApplyEffect(event.getItemStack(), event.getEntity());
    }

    public static void onVillagerEating(final VillagerEatingEvent event) {
        tryToApplyEffect(event.getItemStack(), event.getEntity());
    }
}
