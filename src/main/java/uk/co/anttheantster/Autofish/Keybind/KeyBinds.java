package uk.co.anttheantster.Autofish.Keybind;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class KeyBinds {
    public static KeyBinding AutofishKey;
    public static KeyBinding AutoSellKey;
    public static KeyBinding AutoEatKey;

    public KeyBinds() {
    }

    public static void register() {
        ClientRegistry.registerKeyBinding(KeyBinds.AutofishKey = new KeyBinding(new ChatComponentTranslation("Ant's AutoFish",
                new Object[0]).getFormattedText(), Keyboard.KEY_F10, "Ant's Utilities"));

        ClientRegistry.registerKeyBinding(KeyBinds.AutoSellKey = new KeyBinding(new ChatComponentTranslation("Ant's AutoSell",
                new Object[0]).getFormattedText(), Keyboard.KEY_F9, "Ant's Utilities"));

        ClientRegistry.registerKeyBinding(KeyBinds.AutoEatKey = new KeyBinding(new ChatComponentTranslation("Ant's AutoEat",
                new Object[0]).getFormattedText(), Keyboard.KEY_F8, "Ant's Utilities"));
    }
}