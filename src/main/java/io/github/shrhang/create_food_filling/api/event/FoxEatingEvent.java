package io.github.shrhang.create_food_filling.api.event;

import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

public class FoxEatingEvent extends LivingEvent {
    private final ItemStack itemStack;

    public FoxEatingEvent(Fox fox, ItemStack itemStack) {
        super(fox);
        this.itemStack = itemStack.copy();
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public Fox getEntity() {
        return (Fox) super.getEntity();
    }
}

