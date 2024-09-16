/*
 * Decompiled with CFR 0.152.
 *
 * Could not load the following classes:
 *  net.minecraft.command.ICommand
 *  net.minecraftforge.client.ClientCommandHandler
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.event.FMLPreInitializationEvent
 */
package UselessDuck.Autofish.proxy;

import UselessDuck.Autofish.Autofish;
import UselessDuck.Autofish.command.AutofishCommand;
import UselessDuck.Autofish.proxy.CommonProxy;
import net.minecraft.command.ICommand;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy
        extends CommonProxy {
    @Override
    public void registerEvents(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register((Object)Autofish.instance);
    }

    @Override
    public void initMod() {
        ClientCommandHandler.instance.registerCommand((ICommand)new AutofishCommand());
    }
}

