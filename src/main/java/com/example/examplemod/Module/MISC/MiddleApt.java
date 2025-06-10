package com.example.examplemod.Module.MISC;

import com.example.examplemod.Module.Module;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class MiddleApt extends Module {
    private long lastClickTime = 0;

    public MiddleApt() {
        super("MiddleApt", Keyboard.KEY_NONE, Category.MISC);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (mc.player == null || mc.world == null) {
                return;
            }

            if (Mouse.isButtonDown(2)) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastClickTime >= 1500) {
                    lastClickTime = currentTime;
                    int prevSlot = mc.player.inventory.currentItem;

                    mc.player.connection.sendPacket(new CPacketHeldItemChange(findEPSlot()));
                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));

                    mc.player.inventory.currentItem = prevSlot;
                    mc.player.connection.sendPacket(new CPacketHeldItemChange(prevSlot));
                }
            }
        }
    }

    private int findEPSlot() {
        int epSlot = -1;
        if (mc.player.getHeldItemMainhand().getItem() == Items.GLOWSTONE_DUST) {
            epSlot = mc.player.inventory.currentItem;
        }
        if (epSlot == -1) {
            for (int i = 0; i < 9; ++i) {
                if (mc.player.inventory.getStackInSlot(i).getItem() != Items.GLOWSTONE_DUST) continue;
                epSlot = i;
                break;
            }
        }
        return epSlot;
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}