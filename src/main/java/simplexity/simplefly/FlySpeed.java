package simplexity.simplefly;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FlySpeed implements TabExecutor {
    
    private static MiniMessage miniMessage = SimpleFly.getMiniMessage();
    private static final ArrayList<String> tabComplete = new ArrayList<>();
    private static final String setArg = "set";
    private static final String resetArg = "reset";
    
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        switch (args.length) {
            case 0 -> {
                if (CommandUtils.checkIfPlayerAndPerms(sender, Util.flySpeedPermission)) {
                    float flyspeed = ((Player) sender).getFlySpeed() * 10f;
                    sender.sendMessage(miniMessage.deserialize(
                            ConfigValues.flySpeedOwn,
                            Placeholder.parsed("value", String.valueOf(flyspeed))
                    ));
                    return true;
                } else {
                    return false;
                }
            }
            case 1 -> {
                String argument = args[0];
                if (argument.equalsIgnoreCase(resetArg)) {
                    if (CommandUtils.checkIfPlayerAndPerms(sender, Util.flySpeedPermission)) {
                        ((Player) sender).setFlySpeed(0.1f);
                        sender.sendRichMessage(ConfigValues.flySpeedReset);
                        return true;
                    }
                }
                if (argument.equalsIgnoreCase(setArg)) {
                    sender.sendRichMessage(ConfigValues.notEnoughArguments);
                    return false;
                }
            }
            case 2 -> {
                String firstArgument = args[0];
                String secondArgument = args[1];
                Float flyspeed;
                Player player;
                if (firstArgument.equalsIgnoreCase(setArg)) {
                    try {
                        flyspeed = Float.parseFloat(secondArgument);
                        player = (Player) sender;
                        player.setFlySpeed(flyspeed / 10f);
                        sender.sendRichMessage(ConfigValues.flySpeedSet);
                        return true;
                    } catch (NumberFormatException e) {
                        player = SimpleFly.getFlyServer().getPlayer(secondArgument);
                        if (player == null || !sender.hasPermission(Util.flySpeedOthersPermission)) {
                            sender.sendRichMessage(ConfigValues.notANumber);
                            return false;
                        }
                        sender.sendRichMessage(ConfigValues.notEnoughArguments);
                        return false;
                    }
                }
                if (firstArgument.equalsIgnoreCase(resetArg) && sender.hasPermission(Util.flySpeedOthersPermission)) {
                    player = SimpleFly.getFlyServer().getPlayer(secondArgument);
                    if (player == null) {
                        sender.sendRichMessage(ConfigValues.notAPlayer);
                        return false;
                    }
                    player.setFlySpeed(0.1f);
                    sender.sendMessage(miniMessage.deserialize(ConfigValues.flySpeedResetOther,
                            Placeholder.component("player", player.displayName())));
                    player.sendMessage(miniMessage.deserialize(ConfigValues.flySpeedResetByOther,
                            Placeholder.component("initiator", sender.name())));
                    return true;
                }
                return false;
            }
            case 3 -> {
                String firstArgument = args[0];
                String secondArgument = args[1];
                String thirdArgument = args[2];
                if (firstArgument.equalsIgnoreCase(setArg) && sender.hasPermission(Util.flySpeedOthersPermission)) {
                    Player player = SimpleFly.getFlyServer().getPlayer(secondArgument);
                    if (player == null) {
                        sender.sendRichMessage(ConfigValues.notAPlayer);
                        return false;
                    }
                    Float flyspeed;
                    try {
                        flyspeed = Float.parseFloat(thirdArgument);
                        player.setFlySpeed(flyspeed / 10f);
                        sender.sendRichMessage(ConfigValues.flySpeedSetOther);
                        player.sendMessage(miniMessage.deserialize(ConfigValues.flySpeedSetByOther,
                                Placeholder.component("initiator", sender.name())));
                        return true;
                    } catch (NumberFormatException e) {
                        sender.sendRichMessage(ConfigValues.notANumber);
                        return false;
                    }
                }
                
            }
        }
        return false;
    }
    
    
    
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (commandSender.hasPermission(Util.flySpeedPermission)) {
            tabComplete.clear();
            tabComplete.add(setArg);
            tabComplete.add(resetArg);
            return tabComplete;
        }
        return null;
    }
}
