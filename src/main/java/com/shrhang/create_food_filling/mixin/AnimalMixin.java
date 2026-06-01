package com.shrhang.create_food_filling.mixin;

import com.shrhang.create_food_filling.Config;
import com.shrhang.create_food_filling.api.event.AnimalFeedingEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.shrhang.create_food_filling.content.util.ApplyEffectUtil.preFilter;

@Mixin(Animal.class)
public abstract class AnimalMixin {
    @Inject(method = "usePlayerItem", at = @At("TAIL"))
    private void create_food_filling$onAnimalFed(Player player, InteractionHand hand, ItemStack itemStack, CallbackInfo ci) {
        if (!Config.SERVER.enableFeedAnimalFoodEffects.get()) return;
        if (!preFilter(itemStack, player)) return;
        Animal animal = (Animal) (Object) this;
        NeoForge.EVENT_BUS.post(new AnimalFeedingEvent(animal, player, hand, itemStack));
    }
}
