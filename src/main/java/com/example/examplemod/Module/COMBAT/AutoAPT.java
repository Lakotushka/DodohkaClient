package com.example.examplemod.Module.COMBAT;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Module.Module;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import yea.bushroot.clickgui.Setting;

public class AutoAPT extends Module {
    private long lastUsedTime = 0;
    private static final long COOLDOWN = 1505;

    public AutoAPT() {
        super("AutoAPT", Keyboard.KEY_NONE, Module.Category.COMBAT);
        ExampleMod.instance.settingsManager.rSetting(new Setting("Health", this, 19.5, 1.0, 20.0, false));
    }

    @Override
    public void onEnable() {
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onDisable() {
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.unregister(this);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            onUpdate();
        }
    }

    public void onUpdate() {
        if (mc.player == null || mc.world == null) return;

        float healthLimit = (float) ExampleMod.instance.settingsManager.getSettingByName(this.name, "Health").getValDouble();

        if (mc.player.getHealth() + mc.player.getAbsorptionAmount() <= healthLimit) {
            if (System.currentTimeMillis() - lastUsedTime >= COOLDOWN) {
                useGlowstoneDust();
                lastUsedTime = System.currentTimeMillis();
            }
        }
    }

    private void useGlowstoneDust() {
        int prevSlot = mc.player.inventory.currentItem;

        int epSlot = findEPSlot();
        if (epSlot != -1) {
            mc.player.connection.sendPacket(new CPacketHeldItemChange(epSlot));
            mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));

            mc.player.inventory.currentItem = prevSlot;
            mc.player.connection.sendPacket(new CPacketHeldItemChange(prevSlot));
        }
    }

    private int findEPSlot() {
        int epSlot = -1;
        if (mc.player.getHeldItemMainhand().getItem() == Items.GLOWSTONE_DUST) {
            epSlot = mc.player.inventory.currentItem;
        }
        if (epSlot == -1) {
            for (int i = 0; i < 9; ++i) {
                if (mc.player.inventory.getStackInSlot(i).getItem() != Items.GLOWSTONE_DUST) continue;
                epSlot = i;
                break;
            }
        }

        return epSlot;
    }
}