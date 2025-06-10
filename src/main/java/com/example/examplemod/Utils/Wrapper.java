package com.example.examplemod.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class Wrapper {
    public static Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }
    public static EntityPlayerSP getPlayer() {
        return getMinecraft().player;
    }
    public static WorldClient getWorld() {
        return getMinecraft().world;
    }
    public static GameSettings getGameSettings() {
        return getMinecraft().gameSettings;
    }
    public static NetHandlerPlayClient getConnection() {
        return getPlayer().connection;
    }
    public static void sendChatMessage(String message) {
        if (getPlayer() != null) {
            getPlayer().sendMessage(new TextComponentString(message));
        }
    }
    public static boolean isInGame() {
        return getPlayer() != null && getWorld() != null;
    }
}