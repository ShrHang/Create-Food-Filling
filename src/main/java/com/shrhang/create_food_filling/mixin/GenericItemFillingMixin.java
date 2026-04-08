package com.shrhang.create_food_filling.mixin;

import com.shrhang.create_food_filling.Config;
import com.shrhang.create_food_filling.util.FoodFillingUtil;
import com.simibubi.create.content.fluids.transfer.GenericItemFilling;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

import static com.shrhang.create_food_filling.util.FoodFillingUtil.isFood;

@Mixin(GenericItemFilling.class)
public class GenericItemFillingMixin {

    @Unique
    private static final Logger create_food_filling$LOGGER = LogManager.getLogger();

    @Inject(method = "canItemBeFilled", at = @At("HEAD"), cancellable = true, remap = false)
    private static void create_food_filling$canFoodBeFilled(Level world, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (!Config.COMMON.isFoodFilling.get()) return;

        if (isFood(stack)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "getRequiredAmountForItem", at = @At("HEAD"), cancellable = true, remap = false)
    private static void create_food_filling$getFoodPotionAmount(Level world, ItemStack stack, FluidStack availableFluid, CallbackInfoReturnable<Integer> cir) {
        if (!Config.COMMON.isFoodFilling.get()) return;
        if (!FoodFillingUtil.getUpdatedPotions(stack, availableFluid).isEmpty()) {
            cir.setReturnValue(Config.COMMON.foodFillingAmount.get());
        }
    }

    @Inject(method = "fillItem", at = @At("HEAD"), cancellable = true, remap = false)
    private static void create_food_filling$fillFood(Level world, int requiredAmount, ItemStack stack, FluidStack availableFluid, CallbackInfoReturnable<ItemStack> cir) {
        if (!Config.COMMON.isFoodFilling.get()) return;
        List<MobEffectInstance> newEffects = FoodFillingUtil.getUpdatedPotions(stack, availableFluid);
        if (!newEffects.isEmpty()) {
            availableFluid.shrink(requiredAmount);

            ItemStack filledFood = stack.copy();
            filledFood.setCount(1);
            ItemStack oldFood = filledFood.copy(); // 留作对比旧Lore

            if (Config.COMMON.isPotionTooltip.get() == Config.TooltipMode.SERVER && Config.COMMON.isEatingApplyEffects.get()) {
                // 1. 将新的药水效果与旧的合并，保存回物品
                List<MobEffectInstance> oldEffects = PotionUtils.getAllEffects(filledFood.getTag());
                List<MobEffectInstance> customEffects = new java.util.ArrayList<>(oldEffects);
                customEffects.addAll(newEffects);
                PotionUtils.setCustomEffects(filledFood, customEffects);

                // 2. 调用更新 Lore 的封装方法
                FoodFillingUtil.updateFoodLore(filledFood, oldFood);
            }

            stack.shrink(1);

            cir.setReturnValue(filledFood);
        }

    }
}
