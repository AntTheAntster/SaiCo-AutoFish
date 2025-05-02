package uk.co.anttheantster.Autofish.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import uk.co.anttheantster.Autofish.Keybind.KeyBinds;
import uk.co.anttheantster.Autofish.utils.Config;

public class AutoEat {

    protected static final Minecraft mc = Minecraft.getMinecraft();

    private boolean commandQueued = false;

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (!Config.instance.autoEatEnabled){ return; }

        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        if (mc.thePlayer == null || mc.theWorld == null) {
            return;
        }

        String prefix = "§bAuto§eEat ";
        int hunger = mc.thePlayer.getFoodStats().getFoodLevel();

        if (Config.instance.autoEatEnabled) {
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
