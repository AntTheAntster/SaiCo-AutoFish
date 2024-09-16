package UselessDuck.Autofish;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class InventoryScanner {
    private static final int SCAN_INTERVAL = 10;
    private static final int DISPLAY_DELAY = 30;
    private int tickCounter = 0;
    private int disableCounter = -1;
    private Map<String, Double> itemValues = new HashMap<>();
    private NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
    private boolean wasEnabled = false;
    private SoundManager soundManager;
    private long lastInventoryFullSoundTime = 0L;
    private static final long INVENTORY_FULL_SOUND_COOLDOWN = 10000L;
    private boolean performingFinalCheck = false;

    public InventoryScanner() {
        this.currencyFormatter.setMaximumFractionDigits(0);
        this.soundManager = new SoundManager();
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            boolean isCurrentlyEnabled = Autofish.instance.isAutoFishEnabled();
            if (isCurrentlyEnabled) {
                this.wasEnabled = true;
                this.disableCounter = -1;
                ++this.tickCounter;
                if (this.tickCounter >= SCAN_INTERVAL) {
                    this.scanInventory(false);
                    this.tickCounter = 0;
                }
            } else if (this.wasEnabled) {
                if (this.disableCounter == -1) {
                    this.disableCounter = 0;
                } else if (this.disableCounter < DISPLAY_DELAY) {
                    ++this.disableCounter;
                } else {
                    this.scanInventory(true);
                    this.wasEnabled = false;
                    this.disableCounter = -1;
                }
            }
        }
    }

    public void triggerFinalCheck() {
        this.wasEnabled = true;
        this.disableCounter = 0;
    }



    private void scanInventory(boolean displayResults) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (player == null) return;

        double totalValue = 0.0;
        StringBuilder breakdown = new StringBuilder("Inventory Breakdown:\n");
        Map<String, Integer> itemCounts = new HashMap<>();

        soundManager.startInventoryScan();

        for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
            ItemStack itemStack = player.inventory.getStackInSlot(i);
            if (itemStack != null) {
                String itemName = itemStack.getDisplayName();
                Double value = getItemValue(itemName);

                if (value > 0.0) {
                    double itemTotalValue = value * itemStack.stackSize;
                    totalValue += itemTotalValue;
                    itemCounts.put(itemName, itemCounts.getOrDefault(itemName, 0) + itemStack.stackSize);
                }

                soundManager.checkAndPlaySound(itemStack);
            }
        }

        soundManager.finishInventoryScan();

        for (Map.Entry<String, Integer> entry : itemCounts.entrySet()) {
            String itemName = entry.getKey();
            int count = entry.getValue();
            double value = getItemValue(itemName) * count;
            breakdown.append(String.format("%dx %s: %s\n", count, itemName, currencyFormatter.format(value)));
        }

        if (displayResults) {
            IChatComponent totalComponent = new ChatComponentText("\u00A7a\u00A7lSaiCo\u00A7d\u00A7lPvP You Fished Up \u00A7a" + currencyFormatter.format(totalValue) + " \u00A7a\u00A7lGo \u00A7a\u00A7lSell\u00A7a! \u00A7a\u00A7lSell\u00A7a! \u00A7a\u00A7lSell\u00A7a! ");

            // Add click event to execute "/sell all" command
            totalComponent.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sell all"));

            // Add hover event to show breakdown
            totalComponent.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(breakdown.toString())));

            Minecraft.getMinecraft().thePlayer.addChatMessage(totalComponent);
        }

        if (isInventoryFull(player)) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastInventoryFullSoundTime >= INVENTORY_FULL_SOUND_COOLDOWN) {
                soundManager.onInventoryFull();
                lastInventoryFullSoundTime = currentTime;
            }
        }
    }


    private Double getItemValue(String itemName) {
        //Strip Formatting From the Item Names
        String strippedName = this.stripFormatting(itemName).toLowerCase();

        //Legendary Silvefish Aka Button
        if (strippedName.contains("legendary") && strippedName.contains("divine") && strippedName.contains("silver")) {
            return 1000000.0;
        }



        //Deluxe Divine items
        if (strippedName.contains("deluxe") && strippedName.contains("divine")) {
            if (strippedName.contains("salmon")) {
                return 250000.0;
            }
            if (strippedName.contains("fish")) {
                return 250000.0;
            }
        }


        // Divine Items
        if (strippedName.contains("divine")) {
            if (strippedName.contains("pufferfish")) {
                return 150000.0;
            }
            if (strippedName.contains("salmon")) {
                return 80000.0;
            }
            if (strippedName.contains("clownfish")) {
                return 150000.0;
            }
            if (strippedName.contains("fish")) {
                return 80000.0;
            }
        }

        //Deluxe Arcane Items
        if (strippedName.contains("deluxe") && strippedName.contains("arcane")) {
            return 50000.0;
        }

        //Arcane Items
        if (strippedName.contains("arcane")) {
            if (strippedName.contains("pufferfish")) {
                return 20000.0;
            }
            if (strippedName.contains("clownfish")) {
                return 20000.0;
            }
        }


        //Enchanted Items
        if (strippedName.contains("enchanted")) {
            if (strippedName.contains("fish")) {
                return 10000.0;
            }
            if (strippedName.contains("salmon")) {
                return 10000.0;
            }
        }

        //Deluxe items
        if (strippedName.contains("deluxe") && !strippedName.contains("arcane")) {
            if (strippedName.contains("salmon")) {
                return 6000.0;
            }
            if (strippedName.contains("fish")) {
                return 6000.0;
            }
        }


        //Normal items
        if (strippedName.contains("prismarine shard")) {
            return 2250.0;
        }
        if (strippedName.contains("squid")) {
            return 2250.0;
        }
        if (strippedName.contains("pufferfish")) {
            return 1200.0;
        }
        if (strippedName.contains("clownfish")) {
            return 1200.0;
        }
        if (strippedName.contains("salmon")) {
            return 750.0;
        }
        if (strippedName.contains("rod")) {
            return 0.0;
        }
        if (strippedName.contains("lootbag")) {
            return 0.0;
        }
        if (strippedName.contains("fish")) {
            return 600.0;
        }
        return 0.0;
    }

    private String stripFormatting(String input) {
        return input.replaceAll("\u00A7[0-9a-fk-or]", "");
    }

    private boolean isInventoryFull(EntityPlayer player) {
        for (int i = 0; i < player.inventory.mainInventory.length; ++i) {
            if (player.inventory.mainInventory[i] == null) {
                return false;
            }
        }
        return true;
    }
}