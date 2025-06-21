package uk.co.anttheantster.utilities.Keybind;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class KeyBinds {
    public static KeyBinding GrinderKey;
    public static KeyBinding FishingModeKey;
    public static KeyBinding OpenGUIKey;

    public KeyBinds() {
    }

    public static void register() {
        ClientRegistry.registerKeyBinding(KeyBinds.GrinderKey = new KeyBinding(new ChatComponentTranslation("Ant's Grinder",
                new Object[0]).getFormattedText(), Keyboard.KEY_F7, "Ant's Utilities"));

        ClientRegistry.registerKeyBinding(KeyBinds.FishingModeKey = new KeyBinding(new ChatComponentTranslation("Ant's FishingMode",
                new Object[0]).getFormattedText(), Keyboard.KEY_F9, "Ant's Utilities"));

        ClientRegistry.registerKeyBinding(KeyBinds.OpenGUIKey = new KeyBinding(new ChatComponentTranslation("Ant's Utilities GUI",
                new Object[0]).getFormattedText(), Keyboard.KEY_F8, "Ant's Utilities"));
    }
}