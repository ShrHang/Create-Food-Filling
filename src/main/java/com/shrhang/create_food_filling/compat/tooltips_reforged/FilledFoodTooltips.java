package com.shrhang.create_food_filling.compat.tooltips_reforged;

import com.iafenvoy.integration.entrypoint.EntryPointProvider;
import com.iafenvoy.tooltipsreforged.api.TooltipsReforgeEntrypoint;
import com.iafenvoy.tooltipsreforged.component.PotionEffectsComponent;
import com.shrhang.create_food_filling.Config;
import com.shrhang.create_food_filling.util.FoodFillingUtil;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;

import java.util.List;

@EntryPointProvider(slug = "tooltips_reforged")
public class FilledFoodTooltips implements TooltipsReforgeEntrypoint {
    @Override
    public void appendTooltip(ItemStack itemStack, List<ClientTooltipComponent> list) {
        if (!Config.COMMON.isEatingApplyEffects.get() || Config.COMMON.isPotionTooltip.get() != Config.TooltipMode.CLIENT) return;
        if (!PotionUtils.getAllEffects(itemStack.getTag()).isEmpty() && FoodFillingUtil.isFood(itemStack)) {
            list.add(new PotionEffectsComponent(itemStack, 1.0F));
        }
    }
}
