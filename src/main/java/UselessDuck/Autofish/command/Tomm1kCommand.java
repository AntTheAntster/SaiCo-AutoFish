package UselessDuck.Autofish.command;

import UselessDuck.Autofish.Autofish;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class Tomm1kCommand extends CommandBase {
    public String getCommandName() {
        return "tomm1k";
    }

    public String getCommandUsage(ICommandSender sender) {
        return "/tomm1k";
    }

    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        Autofish.instance.toggleSoundManager();
        String status = Autofish.instance.isSoundManagerEnabled() ? "\u00a7aEnabled" : "\u00a7cDisabled";
        sender.addChatMessage((IChatComponent)new ChatComponentText("\u00a7a\u00a7lSaiCo\u00a7d\u00a7lPvP Sound Manager " + status));
    }

    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    public int getRequiredPermissionLevel() {
        return 0;
    }
}