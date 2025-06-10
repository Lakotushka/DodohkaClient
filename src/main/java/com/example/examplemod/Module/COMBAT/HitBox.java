package com.example.examplemod.Module.COMBAT;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Module.Module;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import yea.bushroot.clickgui.Setting;

public class HitBox extends Module {
    public HitBox() {
        super("HitBox", 0, Module.Category.COMBAT);
        ExampleMod.instance.settingsManager.rSetting(new Setting("Size", this, 1.0, 0.1, 4.0, false));
    }

    @SubscribeEvent
    public void onUpdate(RenderWorldLastEvent e) {
        if (this.toggled) {
            float size = (float) ExampleMod.instance.settingsManager.getSettingByName(this.name, "Size").getValDouble();
            for (EntityPlayer player : this.mc.world.playerEntities) {
                if (player == null || player == this.mc.player) continue;


                player.setEntityBoundingBox(new AxisAlignedBB(
                        player.posX - size,
                        player.getEntityBoundingBox().minY,
                        player.posZ - size,
                        player.posX + size,
                        player.getEntityBoundingBox().maxY,
                        player.posZ + size
                ));
            }
        } else {
            for (EntityPlayer player : this.mc.world.playerEntities) {
                if (player == null || player == this.mc.player) continue;


                player.setEntityBoundingBox(new AxisAlignedBB(
                        player.posX - 0.3,
                        player.getEntityBoundingBox().minY,
                        player.posZ - 0.3,
                        player.posX + 0.3,
                        player.getEntityBoundingBox().maxY,
                        player.posZ + 0.3
                ));
            }
        }
    }

    @Override
    public void onDisable() {

    }
}