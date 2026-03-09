package com.shrhang.create_food_filling.listener;

import com.shrhang.create_food_filling.Config;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import java.util.List;

import static com.shrhang.create_food_filling.util.FoodFillingUtil.isFood;

@EventBusSubscriber(value = Dist.CLIENT)
public class ClientTooltipsHandler {
    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        if (!Config.COMMON.isEatingApplyEffects.get() && Config.COMMON.isPotionTooltip.get() != Config.TooltipMode.CLIENT) return;
        ItemStack itemStack = event.getItemStack();
        if (itemStack.has(DataComponents.POTION_CONTENTS) && isFood(itemStack)) {
            PotionContents contents = itemStack.get(DataComponents.POTION_CONTENTS);
            if (contents != null) {
                List<Component> tooltip = event.getToolTip();
                contents.addPotionTooltip(tooltip::add, 1.0F, 20.0F);
            }
        }
    }
}
