package io.github.shrhang.create_food_filling.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.shrhang.create_food_filling.Config;
import io.github.shrhang.create_food_filling.api.event.VillagerEatingEvent;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static io.github.shrhang.create_food_filling.content.util.ApplyEffectUtil.preFilter;

@Mixin(Villager.class)
public abstract class VillagerMixin {
    @Redirect(
            method = "eatUntilFull",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/SimpleContainer;removeItem(II)Lnet/minecraft/world/item/ItemStack;"
            )
    )
    private ItemStack create_food_filling$onRemoveItem(SimpleContainer container, int slot, int amount, @Local ItemStack itemstack, @Local(ordinal = 1) int j, @Local(ordinal = 2) int k) {
        if (Config.SERVER.enableVillagerFoodEffects.get()) {
            var villager = (Villager) (Object) this;
            if (!preFilter(itemstack, villager)) return container.removeItem(slot, amount);
            if (k == j && itemstack != null && !itemstack.isEmpty()) {
                NeoForge.EVENT_BUS.post(new VillagerEatingEvent(villager, itemstack));
            }
        }
        return container.removeItem(slot, amount);
    }
}
