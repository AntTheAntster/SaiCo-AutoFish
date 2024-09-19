package UselessDuck.Autofish;

import UselessDuck.Autofish.Autofish;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class SoundManager {
    private static final String SOUND_DIRECTORY = "autofish";
    private Map<String, Integer> itemCounts = new HashMap<>();
    private Set<String> currentInventoryItems = new HashSet<>();
    private Map<String, Long> itemRemovalTime = new HashMap<>();
    private static final long SOUND_DELAY = 1000L;
    private static final long SPECIAL_SOUND_DELAY = 700L; // 0.7 second delay for treasure and deluxe divine
    private boolean isInitialScan = true;
    private Map<String, Long> lastSoundPlayTime = new HashMap<>();
    private Set<String> soundPlayedThisScan = new HashSet<>();

    public void startInventoryScan() {
        this.currentInventoryItems.clear();
        this.soundPlayedThisScan.clear();
    }

    public void checkAndPlaySound(ItemStack itemStack) {
        if (itemStack == null || isInitialScan) return;

        String itemKey = generateItemKey(itemStack);
        currentInventoryItems.add(itemKey);

        int currentCount = itemStack.stackSize;
        int previousCount = itemCounts.getOrDefault(itemKey, 0);

        long currentTime = System.currentTimeMillis();
        long removalTime = itemRemovalTime.getOrDefault(itemKey, 0L);
        long lastPlayTime = lastSoundPlayTime.getOrDefault(itemKey, 0L);

        if (currentCount > previousCount && currentTime - removalTime > SOUND_DELAY && !soundPlayedThisScan.contains(itemKey)) {
            String soundName = determineSound(itemStack);
            if (soundName != null) {
                boolean isSpecialSound = "Bigfish".equals(soundName) || "Treasure".equals(soundName);
                long timeSinceLastPlay = currentTime - lastPlayTime;

                if (!isSpecialSound || timeSinceLastPlay > SPECIAL_SOUND_DELAY) {
                    playSound(soundName);
                    lastSoundPlayTime.put(itemKey, currentTime);
                    soundPlayedThisScan.add(itemKey);
                }
            }
        }

        itemCounts.put(itemKey, Math.max(currentCount, previousCount));
        itemRemovalTime.remove(itemKey);
    }


    public void finishInventoryScan() {
        Set<String> removedItems = new HashSet<>(itemCounts.keySet());
        removedItems.removeAll(currentInventoryItems);

        long currentTime = System.currentTimeMillis();
        for (String removedItem : removedItems) {
            itemRemovalTime.put(removedItem, currentTime);
        }

        itemCounts.keySet().retainAll(currentInventoryItems);
        isInitialScan = false;  // Set to false after the first scan is complete
    }

    private String generateItemKey(ItemStack itemStack) {
        String itemName = itemStack.getDisplayName();
        int damage = itemStack.getItemDamage();
        return itemName + "_" + damage;
    }

    private String determineSound(ItemStack itemStack) {
        String name = itemStack.getDisplayName().toLowerCase();
        if ((name.contains("deluxe") && name.contains("divine")) ||
                (name.contains("legendary") && name.contains("divine") && name.contains("silver"))) {
            return "Bigfish";
        }
        if (name.contains("treasure")) {
            return "Treasure";
        }
        return null;
    }

    private void playSound(String soundName) {
        if (Autofish.instance.isSoundManagerEnabled()) {
            Minecraft.getMinecraft().getSoundHandler().playSound((ISound)PositionedSoundRecord.create(new ResourceLocation(SOUND_DIRECTORY, soundName), 1.0f));
        }
    }

    public void resetCounts() {
        this.itemCounts.clear();
        this.currentInventoryItems.clear();
        this.itemRemovalTime.clear();
        this.lastSoundPlayTime.clear();
        this.soundPlayedThisScan.clear();
        this.isInitialScan = true;  // Reset to true when counts are reset
    }

    public void playCastRodSound() {
        this.playSound("CastUrRod");
    }

    public void onAutoFishEnabled() {
        this.resetCounts();
        this.playSound("GrindGrindGrind");
    }

    public void onInventoryFull() {
        this.playSound("SellSellSell");
    }
}