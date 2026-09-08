package io.github.shrhang.create_food_filling.compat.jei;

import io.github.shrhang.create_food_filling.Config;
import io.github.shrhang.create_food_filling.CreateFoodFilling;
import com.simibubi.create.compat.jei.category.animations.AnimatedSpout;
import com.simibubi.create.content.fluids.potion.PotionFluidHandler;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.neoforge.NeoForgeTypes;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class PotionedFoodFillingCategory implements IRecipeCategory<PotionedFoodFillingRecipe> {
    public static final RecipeType<PotionedFoodFillingRecipe> TYPE = RecipeType.create(
            CreateFoodFilling.MODID,
            "food_filling",
            PotionedFoodFillingRecipe.class
    );

    private static final String FLUID_SLOT = "potion_fluid";
    private static final String OUTPUT_SLOT = "output";
    private static final int WIDTH = 177;
    private static final int HEIGHT = 70;

    private final AnimatedSpout spout = new AnimatedSpout();
    private final IDrawable icon;
    private final IDrawable slot;

    public PotionedFoodFillingCategory(IDrawable icon, IDrawable slot) {
        this.icon = icon;
        this.slot = slot;
    }

    @Override
    public RecipeType<PotionedFoodFillingRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("create_food_filling.recipe.food_filling");
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, PotionedFoodFillingRecipe recipe, IFocusGroup focuses) {
        builder
                .addSlot(RecipeIngredientRole.INPUT, 27, 51)
                .setBackground(slot, -1, -1)
                .addItemStack(recipe.input());

        builder
                .addSlot(RecipeIngredientRole.INPUT, 27, 32)
                .setSlotName(FLUID_SLOT)
                .setBackground(slot, -1, -1)
                .setFluidRenderer(Config.COMMON.foodFillingAmount.get(), false, 16, 16)
                .addIngredients(NeoForgeTypes.FLUID_STACK, recipe.fluids())
                .addRichTooltipCallback(PotionedFoodFillingCategory::addPotionFluidTooltip);

        builder
                .addSlot(RecipeIngredientRole.OUTPUT, 132, 51)
                .setSlotName(OUTPUT_SLOT)
                .setBackground(slot, -1, -1)
                .addIngredients(VanillaTypes.ITEM_STACK, recipe.outputs());
    }

    @Override
    public void onDisplayedIngredientsUpdate(PotionedFoodFillingRecipe recipe, List<IRecipeSlotDrawable> recipeSlots, IFocusGroup focuses) {
        Optional<FluidStack> displayedFluid = findSlot(recipeSlots, FLUID_SLOT)
                .flatMap(slot -> slot.getDisplayedIngredient(NeoForgeTypes.FLUID_STACK));
        Optional<IRecipeSlotDrawable> outputSlot = findSlot(recipeSlots, OUTPUT_SLOT);

        if (displayedFluid.isEmpty() || outputSlot.isEmpty()) {
            return;
        }

        outputSlot.get()
                .createDisplayOverrides()
                .addItemStack(recipe.getOutputFor(displayedFluid.get()));
    }

    @Override
    public void draw(PotionedFoodFillingRecipe recipe, mezz.jei.api.gui.ingredient.IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
        AllGuiTextures.JEI_SHADOW.render(graphics, 62, 57);
        AllGuiTextures.JEI_DOWN_ARROW.render(graphics, 126, 29);

        FluidStack displayedFluid = recipeSlotsView.findSlotByName(FLUID_SLOT)
                .flatMap(slot -> slot.getDisplayedIngredient(NeoForgeTypes.FLUID_STACK))
                .orElseGet(() -> recipe.fluids().getFirst());
        spout.withFluids(List.of(displayedFluid))
                .draw(graphics, getWidth() / 2 - 13, 22);
    }

    @Override
    public @Nullable ResourceLocation getRegistryName(PotionedFoodFillingRecipe recipe) {
        return recipe.id();
    }

    private static Optional<IRecipeSlotDrawable> findSlot(List<IRecipeSlotDrawable> slots, String name) {
        return slots.stream()
                .filter(slot -> slot.getSlotName().map(name::equals).orElse(false))
                .findFirst();
    }

    private static void addPotionFluidTooltip(IRecipeSlotView view, ITooltipBuilder tooltip) {
        Optional<FluidStack> displayed = view.getDisplayedIngredient(NeoForgeTypes.FLUID_STACK);
        if (displayed.isEmpty()) {
            return;
        }

        List<Component> potionTooltip = new java.util.ArrayList<>();
        PotionFluidHandler.addPotionTooltip(displayed.get(), potionTooltip::add, 1);
        tooltip.addAll(potionTooltip);
    }
}
