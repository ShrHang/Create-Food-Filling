package com.shrhang.create_food_filling.listener;

import com.shrhang.create_food_filling.CreateFoodFilling;
import com.shrhang.create_food_filling.api.event.AnimalFeedingEvent;
import com.shrhang.create_food_filling.api.event.FoxEatingEvent;
import com.shrhang.create_food_filling.api.event.PandaEatingEvent;
import com.shrhang.create_food_filling.api.event.VillagerEatingEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.shrhang.create_food_filling.util.ApplyEffectUtil.tryToApplyEffect;

@Mod.EventBusSubscriber(modid = CreateFoodFilling.MODID)
public class EatingListener {

    @SubscribeEvent
    public static void onEat(LivingEntityUseItemEvent.Finish event) {
        tryToApplyEffect(event.getItem(), event.getEntity());
    }

    @SubscribeEvent
    public static void onAnimalFeeding(AnimalFeedingEvent event) {
        tryToApplyEffect(event.getItemStack(), event.getEntity());
    }

    @SubscribeEvent
    public static void onFoxEating(FoxEatingEvent event) {
        tryToApplyEffect(event.getItemStack(), event.getEntity());
    }

    @SubscribeEvent
    public static void onPandaEating(PandaEatingEvent event) {
        tryToApplyEffect(event.getItemStack(), event.getEntity());
    }

    @SubscribeEvent
    public static void onVillagerEating(VillagerEatingEvent event) {
        tryToApplyEffect(event.getItemStack(), event.getEntity());
    }
}
