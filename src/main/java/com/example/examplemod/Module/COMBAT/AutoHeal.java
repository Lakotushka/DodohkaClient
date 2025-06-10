package com.example.examplemod.Module.COMBAT;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import yea.bushroot.clickgui.Setting;

public class AutoHeal extends Module {
    private boolean hasHealed = false;

    public AutoHeal() {
        super("AutoHeal", Keyboard.KEY_NONE, Category.COMBAT);


        ExampleMod.instance.settingsManager.rSetting(new Setting("HP", this, 10.0, 0.5, 20.0, false));
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent e) {
        if (mc.player == null || mc.world == null) return;


        double healthThreshold = ExampleMod.instance.settingsManager.getSettingByName(this.name, "HP").getValDouble();


        float currentHealth = mc.player.getHealth();


        if (currentHealth < healthThreshold && !hasHealed) {

            mc.player.sendChatMessage("/heal");


            hasHealed = true;
        }


        if (currentHealth >= healthThreshold) {
            hasHealed = false;
        }
    }
}