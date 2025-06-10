package com.example.examplemod.Module.MISC;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Module.Module;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import yea.bushroot.clickgui.Setting;

public class AutoLoot extends Module {
    private double cooldown;
    private long lastSteal;
    private boolean isStealingItems;
    private int currentSlot;

    public AutoLoot() {
        super("AutoLoot", Keyboard.KEY_NONE, Category.MISC);
        
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (mc.player.openContainer instanceof ContainerChest && ((ContainerChest)mc.player.openContainer).getLowerChestInventory().getSizeInventory() == 54) {
            ContainerChest container = (ContainerChest)mc.player.openContainer;
            if (System.currentTimeMillis() - this.lastSteal >= 0L || !this.isStealingItems) {
                for(this.isStealingItems = true; this.currentSlot < container.getLowerChestInventory().getSizeInventory(); ++this.currentSlot) {
                    ItemStack stack = container.getLowerChestInventory().getStackInSlot(this.currentSlot);
                    if (!stack.isEmpty() && (stack.getItem() == Items.DIAMOND_HELMET || stack.getItem() == Items.DIAMOND_CHESTPLATE ||
                            stack.getItem() == Items.DIAMOND_LEGGINGS || stack.getItem() == Items.DIAMOND_BOOTS ||
                            stack.getItem() == Items.DIAMOND_SWORD ||
                            stack.getItem() == Items.RECORD_STAL)) {
                        mc.playerController.windowClick(container.windowId, this.currentSlot, 0, ClickType.QUICK_MOVE, mc.player);
                        

                        this.lastSteal = System.currentTimeMillis();
                        ++this.currentSlot;
                        return;
                    }
                }

                this.isStealingItems = false;
                this.currentSlot = 0;
            }
        } else {
            this.isStealingItems = false;
            this.currentSlot = 0;
        }
    }
}