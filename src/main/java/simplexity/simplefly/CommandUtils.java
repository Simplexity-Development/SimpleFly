package simplexity.simplefly;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
