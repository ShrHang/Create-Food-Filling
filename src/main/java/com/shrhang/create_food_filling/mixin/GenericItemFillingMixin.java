package com.shrhang.create_food_filling.mixin;

import com.shrhang.create_food_filling.Config;
import com.shrhang.create_food_filling.util.FoodFillingUtil;
import com.simibubi.create.content.fluids.transfer.GenericItemFilling;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.shrhang.create_food_filling.util.FoodFillingUtil.isFood;
import static net.minecraft.core.component.DataComponents.POTION_CONTENTS;

@Mixin(GenericItemFilling.class)
public abstract class GenericItemFillingMixin {

    /**
     * 允许食物被注入
     */
    @Inject(method = "canItemBeFilled", at = @At("HEAD"), cancellable = true)
    private static void create_food_filling$canFoodBeFilled(Level world, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (!Config.COMMON.isFoodFilling.get()) return;
        if (isFood(stack)) {
            cir.setReturnValue(true);
        }
    }

    /**
     * 获取食物所需的注入量
     */
    @Inject(method = "getRequiredAmountForItem", at = @At("HEAD"), cancellable = true)
    private static void create_food_filling$getFoodPotionAmount(Level world, ItemStack stack, FluidStack availableFluid, CallbackInfoReturnable<Integer> cir) {
        if (!Config.COMMON.isFoodFilling.get()) return;
        if (FoodFillingUtil.getUpdatedPotionContents(stack, availableFluid) != null) {
            cir.setReturnValue(Config.COMMON.foodFillingAmount.get());
        }
    }

    /**
     * 执行注入
     */
    @Inject(method = "fillItem", at = @At("HEAD"), cancellable = true)
    private static void create_food_filling$fillFood(Level world, int requiredAmount, ItemStack stack, FluidStack availableFluid, CallbackInfoReturnable<ItemStack> cir) {
        if (!Config.COMMON.isFoodFilling.get()) return;
        PotionContents newContents = FoodFillingUtil.getUpdatedPotionContents(stack, availableFluid);

        if (newContents != null) {
            availableFluid.shrink(requiredAmount);

            ItemStack filledFood = stack.copy();
            filledFood.setCount(1);
            filledFood.set(POTION_CONTENTS, newContents);

            if (Config.COMMON.isPotionTooltip.get() == Config.TooltipMode.SERVER && Config.COMMON.isEatingApplyEffects.get()) {
                FoodFillingUtil.updateFoodLore(filledFood, stack.copy());
            }

            stack.shrink(1);

            cir.setReturnValue(filledFood);
        }
    }
}
