package uk.co.anttheantster.Autofish.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import uk.co.anttheantster.Autofish.Keybind.KeyBinds;

import java.util.ArrayList;
import java.util.List;

public class AutoEat {

    protected static final Minecraft mc = Minecraft.getMinecraft();

    public boolean autoEatEnabled;

    private boolean commandQueued = false;


    @SubscribeEvent
    @SideOnly(value = Side.CLIENT)
    public void onKeyInput(InputEvent.KeyInputEvent e) {
        if (KeyBinds.AutoEatKey.isPressed()) {
            this.autoEatEnabled = !this.autoEatEnabled;

            String status = this.autoEatEnabled ? "§aEnabled!" : "§cDisabled!";
            String prefix = "§fAuto§bEat ";
            String message = prefix + status;

            mc.thePlayer.addChatMessage(new ChatComponentTranslation(message));
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        if (mc.thePlayer == null || mc.theWorld == null) {
            return;
        }

        String prefix = "§bAuto§eEat ";
        int hunger = mc.thePlayer.getFoodStats().getFoodLevel();

        if (this.autoEatEnabled) {
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
