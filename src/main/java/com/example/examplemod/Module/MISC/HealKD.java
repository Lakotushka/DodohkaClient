package com.example.examplemod.Module.MISC;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Module.Module;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import yea.bushroot.clickgui.Setting;

import java.awt.Color;

public class HealKD extends Module {
    private int cooldown = 0;
    private boolean isReady = true;

    public HealKD() {
        super("HealKD", Keyboard.KEY_NONE, Category.MISC);
        ExampleMod.instance.settingsManager.rSetting(new Setting("X Position", this, 400, 0, 1000, true));
        ExampleMod.instance.settingsManager.rSetting(new Setting("Y Position", this, 220, 0, 1000, true));
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (!this.isEnabled()) return;

        if (event.phase == TickEvent.Phase.END) {
            if (cooldown > 0) {
                cooldown--;
                if (cooldown == 0) {
                    isReady = true;
                }
            }
        }
    }

    @SubscribeEvent
    public void onChatMessage(ClientChatReceivedEvent event) {
        if (!this.isEnabled()) return;

        String message = event.getMessage().getFormattedText();
        String unformattedMessage = event.getMessage().getUnformattedText();

        if (message.contains("Вы были исцелены") ||
                unformattedMessage.contains("Вы были исцелены")) {
            if (isReady) {
                cooldown = 1205;
                isReady = false;
            }
        }
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (!this.isEnabled() || event.getType() != RenderGameOverlayEvent.ElementType.TEXT) {
            return;
        }

        ScaledResolution sr = new ScaledResolution(mc);
        int posX = (int) ExampleMod.instance.settingsManager.getSettingByName(this.name, "X Position").getValDouble();
        int posY = (int) ExampleMod.instance.settingsManager.getSettingByName(this.name, "Y Position").getValDouble();


        String heartIcon = "❤";
        mc.fontRenderer.drawStringWithShadow(heartIcon, posX, posY, Color.RED.getRGB());

        
        String timerText = isReady ? "Ready" : String.format("%.1f", cooldown / 20f);
        int textX = posX + 12;
        int textY = posY;

        int color = isReady ? Color.GREEN.getRGB() : Color.WHITE.getRGB();
        mc.fontRenderer.drawStringWithShadow(timerText, textX, textY, color);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        cooldown = 0;
        isReady = true;
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}