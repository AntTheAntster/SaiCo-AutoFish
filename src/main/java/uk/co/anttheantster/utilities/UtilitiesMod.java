package uk.co.anttheantster.utilities;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.SidedProxy;
import uk.co.anttheantster.utilities.modules.Autofish;
import uk.co.anttheantster.utilities.modules.GapAlert;
import uk.co.anttheantster.utilities.modules.GrinderSwitcher;
import uk.co.anttheantster.utilities.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.common.MinecraftForge;
import uk.co.anttheantster.utilities.Keybind.KeyBinds;
import uk.co.anttheantster.utilities.modules.AutoEat;

@Mod(modid = UtilitiesMod.ModID)
public class UtilitiesMod {
    public static final String ModID = "antssaicoutilities";
    @Mod.Instance("utilities")
    @SidedProxy(clientSide = "uk.co.anttheantster.utilities.proxy.ClientProxy", serverSide = "uk.co.anttheantster.utilities.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void onPreInit(final FMLPreInitializationEvent event) {
        UtilitiesMod.proxy.registerEvents(event);
        MinecraftForge.EVENT_BUS.register(new GrinderSwitcher());
        MinecraftForge.EVENT_BUS.register(new Autofish());
        MinecraftForge.EVENT_BUS.register(new InventoryScanner());
        MinecraftForge.EVENT_BUS.register(new SoundManager());
        MinecraftForge.EVENT_BUS.register(new AutoEat());
        MinecraftForge.EVENT_BUS.register(new GapAlert());
    }

    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        UtilitiesMod.proxy.initMod();
        KeyBinds.register();
        populateLists();
    }

    private void populateLists() {
        Autofish.blacklistedItems.add("minecraft:fish");
        Autofish.blacklistedItems.add("minecraft:prismarine_shard");
        Autofish.blacklistedItems.add("minecraft:cooked_fish");
        Autofish.blacklistedItems.add("minecraft:dye");
    }
}