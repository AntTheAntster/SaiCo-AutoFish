package uk.co.anttheantster.utilities.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentTranslation;
import uk.co.anttheantster.utilities.modules.GapAlert;

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

        GapAlert.gapAlertEnabled = !GapAlert.gapAlertEnabled;
        String prefix = "§eGapple§bAlert ";
        String status = GapAlert.gapAlertEnabled ? "§aEnabled" : "§cDisabled";
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
