package simplexity.simplefly.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import simplexity.simplefly.ConfigValues;

public class CommandUtils {
    
    public static boolean checkIfPlayerAndPerms(CommandSender sender, String permission) {
        if (!(sender instanceof Player player)) {
            sender.sendRichMessage(ConfigValues.mustBePlayer);
            return false;
        }
        if (!player.hasPermission(permission)) {
            player.sendRichMessage(ConfigValues.noPermission);
            return false;
        }
        return true;
    }
    
}
