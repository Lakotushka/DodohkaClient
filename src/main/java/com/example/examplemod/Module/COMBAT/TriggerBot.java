package com.example.examplemod.Module.COMBAT;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class TriggerBot extends Module {
    private Entity entity;

    public TriggerBot() {
        super("TriggerBot", Keyboard.KEY_NONE, Category.COMBAT);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        RayTraceResult objectMouseOver = Minecraft.getMinecraft().objectMouseOver;
        if (objectMouseOver != null && objectMouseOver.typeOfHit == RayTraceResult.Type.ENTITY) {
            this.entity = objectMouseOver.entityHit;
            if (this.entity instanceof EntityPlayer) {

                if (!Minecraft.getMinecraft().player.onGround) {

                    if (Minecraft.getMinecraft().player.getCooledAttackStrength(0.0f) == 1.0f) {
                        Minecraft.getMinecraft().playerController.attackEntity(Minecraft.getMinecraft().player, this.entity);
                        Minecraft.getMinecraft().player.swingArm(EnumHand.MAIN_HAND);
                        Minecraft.getMinecraft().player.resetCooldown();
                    }
                }
            }
        }
    }
}
