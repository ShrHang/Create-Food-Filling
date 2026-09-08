package io.github.shrhang.create_food_filling.api.event;

import net.minecraft.world.entity.animal.Panda;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

public class PandaEatingEvent extends LivingEvent {
    private final ItemStack itemStack;

    public PandaEatingEvent(Panda panda, ItemStack itemStack) {
        super(panda);
        this.itemStack = itemStack.copy();
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public Panda getEntity() {
        return (Panda) super.getEntity();
    }
}
