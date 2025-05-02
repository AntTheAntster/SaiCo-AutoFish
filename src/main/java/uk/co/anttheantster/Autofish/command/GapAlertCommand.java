package uk.co.anttheantster.Autofish.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentTranslation;
import uk.co.anttheantster.Autofish.utils.Config;

public class GapAlertCommand extends CommandBase {


    @Override
    public String getCommandName() {
        return "gapalert";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/gapalert - Toggles Gapple alerts on/off";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {

        Config.instance.toggleGapAlert();
        String prefix = "§eGapple§bAlert ";
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
