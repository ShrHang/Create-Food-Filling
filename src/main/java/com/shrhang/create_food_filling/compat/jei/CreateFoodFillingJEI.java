package com.shrhang.create_food_filling.compat.jei;

import com.simibubi.create.AllBlocks;
import com.shrhang.create_food_filling.CreateFoodFilling;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class CreateFoodFillingJEI implements IModPlugin {
    private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(CreateFoodFilling.MODID, "jei_plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        IDrawable icon = guiHelper.createDrawableItemLike(AllBlocks.SPOUT.get());
        IDrawable slot = guiHelper.getSlotDrawable();
        registration.addRecipeCategories(new PotionedFoodFillingCategory(icon, slot));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(
                PotionedFoodFillingCategory.TYPE,
                PotionedFoodFillingRecipeMaker.createRecipes(registration.getIngredientManager())
        );
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(AllBlocks.SPOUT.get(), PotionedFoodFillingCategory.TYPE);
    }
}
