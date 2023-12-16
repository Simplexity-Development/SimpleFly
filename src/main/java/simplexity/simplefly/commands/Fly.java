package simplexity.simplefly.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import simplexity.simplefly.ConfigValues;
import simplexity.simplefly.FlyLogic;
import simplexity.simplefly.SimpleFly;
import simplexity.simplefly.Util;

import java.util.List;

public class Fly implements TabExecutor {
    
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 0) {
            if (CommandUtils.checkIfPlayerAndPerms(sender, Util.flyPermission)) {
                if (FlyLogic.flyEnabled((Player) sender)) {
                    Util.sendUserMessage(sender, ConfigValues.flySetOwn, ConfigValues.enabled, null);
                    return true;
                } else {
                    Util.sendUserMessage(sender, ConfigValues.flySetOwn, ConfigValues.disabled, null);
                    return true;
                }
            } else {
                return false;
            }
        }
        if (!sender.hasPermission(Util.flyOthersPermission)) return false;
        Player player = SimpleFly.getFlyServer().getPlayer(args[0]);
        if (player == null) {
            Util.sendUserMessage(sender, ConfigValues.invalidPlayer);
            return false;
        }
        if (FlyLogic.flyEnabled(player)) {
            Util.sendUserMessage(sender, ConfigValues.flySetOther, ConfigValues.enabled, player);
            Util.sendUserMessage(player, ConfigValues.flySetByOther, ConfigValues.enabled, sender);
            return true;
        } else {
            Util.sendUserMessage(sender, ConfigValues.flySetOther, ConfigValues.disabled, player);
            Util.sendUserMessage(player, ConfigValues.flySetByOther, ConfigValues.disabled, sender);
            return true;
        }
    }
    
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
