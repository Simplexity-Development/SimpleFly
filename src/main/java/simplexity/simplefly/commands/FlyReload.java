package simplexity.simplefly.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import simplexity.simplefly.config.ConfigValues;
import simplexity.simplefly.Util;
import simplexity.simplefly.config.LocaleMessage;

public class FlyReload implements CommandExecutor {
    
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        ConfigValues.reloadConfigValues();
        sender.sendRichMessage(LocaleMessage.FEEDBACK_CONFIG_RELOADED.getMessage());
        return false;
    }
}
