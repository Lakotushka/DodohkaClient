package com.example.examplemod;

import com.example.examplemod.Module.CLIENT.ClickGUI;
import com.example.examplemod.Module.CLIENT.Panic;
import com.example.examplemod.Module.COMBAT.*;
import com.example.examplemod.Module.EXPLOIT.*;
import com.example.examplemod.Module.MISC.*;
import com.example.examplemod.Module.MOVEMENT.*;
import com.example.examplemod.Module.Module;
import com.example.examplemod.Module.RENDER.*;
import font.FontUtils;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;
import yea.bushroot.clickgui.ClickGuiManager;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Client {
    public static String name = "Dodohka Client 1.12.2 | User: " + Minecraft.getMinecraft().getSession().getUsername();
    public static String cName = "§f§aDodohka§aClient ";
    public static CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<Module>();

    public static ClickGuiManager clickGuiManager;

    public static void startup() {
        Display.setTitle(name);

        modules.add(new ClickGUI());
        modules.add(new FakePlayer());
        modules.add(new FastRPD());
        modules.add(new ItemsESP());
        modules.add(new NameTags());
        modules.add(new TriggerBot());
        modules.add(new AttackTrace());
        modules.add(new FullBright());
        modules.add(new ViewModel());
        modules.add(new KillAura());
        modules.add(new TargetHUD());
        modules.add(new ChestESP());
        modules.add(new GlowESP());
        modules.add(new Tracers());
        modules.add(new BoxESP());
        modules.add(new Sprint());
        modules.add(new HitBox());
        modules.add(new AimBot());
        modules.add(new Particles());
        modules.add(new PlayerEntity());
        modules.add(new Panic());
        modules.add(new BugHelper());
        modules.add(new AutoBag());
        modules.add(new AutoLeave());
        modules.add(new HpLeave());
        modules.add(new StaffList());
        modules.add(new AutoLoot());
        modules.add(new AutoFarm());
        modules.add(new AutoRepair());
        modules.add(new AutoRTP());
        modules.add(new AutoWalk());
        modules.add(new MobAura());
        modules.add(new ScoreZombie());
        modules.add(new TestFindFarm());
        modules.add(new TestFindFarm2());
        modules.add(new ArmorHUD());
        modules.add(new AutoRepairFarm());
        modules.add(new AutoHeal());
        modules.add(new ChestStealer());
        modules.add(new AutoEXT());
        modules.add(new AutoSwap());
        modules.add(new AutoBan());
        modules.add(new HealKD());
        modules.add(new AutoAPT());
        modules.add(new BindAPT());
        modules.add(new MiddleApt());
        modules.add(new OblaSeiver());


        clickGuiManager = new ClickGuiManager();

        FontUtils.bootstrap();
    }

    public static ArrayList<Module> getModulesInCategory(Module.Category c) {
        ArrayList<Module> mods = new ArrayList<Module>();
        for (Module m : modules) {
            if (m.getCategory().name().equalsIgnoreCase(c.name())) {
                mods.add(m);
            }
        }
        return mods;
    }

    public static void keyPress(int key) {
        for (Module m : modules) {
            if (m.getKey() == key) {
                m.toggle();
            }
        }
    }
}
