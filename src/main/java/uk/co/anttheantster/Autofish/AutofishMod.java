package uk.co.anttheantster.Autofish;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.SidedProxy;
import uk.co.anttheantster.Autofish.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.common.MinecraftForge;
import uk.co.anttheantster.Autofish.Keybind.KeyBinds;

@Mod(modid = AutofishMod.ModID)
public class AutofishMod {
    public static final String ModID = "anssaicoautofish";
    @Mod.Instance("Autofish")
    @SidedProxy(clientSide = "uk.co.anttheantster.Autofish.proxy.ClientProxy", serverSide = "uk.co.anttheantster.Autofish.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void onPreInit(final FMLPreInitializationEvent event) {
        AutofishMod.proxy.registerEvents(event);
        MinecraftForge.EVENT_BUS.register(new Autofish());
        MinecraftForge.EVENT_BUS.register(new InventoryScanner());
        MinecraftForge.EVENT_BUS.register(new SoundManager());
    }

    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        AutofishMod.proxy.initMod();
        KeyBinds.register();
    }
}