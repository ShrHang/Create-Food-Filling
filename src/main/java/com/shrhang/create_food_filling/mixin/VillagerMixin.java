package com.shrhang.create_food_filling.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.shrhang.create_food_filling.Config;
import com.shrhang.create_food_filling.api.event.VillagerEatingEvent;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.shrhang.create_food_filling.content.util.ApplyEffectUtil.preFilter;

@Mixin(Villager.class)
public abstract class VillagerMixin {
    @Inject(
            method = "eatUntilFull",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/SimpleContainer;removeItem(II)Lnet/minecraft/world/item/ItemStack;",
                    shift = At.Shift.BEFORE
            )
    )
    private void create_food_filling$onStartConsume(CallbackInfo ci, @Local ItemStack itemstack, @Local(ordinal = 1) int j, @Local(ordinal = 2) int k) {
        if (!Config.SERVER.enableVillagerFoodEffects.get()) return;
        var villager = (Villager) (Object) this;
        if (!preFilter(itemstack, villager)) return;
        if (k == j && itemstack != null && !itemstack.isEmpty()) {
            NeoForge.EVENT_BUS.post(new VillagerEatingEvent(villager, itemstack));
        }
    }
}
