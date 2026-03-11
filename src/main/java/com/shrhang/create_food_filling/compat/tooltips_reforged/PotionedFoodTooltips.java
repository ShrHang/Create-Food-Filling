package com.shrhang.create_food_filling.compat.tooltips_reforged;

import com.iafenvoy.integration.entrypoint.EntryPointProvider;
import com.iafenvoy.tooltipsreforged.api.TooltipsReforgeEntrypoint;
import com.iafenvoy.tooltipsreforged.component.PotionEffectsComponent;
import com.shrhang.create_food_filling.Config;
import com.shrhang.create_food_filling.util.FoodFillingUtil;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.ItemStack;

import java.util.List;

import static net.minecraft.core.component.DataComponents.POTION_CONTENTS;

@EntryPointProvider(slug = "tooltips_reforged")
public class PotionedFoodTooltips implements TooltipsReforgeEntrypoint {
    @Override
    public void appendTooltip(ItemStack stack, List<ClientTooltipComponent> components, RegistryAccess registry) {
        if (!Config.COMMON.isEatingApplyEffects.get() || Config.COMMON.isPotionTooltip.get() != Config.TooltipMode.CLIENT) return;
        if (stack.has(POTION_CONTENTS) && FoodFillingUtil.isFood(stack)) {
            components.add(new PotionEffectsComponent(stack, 1.0F));
        }
    }
}
