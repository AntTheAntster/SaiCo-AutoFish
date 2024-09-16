package UselessDuck.Autofish;

import UselessDuck.Autofish.command.AutofishCommand;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.SidedProxy;
import UselessDuck.Autofish.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.common.MinecraftForge;
import UselessDuck.Autofish.Keybind.KeyBinds;

@Mod(modid = "SaiCoPvP_Autofish", name = "SaiCoPvP Autofish", version = "1.0.0", acceptedMinecraftVersions = "[1.8.9]")
public class AutofishMod {
    public static final String ModID = "SaiCoPvP Autofish";
    public static final String ModName = "SaiCoPvP Autofish";
    public static final String ModVersion = "1.0.0";
    @Mod.Instance("Autofish")
    @SidedProxy(clientSide = "UselessDuck.Autofish.proxy.ClientProxy", serverSide = "UselessDuck.Autofish.CommonProxy")
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