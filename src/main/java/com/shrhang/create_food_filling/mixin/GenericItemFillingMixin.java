package com.shrhang.create_food_filling.mixin;

import com.shrhang.create_food_filling.Config;
import com.shrhang.create_food_filling.listener.EatingListener;
import com.simibubi.create.content.fluids.transfer.GenericItemFilling;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

@Mixin(GenericItemFilling.class)
public abstract class GenericItemFillingMixin {

    @Unique
    private static boolean create_food_filling$comparePotionContents(Set<String> seen, MobEffectInstance effect) {
        String id = effect.getEffect().unwrapKey()
                .map(k -> k.location().toString())
                .orElse("unknown");

        String key = id + "|" +
                effect.getAmplifier() + "|" +
                effect.getDuration() + "|" +
                effect.isVisible();

        return !seen.add(key);
    }

    @Unique
    @Nullable
    private static PotionContents create_food_filling$getUpdatedPotionContents(ItemStack food, FluidStack fluid) {
        DataComponentType<PotionContents> POTION_CONTENTS = DataComponents.POTION_CONTENTS;

        if (fluid.getAmount() < Config.COMMON.foodFillingAmount.get() || !fluid.has(POTION_CONTENTS)) return null;
        if (!EatingListener.isFood(food)) return null;

        PotionContents foodContents = food.getOrDefault(POTION_CONTENTS, PotionContents.EMPTY);
        PotionContents fluidContents = fluid.get(POTION_CONTENTS);

        Set<String> seen = new HashSet<>();
        for (MobEffectInstance effect : foodContents.getAllEffects()) {
            create_food_filling$comparePotionContents(seen, effect);
        }
        PotionContents result = foodContents;
        boolean hasChanged = false;

        for (MobEffectInstance effect : fluidContents.getAllEffects()) {
            boolean isDuplicate = create_food_filling$comparePotionContents(seen, effect);

            if (!isDuplicate) {
                result = result.withEffectAdded(effect);
                hasChanged = true;
            }
        }

        return hasChanged ? result : null;
    }

    @Inject(method = "canItemBeFilled", at = @At("HEAD"), cancellable = true)
    private static void create_food_filling$canFoodBeFilled(Level world, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (!Config.COMMON.isFoodFilling.get()) return;

        if (EatingListener.isFood(stack)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "getRequiredAmountForItem", at = @At("HEAD"), cancellable = true)
    private static void create_food_filling$getFoodPotionAmount(Level world, ItemStack stack, FluidStack availableFluid, CallbackInfoReturnable<Integer> cir) {
        if (!Config.COMMON.isFoodFilling.get()) return;
        if (create_food_filling$getUpdatedPotionContents(stack, availableFluid) != null) {
            cir.setReturnValue(Config.COMMON.foodFillingAmount.get());
        }
    }

    @Inject(method = "fillItem", at = @At("HEAD"), cancellable = true)
    private static void create_food_filling$fillFood(Level world, int requiredAmount, ItemStack stack, FluidStack availableFluid, CallbackInfoReturnable<ItemStack> cir) {
        if (!Config.COMMON.isFoodFilling.get()) return;
        PotionContents newContents = create_food_filling$getUpdatedPotionContents(stack, availableFluid);

        if (newContents != null) {
            availableFluid.shrink(requiredAmount);

            ItemStack filledFood = stack.copy();
            filledFood.setCount(1);
            filledFood.set(DataComponents.POTION_CONTENTS, newContents);

            stack.shrink(1);

            cir.setReturnValue(filledFood);
        }
    }
}
