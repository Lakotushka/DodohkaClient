package com.example.examplemod.Module.COMBAT;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Module.Module;
import com.example.examplemod.Utils.Timer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import yea.bushroot.clickgui.Setting;



public class AutoEXT extends Module {
    private final Timer timer;

    public AutoEXT() {
        super("AutoExt", 0, Module.Category.COMBAT);
        ExampleMod.instance.settingsManager.rSetting(new Setting("Time ext", this, 5000.0, 0.0, 20000.0, true));
        this.timer = new Timer();
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        int timeExt = (int) ExampleMod.instance.settingsManager.getSettingByName(this.name, "Time /ext").getValDouble();

        if (this.mc.player.isBurning() && this.timer.getPassedTimeMs() > (long) timeExt) {
            this.mc.player.sendChatMessage("/ext");
            this.timer.reset();
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}