package uk.co.anttheantster.utilities.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentTranslation;

public class Debug {

    protected static Minecraft mc = Minecraft.getMinecraft();

    public void debugTitle(){
        mc.ingameGUI.displayTitle("Debug", null, 10, 60, 10);
    }

    public void debugMessage(){
        mc.thePlayer.addChatMessage(new ChatComponentTranslation("Debug!"));
    }

}
