package simplexity.simplefly.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import simplexity.simplefly.ConfigValues;
import simplexity.simplefly.Util;

public class FlyReload implements CommandExecutor {
    
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        ConfigValues.reloadConfigValues();
        Util.sendUserMessage(sender, ConfigValues.configReloaded);
        return false;
    }
}
