package com.example.examplemod.Module.MISC;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import yea.bushroot.clickgui.Setting;

public class AutoBan extends Module {
    private boolean hasTempBanned = false;

    public AutoBan() {
        super("AutoBan", Keyboard.KEY_NONE, Category.MISC);


        ExampleMod.instance.settingsManager.rSetting(new Setting("HP", this, 10.0, 0.5, 20.0, false));
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent e) {
        if (mc.player == null || mc.world == null) return;


        double healthThreshold = ExampleMod.instance.settingsManager.getSettingByName(this.name, "HP").getValDouble();


        float currentHealth = mc.player.getHealth();


        if (currentHealth <= healthThreshold && !hasTempBanned) {

            mc.player.sendChatMessage(String.format("/tempban %s 1s ПСЖ", mc.player.getName()));


            hasTempBanned = true;


            toggle();
        }


        if (currentHealth > healthThreshold) {
            hasTempBanned = false;
        }
    }
}