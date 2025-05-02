package uk.co.anttheantster.Autofish.Keybind;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class KeyBinds {
    public static KeyBinding AutofishKey;
    public static KeyBinding FishingModeKey;

    public KeyBinds() {
    }

    public static void register() {
        ClientRegistry.registerKeyBinding(KeyBinds.AutofishKey = new KeyBinding(new ChatComponentTranslation("Ant's AutoFish",
                new Object[0]).getFormattedText(), Keyboard.KEY_F10, "Ant's Utilities"));

        ClientRegistry.registerKeyBinding(KeyBinds.FishingModeKey = new KeyBinding(new ChatComponentTranslation("Ant's FishingMode",
                new Object[0]).getFormattedText(), Keyboard.KEY_F9, "Ant's Utilities"));
    }
}