package uk.co.anttheantster.Autofish.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentTranslation;
import uk.co.anttheantster.Autofish.utils.Config;

public class AutoEatCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "autoeat";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/autoeat - Toggles Auto Eat on/off";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {

        Config.instance.toggleAutoEat();
        String prefix = "§eAuto§bEat ";
        String status = Config.instance.gapAlertEnabled ? "§aEnabled" : "§cDisabled";
        String message = prefix + status;

        sender.addChatMessage(new ChatComponentTranslation(message));
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }


}
