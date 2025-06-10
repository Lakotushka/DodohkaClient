package com.example.examplemod.Module.MISC;

import com.example.examplemod.Module.Module;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import org.lwjgl.input.Keyboard;

public class BindAPT extends Module {
    public BindAPT() {
        super("BindAPT", Keyboard.KEY_NONE, Module.Category.MISC);
    }

    @Override
    public void onEnable() {
        int prevSlot = mc.player.inventory.currentItem;

        mc.player.connection.sendPacket(new CPacketHeldItemChange(findEPSlot()));
        mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));

        mc.player.inventory.currentItem = prevSlot;
        mc.player.connection.sendPacket(new CPacketHeldItemChange(prevSlot));

        toggle();
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