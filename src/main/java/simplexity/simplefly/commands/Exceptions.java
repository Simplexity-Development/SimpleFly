package simplexity.simplefly.commands;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import io.papermc.paper.command.brigadier.MessageComponentSerializer;
import net.kyori.adventure.text.minimessage.MiniMessage;
import simplexity.simplefly.SimpleFly;
import simplexity.simplefly.config.LocaleMessage;

@SuppressWarnings("UnstableApiUsage")
public class Exceptions {
    private static final MiniMessage miniMessage = SimpleFly.getMiniMessage();

    public static final SimpleCommandExceptionType ERROR_MUST_BE_PLAYER = new SimpleCommandExceptionType(
            parseMessage(LocaleMessage.ERROR_MUST_BE_PLAYER));

    public static final SimpleCommandExceptionType NO_USERS_FOUND = new SimpleCommandExceptionType(
            parseMessage(LocaleMessage.ERROR_INVALID_PLAYER));


    private static Message parseMessage(LocaleMessage message) {
        return MessageComponentSerializer.message().serialize(
                miniMessage.deserialize(message.getMessage())
        );
    }
}
