package com.example.examplemod.Module.COMBAT;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.example.examplemod.Module.Module;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class BugHelper extends Module {
    private final Minecraft mc;
    private long lastCloseTime;
    private static final int CLOSE_DELAY = 50;

    public BugHelper() {
        super("BugHelper", Keyboard.KEY_NONE, Category.COMBAT);
        mc = Minecraft.getMinecraft();
        lastCloseTime = 0;

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
    public void onClientTick(ClientTickEvent event) {
        if (!isEnabled() || event.phase != TickEvent.Phase.END) {
            return;
        }

        if (mc.player != null && mc.currentScreen != null) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastCloseTime >= CLOSE_DELAY) {
                mc.player.closeScreen();
                lastCloseTime = currentTime;
            }
        }
    }
}