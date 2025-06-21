package uk.co.anttheantster.utilities.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class GrinderSwitcher {

    protected final Minecraft mc = Minecraft.getMinecraft();

    public static boolean grinderModeEnabled = false;


    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent event) {
        if (!grinderModeEnabled){ return; }
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        if (mc.thePlayer == null || mc.theWorld == null) {
            return;
        }
        ItemStack heldItem = mc.thePlayer.inventory.getCurrentItem();
        if (heldItem == null) return;

        if (heldItem.getItem() instanceof ItemSword){
            int maxDurability = heldItem.getMaxDamage();
            int currentDurability = maxDurability - heldItem.getItemDamage();
            if (currentDurability <= 20){
                for (int i = 0; i < 9; i++) {
                    try {

                    } catch (Exception e) {
                        mc.thePlayer.addChatMessage(new ChatComponentTranslation("Cannot Switch to new sword! Disabling..."));
                    }
                    ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);
                    if (stack == null) continue;
                    if (stack.getItem() instanceof ItemSword){
                        if (stack.getItemDamage() > 20){
                            mc.thePlayer.inventory.currentItem = i;
                            break;
                        }
                    }
                }
            }
        }
    }

}
