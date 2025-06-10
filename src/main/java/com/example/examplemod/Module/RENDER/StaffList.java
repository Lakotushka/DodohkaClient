package com.example.examplemod.Module.RENDER;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Module.Module;
import com.example.examplemod.UI.ui;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import yea.bushroot.clickgui.Setting;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StaffList extends Module {
    private static final Set<String> KNOWN_STAFF = new HashSet<>(Arrays.asList(
            "owerruslan", "AmKaL09", "balalayka26", "TwoY_KaLash",
            "RAY_Morbit", "Aph3x", "BALeP6RHKa_CBeTa", "Co6yTblJIHuK",
            "_pokoritel_", "W1ZARDXXX", "NickFanta13", "Leonid2281",
            "XpaNiTeJlb_LyHbl", "spinogriz01", "endermanxxxx", "_n1k1t1n_",
            "ANIA_Deiz", "RenatBenzow", "Loni4Ka", "Tsarevna", "Ayk121",
            "Kasiil", "I_c1El_Ded"
    ));

    private final List<Staff> staffPlayers = new ArrayList<>();
    private final Pattern namePattern = Pattern.compile("^\\w{3,16}$");
    private final Pattern prefixMatches = Pattern.compile(".[I](mod|der|adm|help|wne|хелп|адм|поддержка|кура|own|taf|curat|dev|supp|yt|сотруд).[/I]");
    private float width = 90;
    private float height = 35;

    public StaffList() {
        super("StaffList", Keyboard.KEY_NONE, Category.RENDER);
        ExampleMod.instance.settingsManager.rSetting(new Setting("X Position", this, 2, 0, 1000, true));
        ExampleMod.instance.settingsManager.rSetting(new Setting("Y Position", this, 2, 0, 1000, true));
    }

    public static void drawRoundedRect(double x, double y, double width, double height, double radius, int color) {
        double x1 = x + width;
        double y1 = y + height;

        float f = (color >> 24 & 0xFF) / 255.0F;
        float f1 = (color >> 16 & 0xFF) / 255.0F;
        float f2 = (color >> 8 & 0xFF) / 255.0F;
        float f3 = (color & 0xFF) / 255.0F;

        GL11.glPushAttrib(0);
        GL11.glScaled(0.5, 0.5, 0.5);

        x *= 2;
        y *= 2;
        x1 *= 2;
        y1 *= 2;

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);

        GL11.glBegin(GL11.GL_POLYGON);

        for (int i = 0; i <= 90; i += 3) {
            GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y + radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D);
        }

        for (int i = 90; i <= 180; i += 3) {
            GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y1 - radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D);
        }

        for (int i = 0; i <= 90; i += 3) {
            GL11.glVertex2d(x1 - radius + Math.sin(i * Math.PI / 180.0D) * radius, y1 - radius + Math.cos(i * Math.PI / 180.0D) * radius);
        }

        for (int i = 90; i <= 180; i += 3) {
            GL11.glVertex2d(x1 - radius + Math.sin(i * Math.PI / 180.0D) * radius, y + radius + Math.cos(i * Math.PI / 180.0D) * radius);
        }

        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        GL11.glScaled(2, 2, 2);
        GL11.glPopAttrib();
        GL11.glColor4f(1, 1, 1, 1);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (mc.world == null || mc.player == null) return;

        staffPlayers.clear();

        for (ScorePlayerTeam team : mc.world.getScoreboard().getTeams().stream()
                .sorted(Comparator.comparing(Team::getName))
                .collect(Collectors.toList())) {

            String name = team.getMembershipCollection().toString().replaceAll("[\\[\\]]", "");
            boolean vanish = true;

            for (NetworkPlayerInfo info : mc.player.connection.getPlayerInfoMap()) {
                if (info.getGameProfile().getName().equals(name)) {
                    vanish = false;
                    break;
                }
            }

            if (namePattern.matcher(name).matches() && !name.equals(mc.player.getName())) {
                if (!vanish) {
                    if (prefixMatches.matcher(team.getPrefix().toLowerCase()).matches() || KNOWN_STAFF.contains(name)) {
                        Staff staff = new Staff(team.getPrefix(), name, false, Status.NONE);
                        staffPlayers.add(staff);
                    }
                }
                if (vanish && !team.getPrefix().isEmpty()) {
                    Staff staff = new Staff(team.getPrefix(), name, true, Status.VANISHED);
                    staffPlayers.add(staff);
                }
            }
        }
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        if (!this.isEnabled()) return;
        if (event.getType() != RenderGameOverlayEvent.ElementType.TEXT) return;
        if (mc.player == null || mc.world == null) return;

        double posXDouble = ExampleMod.instance.settingsManager.getSettingByName(this.name, "X Position").getValDouble();
        double posYDouble = ExampleMod.instance.settingsManager.getSettingByName(this.name, "Y Position").getValDouble();
        int posX = (int) posXDouble;
        int posY = (int) posYDouble;
        int padding = 5;

        String headerText = "Staff List";
        int headerWidth = mc.fontRenderer.getStringWidth(headerText);
        int maxWidth = headerWidth;
        int totalHeight = padding * 2 + 10;

        for (Staff staff : staffPlayers) {
            String text = staff.name;
            int lineWidth = mc.fontRenderer.getStringWidth(text) + padding * 3;
            maxWidth = Math.max(maxWidth, lineWidth);
            totalHeight += 12;
        }

        width = Math.max(maxWidth + padding * 2, 110);
        height = totalHeight + padding;

        drawRoundedRect(posX - 1, posY - 1, width + 2, height + 2, 5,
                ExampleMod.instance.settingsManager.getSettingByName("ClickGUI", "Rainbow").getValBoolean()
                        ? ui.rainbow(300)
                        : new Color(0x36D003).getRGB());

        drawRoundedRect(posX, posY, width, height, 4,
                new Color(20, 20, 20).getRGB());

        GlStateManager.enableBlend();
        mc.fontRenderer.drawStringWithShadow(
                headerText,
                posX + width / 2 - headerWidth / 2,
                posY + padding,
                -1
        );

        int currentY = posY + padding * 3;

        for (Staff staff : staffPlayers) {
            int nameColor;
            if (staff.isSpec) {
                nameColor = new Color(255, 0, 0).getRGB();
            } else {
                nameColor = new Color(0, 255, 0).getRGB();
            }

            mc.fontRenderer.drawStringWithShadow(
                    staff.name,
                    posX + padding,
                    currentY,
                    nameColor
            );

            if (staff.status == Status.VANISHED) {
                mc.fontRenderer.drawStringWithShadow(
                        "V",
                        posX + width - padding - mc.fontRenderer.getStringWidth("V"),
                        currentY,
                        new Color(255, 0, 0).getRGB()
                );
            }

            currentY += 12;
        }
        GlStateManager.disableBlend();
    }

    private static class Staff {
        String prefix;
        String name;
        boolean isSpec;
        Status status;

        public Staff(String prefix, String name, boolean isSpec, Status status) {
            this.prefix = prefix;
            this.name = name;
            this.isSpec = isSpec;
            this.status = status;
        }
    }

    private enum Status {
        NONE("", -1),
        VANISHED("V", new Color(255, 0, 0).getRGB());

        public final String string;
        public final int color;

        Status(String string, int color) {
            this.string = string;
            this.color = color;
        }
    }
}