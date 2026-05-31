package com.shrhang.create_food_filling.mixin;

import com.shrhang.create_food_filling.Config;
import com.shrhang.create_food_filling.content.util.FoodFillingUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForge;
import com.shrhang.create_food_filling.api.event.AnimalFeedingEvent;
import static net.minecraft.core.component.DataComponents.POTION_CONTENTS;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Injects into Animal.usePlayerItem to post AnimalFeedingEvent when the animal consumes food from a player.
 * This is more semantically correct than injecting mobInteract, since it only fires when the item is actually consumed
 * (i.e., when the animal is being fed and the item stack is consumed).
 */
@Mixin(Animal.class)
public abstract class AnimalMixin {

    @Inject(method = "usePlayerItem", at = @At("TAIL"))
    private void create_food_filling$onAnimalFed(Player player, InteractionHand hand, ItemStack itemStack, CallbackInfo ci) {
        if (Config.COMMON.prefilterMobInteractEvent.get()) {
            if (!Config.COMMON.isEatingApplyEffects.get()) return;
            if (player.level().isClientSide) return;

            if (itemStack.isEmpty()) return;
            if (!itemStack.has(POTION_CONTENTS)) return;
            if (!FoodFillingUtil.isFood(itemStack)) return;
        }

        Animal animal = (Animal) (Object) this;
        AnimalFeedingEvent event = new AnimalFeedingEvent(animal, player, hand, itemStack);
        NeoForge.EVENT_BUS.post(event);
    }
}
