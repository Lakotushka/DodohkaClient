package com.example.examplemod.Module.COMBAT;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class FastRPD extends Module {

    private boolean isActive = false;
    private long lastClickTime = 0L;
    private static final long CLICK_INTERVAL = 1L;

    public FastRPD() {
        super("FastRPD", Keyboard.KEY_NONE, Category.COMBAT);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        isActive = true;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        isActive = false;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (isActive && Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().world != null) {
            long currentTime = System.currentTimeMillis();

            if (currentTime - lastClickTime >= CLICK_INTERVAL) {
                rightClick();
                lastClickTime = currentTime;
            }
        }
    }

    private void rightClick() {
        Minecraft.getMinecraft().playerController.processRightClick(
                Minecraft.getMinecraft().player,
                Minecraft.getMinecraft().world,
                EnumHand.MAIN_HAND
        );
    }
}