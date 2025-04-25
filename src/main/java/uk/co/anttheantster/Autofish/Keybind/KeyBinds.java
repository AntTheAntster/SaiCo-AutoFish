package uk.co.anttheantster.Autofish.Keybind;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.client.settings.KeyBinding;

public class KeyBinds {
    public static KeyBinding AutofishKey;

    public KeyBinds() {
    }

    public static void register() {
        ClientRegistry.registerKeyBinding(KeyBinds.AutofishKey = new KeyBinding(new ChatComponentTranslation("Ant's AutoFish",
                new Object[0]).getFormattedText(), 68, "Ant's AutoFish"));
    }
}