package io.github.shrhang.create_food_filling.api.event;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

public class AnimalFeedingEvent extends LivingEvent {
    public final Player player;
    public InteractionHand hand;
    private ItemStack itemStack;

    public AnimalFeedingEvent(Animal animal, Player player, InteractionHand hand, ItemStack itemStack) {
        super(animal);
        this.player = player;
        this.hand = hand;
        this.itemStack = itemStack.copy();
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack.copy();
    }

    @Override
    public Animal getEntity() {
        return (Animal) super.getEntity();
    }
}

