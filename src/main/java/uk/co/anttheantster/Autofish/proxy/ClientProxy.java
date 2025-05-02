/*
 * Decompiled with CFR 0.152.
 *
 * Could not load the following classes:
 *  net.minecraft.command.ICommand
 *  net.minecraftforge.client.ClientCommandHandler
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.event.FMLPreInitializationEvent
 */
package uk.co.anttheantster.Autofish.proxy;

import uk.co.anttheantster.Autofish.command.AutoEatCommand;
import uk.co.anttheantster.Autofish.command.GapAlertCommand;
import uk.co.anttheantster.Autofish.modules.Autofish;
import uk.co.anttheantster.Autofish.command.Tomm1kCommand;
import uk.co.anttheantster.Autofish.command.MuteFishCommand;
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
        ClientCommandHandler.instance.registerCommand((ICommand)new Tomm1kCommand());
        ClientCommandHandler.instance.registerCommand((ICommand)new MuteFishCommand());
        ClientCommandHandler.instance.registerCommand((ICommand)new GapAlertCommand());
        ClientCommandHandler.instance.registerCommand((ICommand)new AutoEatCommand());

    }
}

