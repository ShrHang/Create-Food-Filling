package com.shrhang.create_food_filling.api.event;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

public class AnimalFeedingEvent extends LivingEvent {
    private final Player player;
    private InteractionHand hand;
    private ItemStack itemStack;

    public AnimalFeedingEvent(Animal animal, Player player, InteractionHand hand, ItemStack itemStack) {
        super(animal);
        this.player = player;
        this.hand = hand;
        this.itemStack = itemStack.copy();
    }

    public Player getPlayer() {
        return player;
    }

    public InteractionHand getHand() {
        return hand;
    }

    public void setHand(InteractionHand hand) {
        this.hand = hand;
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

