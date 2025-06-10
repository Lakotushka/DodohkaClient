package com.example.examplemod.Utils;

import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class RenderUtil {
    public static void drawRoundedRect(double x, double y, double width, double height, double radius, int color) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

        double x1 = x + width;
        double y1 = y + height;
        float f = (color >> 24 & 0xFF) / 255.0F;
        float f1 = (color >> 16 & 0xFF) / 255.0F;
        float f2 = (color >> 8 & 0xFF) / 255.0F;
        float f3 = (color & 0xFF) / 255.0F;
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glBegin(GL11.GL_POLYGON);

        double degree = Math.PI / 180;
        for (double i = 0; i <= 90; i += 1)
            GL11.glVertex2d(x + radius + Math.sin(i * degree) * radius, y + radius - Math.cos(i * degree) * radius);
        for (double i = 90; i <= 180; i += 1)
            GL11.glVertex2d(x + radius + Math.sin(i * degree) * radius, y1 - radius - Math.cos(i * degree) * radius);
        for (double i = 180; i <= 270; i += 1)
            GL11.glVertex2d(x1 - radius + Math.sin(i * degree) * radius, y1 - radius - Math.cos(i * degree) * radius);
        for (double i = 270; i <= 360; i += 1)
            GL11.glVertex2d(x1 - radius + Math.sin(i * degree) * radius, y + radius - Math.cos(i * degree) * radius);
        GL11.glEnd();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawShadowRect(double x, double y, double width, double height, int radius, int startColor, int endColor) {
        drawRoundedRect(x - 2, y - 2, width + 4, height + 4, radius, new Color(0, 0, 0, 50).getRGB());
        drawRoundedRect(x - 1, y - 1, width + 2, height + 2, radius, new Color(0, 0, 0, 30).getRGB());
        drawRoundedRect(x, y, width, height, radius, new Color(startColor).getRGB());
    }
}