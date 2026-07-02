package com.shrhang.create_food_filling.mixin;

import com.shrhang.create_food_filling.Config;
import com.shrhang.create_food_filling.api.event.FoxEatingEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.shrhang.create_food_filling.util.ApplyEffectUtil.preFilter;

@Mixin(Fox.class)
public abstract class FoxMixin {
    @Inject(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;finishUsingItem(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/world/item/ItemStack;"))
    private void create_food_filling$onFoxEating(CallbackInfo ci) {
        if (!Config.SERVER.enableFoxFoodEffects.get()) return;
        Fox fox = (Fox) (Object) this;
        ItemStack itemStack = fox.getItemBySlot(EquipmentSlot.MAINHAND);
        if (!preFilter(itemStack, fox)) return;
        MinecraftForge.EVENT_BUS.post(new FoxEatingEvent(fox, itemStack));
    }
}
