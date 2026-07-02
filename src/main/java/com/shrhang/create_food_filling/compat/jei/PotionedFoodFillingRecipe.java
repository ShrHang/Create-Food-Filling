package com.shrhang.create_food_filling.compat.jei;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;

public record PotionedFoodFillingRecipe(
        ResourceLocation id,
        ItemStack input,
        List<FluidStack> fluids,
        List<ItemStack> outputs
) {
    public ItemStack getOutputFor(FluidStack fluidStack) {
        for (int i = 0; i < fluids.size(); i++) {
            if (FluidStack.isSameFluidSameComponents(fluids.get(i), fluidStack)) {
                return outputs.get(i);
            }
        }
        return outputs.isEmpty() ? ItemStack.EMPTY : outputs.getFirst();
    }
}
