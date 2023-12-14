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
    
    private static final MiniMessage miniMessage = SimpleFly.getMiniMessage();
    private static final ArrayList<String> tabComplete = new ArrayList<>();
    private static final String setArg = "set";
    private static final String resetArg = "reset";
    private static final String getArg = "get";
    
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        switch (args.length) {
            case 0 -> {
                if (CommandUtils.checkIfPlayerAndPerms(sender, Util.flySpeedPermission)) {
                    float flyspeed = ((Player) sender).getFlySpeed() * 10f;
                    Util.sendUserMessage(sender, ConfigValues.flySpeedGetOwn,
                            String.valueOf(flyspeed), null);
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
                        Util.sendUserMessage(sender, ConfigValues.flySpeedResetOwn);
                        return true;
                    }
                }
                if (argument.equalsIgnoreCase(setArg)) {
                    Util.sendUserMessage(sender, ConfigValues.notEnoughArguments);
                    return false;
                }
            }
            case 2 -> {
                String firstArgument = args[0];
                String secondArgument = args[1];
                float flyspeed;
                Player player;
                if (firstArgument.equalsIgnoreCase(setArg)) {
                    try {
                        flyspeed = Float.parseFloat(secondArgument);
                        player = (Player) sender;
                        if (flyspeed > ConfigValues.maxFlySpeed || flyspeed < ConfigValues.minFlySpeed) {
                            player.sendMessage(miniMessage.deserialize(ConfigValues.notInRange,
                                    Placeholder.parsed("min", String.valueOf(ConfigValues.minFlySpeed)),
                                    Placeholder.parsed("max", String.valueOf(ConfigValues.maxFlySpeed))));
                            return false;
                        }
                        player.setFlySpeed(flyspeed / 10f);
                        Util.sendUserMessage(player, ConfigValues.flySpeedSetOwn,
                                firstArgument, null);
                        return true;
                    } catch (NumberFormatException e) {
                        player = SimpleFly.getFlyServer().getPlayer(secondArgument);
                        if (player == null || !sender.hasPermission(Util.flySpeedOthersPermission)) {
                            Util.sendUserMessage(sender, ConfigValues.invalidNumber);
                            return false;
                        }
                        Util.sendUserMessage(sender, ConfigValues.notEnoughArguments);
                        return false;
                    }
                }
                if (firstArgument.equalsIgnoreCase(resetArg) && sender.hasPermission(Util.flySpeedOthersPermission)) {
                    player = SimpleFly.getFlyServer().getPlayer(secondArgument);
                    if (player == null) {
                        Util.sendUserMessage(sender, ConfigValues.invalidPlayer);
                        return false;
                    }
                    player.setFlySpeed(0.1f);
                    Util.sendUserMessage(sender, ConfigValues.flySpeedResetOther,
                            null, player);
                    Util.sendUserMessage(player, ConfigValues.flySpeedResetByOther,
                            null, sender);
                    return true;
                }
                if (firstArgument.equalsIgnoreCase(getArg) && sender.hasPermission(Util.flySpeedOthersPermission)) {
                    player = SimpleFly.getFlyServer().getPlayer(secondArgument);
                    if (player == null) {
                        Util.sendUserMessage(sender, ConfigValues.invalidPlayer);
                        return false;
                    }
                    flyspeed = player.getFlySpeed() * 10f;
                    Util.sendUserMessage(sender, ConfigValues.flySpeedGetOther,
                            String.valueOf(flyspeed), player);
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
                        Util.sendUserMessage(sender, ConfigValues.invalidPlayer);
                        return false;
                    }
                    float flyspeed;
                    try {
                        flyspeed = Float.parseFloat(thirdArgument);
                        player.setFlySpeed(flyspeed / 10f);
                        Util.sendUserMessage(sender, ConfigValues.flySpeedSetOther,
                                thirdArgument, player);
                        Util.sendUserMessage(player, ConfigValues.flySpeedSetByOther,
                                thirdArgument, sender);
                        return true;
                    } catch (NumberFormatException e) {
                        Util.sendUserMessage(sender, ConfigValues.invalidNumber);
                        return false;
                    }
                }
                
            }
        }
        return false;
    }
    
    
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (sender.hasPermission(Util.flySpeedPermission) && args.length == 1) {
            tabComplete.clear();
            tabComplete.add(setArg);
            tabComplete.add(resetArg);
            if (sender.hasPermission(Util.flySpeedOthersPermission)) {
                tabComplete.add(getArg);
            }
            return tabComplete;
        }
        if (args.length == 3) {
            return List.of("");
        }
        return null;
    }
}
