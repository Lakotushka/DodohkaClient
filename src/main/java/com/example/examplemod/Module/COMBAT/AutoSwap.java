package com.example.examplemod.Module.COMBAT;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Module.Module;
import com.example.examplemod.Utils.Timer;
import net.minecraft.inventory.ClickType;
import org.lwjgl.input.Keyboard;
import yea.bushroot.clickgui.Setting;


public class AutoSwap extends Module {

    private final Timer timer;
    private int hotbarSlot;

    public AutoSwap() {
        super("AutoSwap", Keyboard.KEY_NONE, Category.COMBAT);
        this.timer = new Timer();


        ExampleMod.instance.settingsManager.rSetting(new Setting("HotbarSlot", this, 9, 0, 9, true));
    }

    @Override
    public void onEnable() {

        hotbarSlot = (int) ExampleMod.instance.settingsManager.getSettingByName("AutoSwap", "HotbarSlot").getValDouble();


        mc.playerController.windowClick(0, 6, hotbarSlot - 1, ClickType.SWAP, mc.player);
        toggle();
    }
}