package simplexity.simplefly;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class FlyReload implements CommandExecutor {
    
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        ConfigValues.reloadConfigValues();
        Util.sendUserMessage(sender, ConfigValues.configReloaded);
        return false;
    }
}
