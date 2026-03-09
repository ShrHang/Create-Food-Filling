package com.shrhang.create_food_filling.mixin;

import com.shrhang.create_food_filling.Config;
import com.shrhang.create_food_filling.util.FoodFillingUtil;
import com.simibubi.create.content.fluids.transfer.GenericItemFilling;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.shrhang.create_food_filling.util.FoodFillingUtil.isFood;

@Mixin(GenericItemFilling.class)
public abstract class GenericItemFillingMixin {

    @Inject(method = "canItemBeFilled", at = @At("HEAD"), cancellable = true)
    private static void create_food_filling$canFoodBeFilled(Level world, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (!Config.COMMON.isFoodFilling.get()) return;

        if (isFood(stack)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "getRequiredAmountForItem", at = @At("HEAD"), cancellable = true)
    private static void create_food_filling$getFoodPotionAmount(Level world, ItemStack stack, FluidStack availableFluid, CallbackInfoReturnable<Integer> cir) {
        if (!Config.COMMON.isFoodFilling.get()) return;
        if (FoodFillingUtil.getUpdatedPotionContents(stack, availableFluid) != null) {
            cir.setReturnValue(Config.COMMON.foodFillingAmount.get());
        }
    }

    @Inject(method = "fillItem", at = @At("HEAD"), cancellable = true)
    private static void create_food_filling$fillFood(Level world, int requiredAmount, ItemStack stack, FluidStack availableFluid, CallbackInfoReturnable<ItemStack> cir) {
        if (!Config.COMMON.isFoodFilling.get()) return;
        PotionContents newContents = FoodFillingUtil.getUpdatedPotionContents(stack, availableFluid);

        if (newContents != null) {
            availableFluid.shrink(requiredAmount);

            ItemStack filledFood = stack.copy();
            filledFood.setCount(1);
            filledFood.set(DataComponents.POTION_CONTENTS, newContents);

            if (Config.COMMON.isPotionTooltip.get() == Config.TooltipMode.SERVER)
                FoodFillingUtil.updateFoodLore(filledFood, stack.copy());

            stack.shrink(1);

            cir.setReturnValue(filledFood);
        }
    }
}
