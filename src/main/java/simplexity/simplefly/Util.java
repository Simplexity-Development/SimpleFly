package simplexity.simplefly;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.CommandSender;

public class Util {

    private static final MiniMessage miniMessage = SimpleFly.getMiniMessage();

    public static void sendUserMessage(CommandSender sender, String message) {
        if (message.isEmpty()) return;
        sender.sendMessage(miniMessage.deserialize(message));
    }

    public static void sendUserMessage(CommandSender userToSendTo, String message,
                                       String value, CommandSender userToParse) {
        if (message.isEmpty()) return;
        Component parsedName;
        if (userToParse == null) {
            parsedName = Component.empty();
        } else {
            parsedName = userToParse.name();
        }
        userToSendTo.sendMessage(miniMessage.deserialize(message,
                Placeholder.parsed("value", value),
                Placeholder.component("initiator", parsedName),
                Placeholder.component("target", parsedName)));
    }
}
