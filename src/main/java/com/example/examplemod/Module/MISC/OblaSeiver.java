package com.example.examplemod.Module.MISC;

import com.example.examplemod.Module.Module;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class OblaSeiver extends Module {
    private boolean executed = false;
    private int delay = 0;
    private boolean moved = false;

    public OblaSeiver() {
        super("OblaSeiver", Keyboard.KEY_NONE, Category.MISC);
    }

    @Override
    public void onEnable() {
        executed = false;
        delay = 0;
        moved = false;
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onDisable() {
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.unregister(this);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END || mc.player == null || mc.world == null) return;

        if (!executed) {
            if (mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_SWORD) {
                mc.player.sendChatMessage("/ec open");
                executed = true;
                delay = 6;
            } else {
                toggle();
            }
            return;
        }

        if (delay > 0) {
            delay--;
            return;
        }

        if (!moved && mc.currentScreen instanceof GuiContainer && mc.player.openContainer != null) {
            if (moveSwordToEnderChest()) {
                moved = true;
                mc.player.closeScreen();
                toggle();
            } else {
                toggle();
            }
        }
    }

    private boolean moveSwordToEnderChest() {
        int totalSlots = mc.player.openContainer.inventorySlots.size();
        int playerInventoryStart = totalSlots - 36;

        for (int i = playerInventoryStart; i < totalSlots; i++) {
            ItemStack stack = mc.player.openContainer.getSlot(i).getStack();
            if (stack != null && stack.getItem() == Items.DIAMOND_SWORD) {

                mc.playerController.windowClick(mc.player.openContainer.windowId, i, 0, ClickType.PICKUP, mc.player);

                for (int chestSlot = 0; chestSlot < 54; chestSlot++) {
                    if (!mc.player.openContainer.getSlot(chestSlot).getHasStack()) {
                        mc.playerController.windowClick(mc.player.openContainer.windowId, chestSlot, 0, ClickType.PICKUP, mc.player);
                        return true;
                    }
                }
                mc.playerController.windowClick(mc.player.openContainer.windowId, i, 0, ClickType.PICKUP, mc.player);
                return false;
            }
        }

        return false;
    }
}
