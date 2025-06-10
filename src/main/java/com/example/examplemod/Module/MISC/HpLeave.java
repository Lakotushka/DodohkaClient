package com.example.examplemod.Module.MISC;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import yea.bushroot.clickgui.Setting;

public class HpLeave extends Module {
    private final Minecraft mc;
    private Thread spamThread;
    private boolean isSpamming;

    public HpLeave() {
        super("HpLeave", Keyboard.KEY_NONE, Category.MISC);
        this.mc = Minecraft.getMinecraft();


        ExampleMod.instance.settingsManager.rSetting(new Setting("Packets Amount", this, 150000, 100000, 150000, true));
        ExampleMod.instance.settingsManager.rSetting(new Setting("Health Threshold", this, 6.0, 1.0, 20.0, false));
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (!isEnabled() || mc.player == null || mc.getConnection() == null) return;

        float currentHealth = mc.player.getHealth();
        float healthThreshold = (float) ExampleMod.instance.settingsManager
                .getSettingByName(this.getName(), "Health Threshold")
                .getValDouble();

        if (currentHealth <= healthThreshold && !isSpamming) {
            startSpamming();
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        isSpamming = false;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if (spamThread != null) {
            spamThread.interrupt();
            spamThread = null;
        }
        isSpamming = false;
    }

    private void startSpamming() {
        if (isSpamming) return;

        isSpamming = true;
        spamThread = new Thread(() -> {
            try {
                if (mc.getConnection() == null) return;

                double x = mc.player.posX;
                double y = mc.player.posY;
                double z = mc.player.posZ;
                float yaw = mc.player.rotationYaw;
                float pitch = mc.player.rotationPitch;

                int packetsAmount = (int) ExampleMod.instance.settingsManager
                        .getSettingByName(this.getName(), "Packets Amount")
                        .getValDouble();

                for (int i = 140000; i < packetsAmount && isEnabled(); i++) {
                    mc.getConnection().sendPacket(
                            new CPacketPlayer.PositionRotation(x, y, z, yaw, pitch, true)
                    );
                }
            } finally {
                isSpamming = false;
            }
        });
        spamThread.start();
    }
}