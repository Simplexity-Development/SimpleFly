package simplexity.simplefly;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Fly implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 0) {
            if (CommandUtils.checkIfPlayerAndPerms(sender, Util.flyPermission)) {
                FlyLogic.flyEnabled((Player) sender);
                return true;
            } else {
                return false;
            }
        }
        if (!sender.hasPermission(Util.flyOthersPermission)) return false;
        Player player = SimpleFly.getFlyServer().getPlayer(args[0]);
        if (player == null) {
            sender.sendRichMessage(ConfigValues.notAPlayer);
            return false;
        }
        FlyLogic.flyEnabled(player);
        return true;
    }
    
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
