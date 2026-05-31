package com.shrhang.create_food_filling.mixin;

import com.shrhang.create_food_filling.api.event.FoxEatingEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.shrhang.create_food_filling.content.util.ApplyEffectUtil.preFilter;

@Mixin(Fox.class)
public abstract class FoxMixin {
    @Inject(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;finishUsingItem(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/world/item/ItemStack;"))
    private void create_food_filling$onFoxEating(CallbackInfo ci) {
        Fox fox = (Fox) (Object) this;
        ItemStack itemStack = fox.getItemBySlot(EquipmentSlot.MAINHAND);
        if (!preFilter(itemStack, fox)) return;
        FoxEatingEvent event = new FoxEatingEvent(fox, itemStack);
        NeoForge.EVENT_BUS.post(event);
    }
}
