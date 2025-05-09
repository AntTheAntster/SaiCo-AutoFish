package uk.co.anttheantster.Autofish;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import uk.co.anttheantster.Autofish.Keybind.KeyBinds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.common.config.Configuration;
import java.io.File;
import java.util.*;

public class Autofish {
    public static Autofish instance = new Autofish();
    protected static final Minecraft mc = Minecraft.getMinecraft();
    private boolean AutoFish;
    private boolean fished = false;
    private SoundManager soundManager = new SoundManager();
    private static final int CAST_ROD_DELAY = 300;
    private int castRodTimer = 0;
    public boolean soundManagerEnabled = false;
    private long lastCastTime = 0;
    private static final long CAST_COOLDOWN = 250;
    private Map<String, Integer> itemCounts = new HashMap<>();
    private Set<String> currentInventoryItems = new HashSet<>();
    private Map<String, Long> itemRemovalTime = new HashMap<>();
    private Configuration config;
    private InventoryScanner inventoryScanner;
    private boolean muteFish = false;
    private boolean isMuted = false;
    private boolean temporaryUnmute = true;
    private long lastOrbSoundTime = 0;
    private static final long ORB_SOUND_COOLDOWN = 1000; // 1 second cooldown
    private long lastUnmuteTime = 0;
    private static final long UNMUTE_DURATION = 1000; // 1 second in milliseconds
    public static ArrayList<String> blacklistedItems = new ArrayList<>();
    public static ArrayList<String> whitelistedItems = new ArrayList<>();
    public boolean sellEnabled;
    public String sellPrefix = "§f§lAuto§b§lSell";
    public boolean sellQueued;


    public Autofish() {
        loadConfig();
        this.inventoryScanner = new InventoryScanner();
    }

    private void loadConfig() {
        config = new Configuration(new File(Minecraft.getMinecraft().mcDataDir, "config/autofish.cfg"));
        config.load();
        soundManagerEnabled = config.getBoolean("soundManagerEnabled", Configuration.CATEGORY_GENERAL, false, "Enable or disable the sound manager");
        muteFish = config.getBoolean("MuteFish", Configuration.CATEGORY_GENERAL, false, "Enable or disable Mute Fish");
        config.save();
    }

    private void saveConfig() {
        config.getCategory(Configuration.CATEGORY_GENERAL).get("soundManagerEnabled").set(soundManagerEnabled);
        config.getCategory(Configuration.CATEGORY_GENERAL).get("MuteFish").set(muteFish);
        config.save();
    }

    @SubscribeEvent
    @SideOnly(value = Side.CLIENT)
    public void onKeyInput(InputEvent.KeyInputEvent e) {
        if (KeyBinds.AutofishKey.isPressed()) {
            this.AutoFish = !this.AutoFish;
            if (!this.AutoFish) {
                // AutoFish is being turned off
                this.inventoryScanner.triggerFinalCheck();
            } else {
                // AutoFish is being turned on
                this.soundManager.onAutoFishEnabled();
            }

            String status = this.AutoFish ? "§aEnabled" : "§cDisabled";
            String autofishBold = "§f§lAuto§b§lFish";
            String messageBold = "§c§lAnt's Saico Fish " + autofishBold + " " + status;
            String messageBoldSound = "§c§lPlease Enable Sound§d§l For it to work ";


            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent) new ChatComponentTranslation(messageBold, new Object[0]));
            float masterVolume = mc.gameSettings.getSoundLevel(SoundCategory.MASTER);
            if (masterVolume == 0.0F) {
                Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent) new ChatComponentTranslation(messageBoldSound, new Object[0]));;
            }
        }

        if (KeyBinds.AutoSellKey.isPressed()) {
            this.sellEnabled = !this.sellEnabled;

            String status = this.sellEnabled ? "§aEnabled" : "§cDisabled";
            String autosellBold = "§f§lAuto§b§lSell";
            String messageBold = "§c§lAnt's SaiCo Fish " + autosellBold + " " + status;

            mc.thePlayer.addChatMessage(new ChatComponentTranslation(messageBold, new Object[0]));
        }
    }


    @SubscribeEvent
    public void onPlaySoundEvent(final PlaySoundEvent event) {
        boolean isMuteActive = config.getBoolean("MuteFish", Configuration.CATEGORY_GENERAL, false, "Enable or disable Mute Fish");
        String soundName = event.sound.getSoundLocation().getResourcePath();
        long currentTime = System.currentTimeMillis();

        if (mc.theWorld != null && mc.thePlayer != null && mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemFishingRod && isMuteActive) {
            if (soundName.equals("random.splash") ||
                    soundName.equals("game.neutral.swim.splash") ||
                    soundName.equals("game.neutral.swim") ||
                    soundName.equals("random.bow") ||
                    soundName.equals("random.orb")) {
                loadConfig();

                if (currentTime - lastUnmuteTime < UNMUTE_DURATION) {
                } else {
                    event.result = null;
                }
            }
            loadConfig();
        }


        if (AutoFish && mc.theWorld != null && mc.thePlayer != null && mc.thePlayer.getHeldItem() != null
                && mc.thePlayer.getHeldItem().getItem() instanceof ItemFishingRod
                && event.sound.getSoundLocation().getResourcePath().equals("random.splash")
                && currentTime - lastCastTime > CAST_COOLDOWN) {

            mc.playerController.sendUseItem((EntityPlayer)mc.thePlayer, (World)mc.theWorld, mc.thePlayer.inventory.getCurrentItem());
            lastCastTime = currentTime;

            // Schedule the second cast after a short delay
            new Thread(() -> {
                try {
                    Thread.sleep(137);
                    mc.addScheduledTask(() -> {
                        mc.playerController.sendUseItem((EntityPlayer)mc.thePlayer, (World)mc.theWorld, mc.thePlayer.inventory.getCurrentItem());
                    });

                    if (sellQueued){
                        return;
                    }
                    int sleepRandom = new Random().nextInt(4);
                    Thread.sleep(sleepRandom * 1000);
                    sellQueued = true;

                    if (sellEnabled) {
                        inventoryHandler();
                        sellQueued = false;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Minecraft mc = Minecraft.getMinecraft();
            EntityPlayerSP player = mc.thePlayer;
            if (player != null && instance.isAutoFishEnabled()) {
                ItemStack heldItem = player.getHeldItem();
                if (heldItem != null && heldItem.getItem() instanceof ItemFishingRod) {
                    if (this.isFishingRodCast((EntityPlayer) player)) {
                        ++this.castRodTimer;
                        if (this.castRodTimer >= CAST_ROD_DELAY) {
                            this.castRodTimer = 0;
                        }
                    } else {
                        ++this.castRodTimer;
                        if (this.castRodTimer >= CAST_ROD_DELAY) {
                            this.soundManager.playCastRodSound();
                            this.castRodTimer = 0;
                        }
                    }
                } else {
                    this.castRodTimer = 0;
                }
            } else {
                this.castRodTimer = 0;
            }
        }
    }

    private boolean isFishingRodCast(EntityPlayer player) {
        for (EntityFishHook fishHook : player.worldObj.getEntitiesWithinAABB(EntityFishHook.class, player.getEntityBoundingBox().expand(64.0, 64.0, 64.0))) {
            if (fishHook.angler != player) continue;
            return true;
        }
        return false;
    }

    private String determineSound(ItemStack itemStack) {
        String name = itemStack.getDisplayName().toLowerCase();
        if ((name.contains("deluxe") && name.contains("divine")) ||
                (name.contains("legendary") && name.contains("divine") && name.contains("silver"))) {
            return "Bigfish";
        } else if (name.contains("treasure")) {
            return "Treasure";
        }
        return null;
    }

    public boolean isAutoFishEnabled() {
        return this.AutoFish;
    }

    public boolean isSoundManagerEnabled() {
        return this.soundManagerEnabled;
    }

    public void toggleSoundManager() {
        this.soundManagerEnabled = !this.soundManagerEnabled;
        saveConfig();
    }

    public boolean isMuteFishEnabled() {
        return muteFish;
    }

    public void toggleMuteFish() {
        this.muteFish = !this.muteFish;
        config.getCategory(Configuration.CATEGORY_GENERAL).get("MuteFish").set(muteFish);
        saveConfig();
    }


    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent event) {
        String chatMessage = event.message.getUnformattedText();

        if (chatMessage.contains("-> me]")) {
            lastUnmuteTime = System.currentTimeMillis();
        }
    }


    private void playRandomOrbSound() {
        Minecraft mc = Minecraft.getMinecraft();
        mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("random.orb"), 1.0F));
        lastOrbSoundTime = System.currentTimeMillis();
    }

    private void inventoryHandler() {
        for (ItemStack stack : mc.thePlayer.inventory.mainInventory) {
            if (stack != null) {
                String itemName = stack.getItem().getRegistryName();

                for (String item : blacklistedItems){
                    if (itemName.equals(item)){
                        mc.thePlayer.sendChatMessage("/sell all");
                    }

                }
            }

        }

        /*
        for (ItemStack stack : mc.thePlayer.inventory.mainInventory) {
            if (stack != null) {
                String itemName = stack.getItem().getRegistryName();

                for (String item : whitelistedItems){
                    mc.thePlayer.addChatMessage(new ChatComponentTranslation(item + " is Found! Storing!"));
                    
                }
            }
        }

         */
    }
}