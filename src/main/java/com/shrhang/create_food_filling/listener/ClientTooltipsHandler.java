package com.shrhang.create_food_filling.listener;

import com.shrhang.create_food_filling.Config;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static com.shrhang.create_food_filling.util.FoodFillingUtil.isFood;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ClientTooltipsHandler {
    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        if (ModList.get().isLoaded("tooltips_reforged")) return;
        if (!Config.COMMON.isEatingApplyEffects.get() || Config.COMMON.isPotionTooltip.get() != Config.TooltipMode.CLIENT) return;
        ItemStack itemStack = event.getItemStack();
        List<MobEffectInstance> effects = PotionUtils.getAllEffects(itemStack.getTag());
        if (!effects.isEmpty() && isFood(itemStack)) {
            PotionUtils.addPotionTooltip(effects, event.getToolTip(), 1.0F);
        }
    }
}
