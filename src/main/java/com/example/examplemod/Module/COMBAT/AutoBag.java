package com.example.examplemod.Module.COMBAT;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Module.Module;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.network.play.client.CPacketCloseWindow;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import org.lwjgl.input.Keyboard;
import yea.bushroot.clickgui.Setting;

public class AutoBag extends Module {
    private int ticktimer = 0;

    public AutoBag() {
        super("AutoBug", Keyboard.KEY_NONE, Category.COMBAT);
        ExampleMod.instance.settingsManager.rSetting(new Setting("time", this, 15.0D, 1.0D, 17.0D, true));
    }

    @SubscribeEvent
    public void onUpdate(ClientTickEvent event) {
        if (this.isEnabled()) {
            ++this.ticktimer;
            if (!((double)this.ticktimer < ExampleMod.instance.settingsManager.getSettingByName(this.getName(), "time").getValDouble())) {
                if (!(mc.currentScreen instanceof GuiInventory) && !(mc.currentScreen instanceof GuiChest)) {
                    if (mc.getCurrentServerData() != null && mc.getConnection() != null) {
                        mc.getConnection().sendPacket(new CPacketCloseWindow(0));
                    }

                    if ((double)this.ticktimer >= ExampleMod.instance.settingsManager.getSettingByName(this.getName(), "time").getValDouble()) {
                        this.ticktimer = 0;
                    }
                }
            }
        }
    }
}