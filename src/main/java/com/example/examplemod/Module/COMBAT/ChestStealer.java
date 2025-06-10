package com.example.examplemod.Module.COMBAT;

import com.example.examplemod.Utils.Timer1;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import com.example.examplemod.Module.Module;
import com.example.examplemod.ExampleMod;
import org.lwjgl.input.Keyboard;
import yea.bushroot.clickgui.Setting;

public class ChestStealer extends Module {
    private Timer1 timer = new Timer1();
    private int lastProcessedSlot = 0;

    public ChestStealer() {
        super("ChestStealer", Keyboard.KEY_NONE, Category.COMBAT);
        ExampleMod.instance.settingsManager.rSetting(new Setting("Delay", this, 43, 1, 100, false));
    }

    @Override
    public void onEnable() {

        super.onEnable();
    }

    @Override
    public void onDisable() {

        super.onDisable();
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        double delay = ExampleMod.instance.settingsManager.getSettingByName("ChestStealer", "Delay").getValDouble();
        if (mc.player.openContainer != null && mc.player.openContainer instanceof ContainerChest) {
            ContainerChest container = (ContainerChest) mc.player.openContainer;
            for (int i = lastProcessedSlot; i < container.inventorySlots.size(); ++i) {
                if (!container.getLowerChestInventory().getStackInSlot(i).isEmpty() && timer.passedMs((long) delay)) {
                    mc.playerController.windowClick(container.windowId, i, 0, ClickType.QUICK_MOVE, mc.player);
                    this.timer.reset();
                    lastProcessedSlot = i + 1;
                    break;
                }
            }

            if (empty(container)) {
                mc.player.closeScreen();
            }
        } else {
            lastProcessedSlot = 0;
        }
    }

    public boolean empty(Container container) {
        for (Slot slot : container.inventorySlots) {
            if (!slot.getStack().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
