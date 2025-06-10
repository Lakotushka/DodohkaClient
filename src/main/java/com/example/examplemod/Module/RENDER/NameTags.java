package com.example.examplemod.Module.RENDER;

import com.example.examplemod.Module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;

import java.util.Map;

public class NameTags extends Module {
    private final Minecraft mc;
    private final float textScale = 0.02f;

    public NameTags() {
        super("NameTags", Keyboard.KEY_NONE, Category.RENDER);
        this.mc = Minecraft.getMinecraft();
    }

    @SubscribeEvent
    public void onRender(RenderLivingEvent.Specials.Pre event) {
        EntityLivingBase entity = event.getEntity();


        if (!(entity instanceof EntityPlayer) || entity == mc.player || entity.isDead || entity.getHealth() <= 0.0F) {
            return;
        }


        if (isPlayerBehindWall(entity)) {
            return;
        }


        Vec3d pos = new Vec3d(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);


        GlStateManager.pushMatrix();
        GlStateManager.translate(pos.x - mc.getRenderManager().viewerPosX, pos.y - mc.getRenderManager().viewerPosY, pos.z - mc.getRenderManager().viewerPosZ);
        GlStateManager.disableDepth();
        GlStateManager.scale(textScale, textScale, textScale);


        int health = (int) entity.getHealth();
        int armor = entity.getTotalArmorValue();
        String enchantments = getEnchantments(entity);
        String displayText = String.format("%s\nБроня: %d\n%s ❤ %d", enchantments, armor, entity.getName(), health);


        int textWidth = mc.fontRenderer.getStringWidth(displayText);
        int textHeight = mc.fontRenderer.FONT_HEIGHT * 3;
        mc.fontRenderer.drawString(displayText, -textWidth / 2, -textHeight / 2, 0xFFFFFF, false);


        GlStateManager.enableDepth();
        GlStateManager.popMatrix();


        event.setCanceled(true);
    }

    private boolean isPlayerBehindWall(EntityLivingBase entity) {

        return false;
    }

    private String getEnchantments(EntityLivingBase entity) {
        StringBuilder enchantments = new StringBuilder();
        for (ItemStack item : entity.getArmorInventoryList()) {
            if (!item.isEmpty()) {
                Map<Enchantment, Integer> enchants = EnchantmentHelper.getEnchantments(item);
                for (Map.Entry<Enchantment, Integer> entry : enchants.entrySet()) {
                    Enchantment enchantment = entry.getKey();
                    int level = entry.getValue();
                    enchantments.append(enchantment.getTranslatedName(level)).append(" ");
                }
            }
        }
        return enchantments.toString().trim();
    }
}