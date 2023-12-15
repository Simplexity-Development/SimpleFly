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
        boolean hasOtherPermission = sender.hasPermission(Util.flyOthersPermission);
        switch (args.length) {
            case 0 -> {
                getOwnFlySpeed(sender);
                return true;
            }
            case 1 -> {
                switch (args[0]) {
                    case setArg -> {
                        Util.sendUserMessage(sender, ConfigValues.notEnoughArguments);
                        return false;
                    }
                    case getArg -> {
                        getOwnFlySpeed(sender);
                        return true;
                    }
                    case resetArg -> {
                        resetOwnFlySpeed(sender);
                        return true;
                    }
                    default -> Util.sendUserMessage(sender, ConfigValues.invalidCommand);
                }
            }
            case 2 -> {
                Player player = SimpleFly.getFlyServer().getPlayer(args[1]);
                switch (args[0]) {
                    case setArg -> {
                        if (player == null || !hasOtherPermission) {
                            setOwnSpeed(sender, args);
                            return true;
                        } else {
                            Util.sendUserMessage(sender, ConfigValues.notEnoughArguments);
                            return false;
                        }
                    }
                    case getArg -> {
                        if (player == null || !hasOtherPermission) {
                            Util.sendUserMessage(sender, ConfigValues.invalidCommand);
                            return false;
                        } else {
                            getOtherFlySpeed(sender, args);
                            return true;
                        }
                    }
                    case resetArg -> {
                        if (player == null || !hasOtherPermission) {
                            Util.sendUserMessage(sender, ConfigValues.invalidCommand);
                            return true;
                        } else {
                            resetOtherFlySpeed(sender, args);
                            return true;
                        }
                    }
                    default -> Util.sendUserMessage(sender, ConfigValues.invalidCommand);
                }
            }
            case 3 -> {
                if (args[0].equals(setArg) && hasOtherPermission) {
                    setOtherPlayerSpeed(sender, args);
                    return true;
                }
                Util.sendUserMessage(sender, ConfigValues.invalidCommand);
            }
        }
        return false;
    }
    
    
    private void setOtherPlayerSpeed(CommandSender sender, String[] args) {
        String firstArgument = args[0];
        String secondArgument = args[1];
        String thirdArgument = args[2];
        if (firstArgument.equalsIgnoreCase(setArg)) {
            Player player = SimpleFly.getFlyServer().getPlayer(secondArgument);
            if (player == null) {
                Util.sendUserMessage(sender, ConfigValues.invalidPlayer);
                return;
            }
            float flyspeed;
            try {
                flyspeed = Float.parseFloat(thirdArgument);
                player.setFlySpeed(flyspeed / 10f);
                Util.sendUserMessage(sender, ConfigValues.flySpeedSetOther,
                        thirdArgument, player);
                Util.sendUserMessage(player, ConfigValues.flySpeedSetByOther,
                        thirdArgument, sender);
            } catch (NumberFormatException e) {
                Util.sendUserMessage(sender, ConfigValues.invalidNumber);
            }
        }
    }
    
    private void resetOtherFlySpeed(CommandSender sender, String[] args) {
        Player player = SimpleFly.getFlyServer().getPlayer(args[1]);
        if (player == null) {
            Util.sendUserMessage(sender, ConfigValues.invalidPlayer);
            return;
        }
        player.setFlySpeed(0.1f);
        Util.sendUserMessage(sender, ConfigValues.flySpeedResetOther,
                null, player);
        Util.sendUserMessage(player, ConfigValues.flySpeedResetByOther,
                null, sender);
    }
    
    private void getOtherFlySpeed(CommandSender sender, String[] args) {
        Player player = SimpleFly.getFlyServer().getPlayer(args[1]);
        if (player == null) {
            Util.sendUserMessage(sender, ConfigValues.invalidPlayer);
            return;
        }
        float flyspeed = player.getFlySpeed() * 10f;
        Util.sendUserMessage(sender, ConfigValues.flySpeedGetOther,
                String.valueOf(flyspeed), player);
    }
    
    private void setOwnSpeed(CommandSender sender, String[] args) {
        float flyspeed;
        Player player;
        String secondArgument = args[1];
        try {
            flyspeed = Float.parseFloat(secondArgument);
            player = (Player) sender;
            if (flyspeed > ConfigValues.maxFlySpeed || flyspeed < ConfigValues.minFlySpeed) {
                player.sendMessage(miniMessage.deserialize(ConfigValues.notInRange,
                        Placeholder.parsed("min", String.valueOf(ConfigValues.minFlySpeed)),
                        Placeholder.parsed("max", String.valueOf(ConfigValues.maxFlySpeed))));
                return;
            }
            player.setFlySpeed(flyspeed / 10f);
            Util.sendUserMessage(player, ConfigValues.flySpeedSetOwn,
                    secondArgument, null);
        } catch (NumberFormatException e) {
            player = SimpleFly.getFlyServer().getPlayer(secondArgument);
            if (player == null || !sender.hasPermission(Util.flySpeedOthersPermission)) {
                Util.sendUserMessage(sender, ConfigValues.invalidNumber);
                return;
            }
            Util.sendUserMessage(sender, ConfigValues.notEnoughArguments);
        }
    }
    
    private void resetOwnFlySpeed(CommandSender sender) {
        if (CommandUtils.checkIfPlayerAndPerms(sender, Util.flySpeedPermission)) {
            ((Player) sender).setFlySpeed(0.1f);
            Util.sendUserMessage(sender, ConfigValues.flySpeedResetOwn);
        }
    }
    
    private void getOwnFlySpeed(CommandSender sender) {
        if (CommandUtils.checkIfPlayerAndPerms(sender, Util.flySpeedPermission)) {
            float flyspeed = ((Player) sender).getFlySpeed() * 10f;
            Util.sendUserMessage(sender, ConfigValues.flySpeedGetOwn,
                    String.valueOf(flyspeed), null);
        }
    }
    
    
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command
            command, @NotNull String s, @NotNull String[] args) {
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
