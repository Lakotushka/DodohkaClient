package com.example.examplemod.Module.COMBAT;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Module.Module;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import yea.bushroot.clickgui.Setting;

public class AutoRepair extends Module {
    private int tickTimer;

    public AutoRepair() {
        super("AutoRepairALL", Keyboard.KEY_NONE, Category.COMBAT);
        ExampleMod.instance.settingsManager.rSetting(new Setting("Unbreaking", this, 150, 50, 300, true));
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {

        tickTimer++;


        if (tickTimer == 20) {
            boolean shouldRepair = false;


            for (ItemStack itemStack : this.mc.player.inventory.armorInventory) {
                if (itemStack != null && itemStack.getItemDamage() > ExampleMod.instance.settingsManager.getSettingByName("AutoRepairALL", "Unbreaking").getValDouble()) {
                    shouldRepair = true;
                    break;
                }
            }


            if (shouldRepair) {
                this.mc.player.sendChatMessage("/repair all");
            }

            tickTimer = 0;
        }
    }
}