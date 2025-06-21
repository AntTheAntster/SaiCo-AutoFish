package uk.co.anttheantster.utilities.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentTranslation;
import uk.co.anttheantster.utilities.modules.AutoEat;
import uk.co.anttheantster.utilities.modules.GapAlert;

public class AutoEatCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "autoeat";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/autoeat - Toggles AutoEat on/off";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {

        AutoEat.eatEnabled = !AutoEat.eatEnabled;
        String prefix = "§eAuto§bEat ";
        String status = AutoEat.eatEnabled ? "§aEnabled" : "§cDisabled";
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
