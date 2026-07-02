package com.shrhang.create_food_filling.compat.jei;

import com.shrhang.create_food_filling.Config;
import com.shrhang.create_food_filling.CreateFoodFilling;
import com.shrhang.create_food_filling.util.FoodFillingUtil;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PotionedFoodFillingRecipeMaker {
    public static List<PotionedFoodFillingRecipe> createRecipes(IIngredientManager ingredientManager) {
        List<FluidStack> fluids = ingredientManager.getAllIngredients(ForgeTypes.FLUID_STACK)
                .stream()
                .filter(fluidStack -> !PotionUtils.getAllEffects(fluidStack.getTag()).isEmpty())
                .map(PotionedFoodFillingRecipeMaker::withConfiguredAmount)
                .sorted(Comparator.comparing(PotionedFoodFillingRecipeMaker::fluidSortKey))
                .toList();

        if (fluids.isEmpty()) {
            return List.of();
        }

        List<PotionedFoodFillingRecipe> recipes = new ArrayList<>();
        for (ItemStack input : ingredientManager.getAllIngredients(VanillaTypes.ITEM_STACK)) {
            if (!FoodFillingUtil.isFood(input)) {
                continue;
            }

            List<FluidStack> validFluids = new ArrayList<>();
            List<ItemStack> outputs = new ArrayList<>();
            for (FluidStack fluid : fluids) {
                List<MobEffectInstance> newEffects = FoodFillingUtil.getUpdatedPotions(input, fluid);
                if (newEffects.isEmpty()) {
                    continue;
                }

                ItemStack output = input.copy();
                output.setCount(1);
                List<MobEffectInstance> effects = new ArrayList<>(PotionUtils.getAllEffects(output.getTag()));
                effects.addAll(newEffects);
                PotionUtils.setCustomEffects(output, effects);
                if (Config.COMMON.isPotionTooltip.get() == Config.TooltipMode.SERVER && Config.COMMON.isEatingApplyEffects.get()) {
                    FoodFillingUtil.updateFoodLore(output, input.copy());
                }

                validFluids.add(fluid.copy());
                outputs.add(output);
            }

            if (!validFluids.isEmpty()) {
                ItemStack recipeInput = input.copy();
                recipeInput.setCount(1);
                recipes.add(new PotionedFoodFillingRecipe(recipeId(recipeInput), recipeInput, validFluids, outputs));
            }
        }
        return recipes;
    }

    private static FluidStack withConfiguredAmount(FluidStack fluidStack) {
        FluidStack copy = fluidStack.copy();
        copy.setAmount(Config.COMMON.foodFillingAmount.get());
        return copy;
    }

    private static ResourceLocation recipeId(ItemStack stack) {
        ResourceLocation itemId = ForgeRegistries.ITEMS.getKey(stack.getItem());
        if (itemId == null) {
            itemId = ResourceLocation.fromNamespaceAndPath("minecraft", "air");
        }
        return ResourceLocation.fromNamespaceAndPath(
                CreateFoodFilling.MODID,
                "jei/food_filling/" + itemId.getNamespace() + "/" + itemId.getPath()
        );
    }

    private static String fluidSortKey(FluidStack fluidStack) {
        ResourceLocation fluidId = ForgeRegistries.FLUIDS.getKey(fluidStack.getFluid());
        return fluidId + "|" + fluidStack.getTag();
    }
}
