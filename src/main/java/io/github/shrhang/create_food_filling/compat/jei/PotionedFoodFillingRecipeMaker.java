package io.github.shrhang.create_food_filling.compat.jei;

import io.github.shrhang.create_food_filling.Config;
import io.github.shrhang.create_food_filling.CreateFoodFilling;
import io.github.shrhang.create_food_filling.content.util.FoodFillingUtil;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.neoforge.NeoForgeTypes;
import mezz.jei.api.runtime.IIngredientManager;
import net.createmod.catnip.registry.RegisteredObjectsHelper;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static net.minecraft.core.component.DataComponents.POTION_CONTENTS;

public class PotionedFoodFillingRecipeMaker {
    public static List<PotionedFoodFillingRecipe> createRecipes(IIngredientManager ingredientManager) {
        List<FluidStack> fluids = ingredientManager.getAllIngredients(NeoForgeTypes.FLUID_STACK)
                .stream()
                .filter(fluidStack -> fluidStack.has(POTION_CONTENTS))
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
                PotionContents contents = FoodFillingUtil.getUpdatedPotionContents(input, fluid);
                if (contents == null) {
                    continue;
                }

                ItemStack output = input.copy();
                output.setCount(1);
                output.set(DataComponents.POTION_CONTENTS, contents);
                if (Config.COMMON.tooltipMode.get() == Config.TooltipMode.SERVER && Config.COMMON.isEatingApplyEffects.get()) {
                    FoodFillingUtil.updateFoodLore(output, input.copy());
                }

                validFluids.add(fluid.copy());
                outputs.add(output);
            }

            if (!validFluids.isEmpty()) {
                recipes.add(new PotionedFoodFillingRecipe(recipeId(input), input.copyWithCount(1), validFluids, outputs));
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
        ResourceLocation itemId = RegisteredObjectsHelper.getKeyOrThrow(stack.getItem());
        return ResourceLocation.fromNamespaceAndPath(
                CreateFoodFilling.MODID,
                "jei/food_filling/" + itemId.getNamespace() + "/" + itemId.getPath()
        );
    }

    private static String fluidSortKey(FluidStack fluidStack) {
        ResourceLocation fluidId = RegisteredObjectsHelper.getKeyOrThrow(fluidStack.getFluid());
        return fluidId + "|" + fluidStack.getComponentsPatch();
    }
}
