package UselessDuck.Autofish.command;

import UselessDuck.Autofish.Autofish;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class MuteFishCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "mutefish";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/mutefish";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        Autofish.instance.toggleMuteFish();
        String status = Autofish.instance.isMuteFishEnabled() ? "\u00a7aEnabled" : "\u00a7cDisabled";
        sender.addChatMessage((IChatComponent)new ChatComponentText("\u00a7a\u00a7lMuteFish " + status));
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