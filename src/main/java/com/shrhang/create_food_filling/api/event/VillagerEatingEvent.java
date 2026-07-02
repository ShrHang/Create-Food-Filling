package com.shrhang.create_food_filling.api.event;

import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;

public class VillagerEatingEvent extends LivingEvent {
    private final ItemStack itemStack;

    public VillagerEatingEvent(Villager villager, ItemStack itemStack) {
        super(villager);
        this.itemStack = itemStack.copy();
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public Villager getEntity() {
        return (Villager) super.getEntity();
    }
}
