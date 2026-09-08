package io.github.shrhang.create_food_filling.mixin;

import io.github.shrhang.create_food_filling.Config;
import io.github.shrhang.create_food_filling.api.event.PandaEatingEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.Panda;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static io.github.shrhang.create_food_filling.content.util.ApplyEffectUtil.preFilter;

@Mixin(Panda.class)
public abstract class PandaMixin {
    @Inject(method = "handleEating", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/Panda;setItemSlot(Lnet/minecraft/world/entity/EquipmentSlot;Lnet/minecraft/world/item/ItemStack;)V"))
    private void create_food_filling$onPandaEating(CallbackInfo ci) {
        if (!Config.SERVER.enablePandaFoodEffects.get()) return;
        Panda panda = (Panda) (Object) this;
        ItemStack itemStack = panda.getItemBySlot(EquipmentSlot.MAINHAND);
        if (!preFilter(itemStack, panda)) return;
        if (!itemStack.isEmpty()) {
            NeoForge.EVENT_BUS.post(new PandaEatingEvent(panda, itemStack));
        }
    }
}
