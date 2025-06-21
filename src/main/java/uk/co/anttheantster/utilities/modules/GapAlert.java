package uk.co.anttheantster.utilities.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class GapAlert {

    public static boolean gapAlertEnabled = true;

    protected static Minecraft mc = Minecraft.getMinecraft();
    public boolean hasGap;
    private static final String gapAlert = "§cGapple Removed!";

    @SubscribeEvent
    public void onClentTick(TickEvent.ClientTickEvent event) {
        if (mc.thePlayer == null || mc.theWorld == null) { return; }

        if (!gapAlertEnabled) {
            return;
        }

        if (hasGap){
            if (!hasRegenV()){
                mc.ingameGUI.displayTitle(gapAlert, null, 10, 60, 10);
                hasGap = false;
            }
        } else {
            hasGap = hasRegenV();
        }


    }

    private boolean hasRegenV(){
        PotionEffect pEffect = mc.thePlayer.getActivePotionEffect(Potion.regeneration);
        return pEffect != null && pEffect.getAmplifier() == 4;
    }

}
