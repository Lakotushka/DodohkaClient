package com.example.examplemod.Module.RENDER;
import com.example.examplemod.Module.Module;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.lwjgl.input.Keyboard;
import java.util.Objects;
public class FullBright extends Module {
    public FullBright() {
        super("FullBright", Keyboard.KEY_NONE, Category.RENDER);
    }
    @Override
    public void onEnable() {
        mc.player.addPotionEffect(new PotionEffect(Objects.requireNonNull(Potion.getPotionById(16)), 999999, 1));
    }
    @Override
    public void onDisable() {
        mc.player.removePotionEffect(Objects.requireNonNull(Potion.getPotionById(16)));
    }
}
