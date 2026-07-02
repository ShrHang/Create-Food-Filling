package com.shrhang.create_food_filling.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.shrhang.create_food_filling.Config;
import com.shrhang.create_food_filling.api.event.VillagerEatingEvent;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static com.shrhang.create_food_filling.util.ApplyEffectUtil.preFilter;

@Mixin(Villager.class)
public abstract class VillagerMixin {
    @Redirect(
            method = "eatUntilFull",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/SimpleContainer;removeItem(II)Lnet/minecraft/world/item/ItemStack;"
            )
    )
    private ItemStack create_food_filling$onRemoveItem(SimpleContainer container, int slot, int amount, @Local ItemStack itemStack, @Local(ordinal = 1) int j, @Local(ordinal = 2) int k) {
        if (Config.SERVER.enableVillagerFoodEffects.get()) {
            Villager villager = (Villager) (Object) this;
            if (preFilter(itemStack, villager) && k == j) {
                MinecraftForge.EVENT_BUS.post(new VillagerEatingEvent(villager, itemStack));
            }
        }
        return container.removeItem(slot, amount);
    }
}
