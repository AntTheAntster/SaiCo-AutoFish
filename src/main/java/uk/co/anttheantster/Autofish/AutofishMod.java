package uk.co.anttheantster.Autofish;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.SidedProxy;
import uk.co.anttheantster.Autofish.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.common.MinecraftForge;
import uk.co.anttheantster.Autofish.Keybind.KeyBinds;
import uk.co.anttheantster.Autofish.utils.AutoEat;

@Mod(modid = AutofishMod.ModID)
public class AutofishMod {
    public static final String ModID = "antssaicoautofish";
    @Mod.Instance("Autofish")
    @SidedProxy(clientSide = "uk.co.anttheantster.Autofish.proxy.ClientProxy", serverSide = "uk.co.anttheantster.Autofish.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void onPreInit(final FMLPreInitializationEvent event) {
        AutofishMod.proxy.registerEvents(event);
        MinecraftForge.EVENT_BUS.register(new Autofish());
        MinecraftForge.EVENT_BUS.register(new InventoryScanner());
        MinecraftForge.EVENT_BUS.register(new SoundManager());
        MinecraftForge.EVENT_BUS.register(new AutoEat());
    }

    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        AutofishMod.proxy.initMod();
        KeyBinds.register();
        populateLists();
    }

    private void populateLists() {
        Autofish.blacklistedItems.add("minecraft:fish");
        Autofish.blacklistedItems.add("minecraft:prismarine_shard");
        Autofish.blacklistedItems.add("minecraft:cooked_fish");
        Autofish.blacklistedItems.add("minecraft:dye");

        /*
        Autofish.whitelistedItems.add("minecraft:paper");
        Autofish.whitelistedItems.add("minecraft:blaze_powder");
        Autofish.whitelistedItems.add("minecraft:chest");
        Autofish.whitelistedItems.add("minecraft:player_head");
        Autofish.whitelistedItems.add("minecraft:prismarine_crystals");
        Autofish.whitelistedItems.add("minecraft:quartz");
         */
    }
}