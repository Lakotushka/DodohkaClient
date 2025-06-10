package com.example.examplemod.UI;

import com.example.examplemod.Client;
import com.example.examplemod.Module.CLIENT.Panic;
import com.example.examplemod.Module.Module;
import font.FontUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

import static net.minecraft.client.gui.Gui.drawRect;

public class ui {
    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post e) {
        switch (e.getType()) {
            case TEXT:
                if (!Panic.isPanic) {
                    int y = 12;
                    final int[] counter = {1};

                    Minecraft mc = Minecraft.getMinecraft();
                    FontRenderer fr = mc.fontRenderer;
                    ScaledResolution sr = new ScaledResolution(mc);

                    int posY = 12;

                    try {
                        String text = Client.cName + "§f | " + mc.getSession().getUsername() + " | " + Objects.requireNonNull(mc.getCurrentServerData()).serverIP +
                                " | FPS: §a" + Minecraft.getDebugFPS() + "§f | Ping: §a" + mc.getCurrentServerData().pingToServer;

                        drawRect(6, 6, FontUtils.normal.getStringWidth(text) > 191 ? (int) (FontUtils.normal.getStringWidth(text) + 15) : 201, 15, new Color(0x151515).hashCode());
                        drawRect(6, 6, FontUtils.normal.getStringWidth(text) > 191 ? (int) (FontUtils.normal.getStringWidth(text) + 15) : 201, 5, rainbow(250));

                        FontUtils.normal.drawString(text, 10, posY, -1);
                    } catch (Exception ex) {
                        drawRect(6, 6, 201, 15, new Color(0x151515).hashCode());
                        drawRect(6, 6, 201, 5, rainbow(250));


                    }


                    ArrayList<Module> enabledMods = new ArrayList<>();

                    for (Module module : Client.modules) {
                        if (module.toggled) {
                            enabledMods.add(module);
                        }
                    }

                    enabledMods.sort((module1, module2) -> mc.fontRenderer.getStringWidth(module2.getName()) - mc.fontRenderer.getStringWidth(module1.getName()));

                    for (PotionEffect activePotionEffect : mc.player.getActivePotionEffects()) {
                        if (activePotionEffect.getPotion().isBeneficial()) {
                            y = 37;
                        } if (activePotionEffect.getPotion().isBadEffect()) {
                            y = 37 * 2;
                        }
                    }

                    for (Module module : enabledMods) {
                        Gui.drawRect(sr.getScaledWidth(), y, sr.getScaledWidth() - 3,
                                y + 11, rainbow(counter[0] * 250));

                        fr.drawStringWithShadow(module.name, sr.getScaledWidth() - 5 - fr.getStringWidth(module.name),
                                y, rainbow(counter[0] * 250));
                        y += 11;
                        counter[0]++;
                    }
                } else {
                    Minecraft.getMinecraft().fontRenderer.drawString("", 6, 6, -1);
                }
            default:
                break;
        }
    }

    public static int rainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.5f, 1f).getRGB();
    }
}
