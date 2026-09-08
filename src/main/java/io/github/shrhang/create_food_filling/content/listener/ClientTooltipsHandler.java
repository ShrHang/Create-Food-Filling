package io.github.shrhang.create_food_filling.content.listener;

import io.github.shrhang.create_food_filling.Config;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import java.util.List;

import static io.github.shrhang.create_food_filling.content.util.FoodFillingUtil.isFood;
import static net.minecraft.core.component.DataComponents.POTION_CONTENTS;

@EventBusSubscriber(value = Dist.CLIENT)
public class ClientTooltipsHandler {
    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        if (ModList.get().isLoaded("tooltips_reforged")) return;
        if (!Config.COMMON.isEatingApplyEffects.get() || Config.COMMON.tooltipMode.get() != Config.TooltipMode.CLIENT) return;
        ItemStack itemStack = event.getItemStack();
        if (itemStack.has(POTION_CONTENTS) && isFood(itemStack)) {
            PotionContents contents = itemStack.get(POTION_CONTENTS);
            if (contents != null) {
                List<Component> tooltip = event.getToolTip();
                contents.addPotionTooltip(tooltip::add, 1.0F, 20.0F);
            }
        }
    }
}
