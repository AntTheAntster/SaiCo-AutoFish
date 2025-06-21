package uk.co.anttheantster.utilities.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoEat {

    public static boolean eatEnabled = true;

    protected static final Minecraft mc = Minecraft.getMinecraft();
    private boolean commandQueued = false;

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (!eatEnabled){ return; }

        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        if (mc.thePlayer == null || mc.theWorld == null) {
            return;
        }

        String prefix = "§bAuto§eEat ";
        int hunger = mc.thePlayer.getFoodStats().getFoodLevel();

        if (eatEnabled) {
            if (hunger < 18){
                if (!commandQueued) {
                    mc.thePlayer.sendChatMessage("/eat");
                    commandQueued = true;
                    mc.thePlayer.addChatMessage(new ChatComponentTranslation(prefix + "§aEaten!"));
                }
            } else {
                commandQueued = false;
            }
        }
    }

}
