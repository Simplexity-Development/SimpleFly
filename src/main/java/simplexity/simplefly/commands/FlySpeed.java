package simplexity.simplefly.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import simplexity.simplefly.Constants;
import simplexity.simplefly.SimpleFly;
import simplexity.simplefly.config.ConfigHandler;
import simplexity.simplefly.config.LocaleMessage;

import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class FlySpeed {

    private static final MiniMessage miniMessage = SimpleFly.getMiniMessage();

    public static LiteralArgumentBuilder<CommandSourceStack> createCommand() {
        return Commands.literal("flyspeed")
                .requires(src -> src.getSender().hasPermission(Constants.FLY_SPEED_PERMISSION))
                .then(Commands.literal("get")
                        .executes(FlySpeed::getOwnSpeed)
                        .then(Commands.argument("target", ArgumentTypes.player())
                                .executes(FlySpeed::getOtherSpeed)
                        )
                )
                .then(Commands.literal("reset")
                        .executes(FlySpeed::resetOwnSpeed)
                        .then(Commands.argument("targets", ArgumentTypes.players())
                                .requires(css -> css.getSender().hasPermission(Constants.FLY_SPEED_OTHERS_PERMISSION))
                                .executes(FlySpeed::resetOtherSpeed)
                        )
                )
                .then(Commands.literal("set")
                        .then(Commands.argument("speed",
                                        FloatArgumentType.floatArg(ConfigHandler.getInstance().getMinFlySpeed(),
                                                ConfigHandler.getInstance().getMaxFlySpeed()))
                                .executes(FlySpeed::setOwnSpeed)
                                .then(Commands.argument("targets", ArgumentTypes.players())
                                        .requires(css -> css.getSender().hasPermission(Constants.FLY_SPEED_OTHERS_PERMISSION))
                                        .executes(FlySpeed::setOtherSpeed)
                                )
                        )
                );

    }

    private static int getOwnSpeed(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        if (!(context.getSource().getSender() instanceof Player player)) throw Exceptions.ERROR_MUST_BE_PLAYER.create();
        float flyspeedValue = player.getFlySpeed() * 10.0f;
        player.sendRichMessage(LocaleMessage.FLY_SPEED_GET_OWN.getMessage(),
                Placeholder.parsed("value", String.valueOf(flyspeedValue)));
        return Command.SINGLE_SUCCESS;
    }

    private static int getOtherSpeed(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        PlayerSelectorArgumentResolver targetResolver = context.getArgument("target", PlayerSelectorArgumentResolver.class);
        Player target = targetResolver.resolve(context.getSource()).getFirst();
        float targetSpeed = target.getFlySpeed() * 10f;
        CommandSender sender = context.getSource().getSender();
        sender.sendRichMessage(LocaleMessage.FLY_SPEED_GET_OTHER.getMessage(),
                Placeholder.component("target", target.displayName()),
                Placeholder.parsed("value", String.valueOf(targetSpeed)));
        return Command.SINGLE_SUCCESS;
    }

    private static int resetOwnSpeed(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        if (!(context.getSource().getSender() instanceof Player player)) throw Exceptions.ERROR_MUST_BE_PLAYER.create();
        player.setFlySpeed(0.1f);
        player.sendRichMessage(LocaleMessage.FLY_SPEED_RESET_OWN.getMessage());
        return Command.SINGLE_SUCCESS;
    }

    private static int resetOtherSpeed(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSender sender = context.getSource().getSender();
        PlayerSelectorArgumentResolver targetResolver = context.getArgument("targets", PlayerSelectorArgumentResolver.class);
        List<Player> targets = targetResolver.resolve(context.getSource());

        return setSpeed(targets, sender, 1.0f,
                LocaleMessage.FLY_SPEED_RESET_OTHER.getMessage(),
                LocaleMessage.FLY_SPEED_RESET_BY_OTHER.getMessage(),
                LocaleMessage.FLY_SPEED_RESET_OTHER_MANY.getMessage());
    }

    private static int setOwnSpeed(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        if (!(context.getSource().getSender() instanceof Player player)) throw Exceptions.ERROR_MUST_BE_PLAYER.create();
        float speed = context.getArgument("speed", Float.class);
        float actualSpeed = speed / 10f;
        player.setFlySpeed(actualSpeed);
        player.sendRichMessage(LocaleMessage.FLY_SPEED_SET_OWN.getMessage(),
                Placeholder.parsed("value", String.valueOf(speed)));
        return Command.SINGLE_SUCCESS;
    }

    private static int setOtherSpeed(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSender sender = context.getSource().getSender();
        PlayerSelectorArgumentResolver targetResolver = context.getArgument("targets", PlayerSelectorArgumentResolver.class);
        List<Player> targets = targetResolver.resolve(context.getSource());
        float speed = context.getArgument("speed", Float.class);
        return setSpeed(targets, sender, speed,
                LocaleMessage.FLY_SPEED_SET_OTHER.getMessage(),
                LocaleMessage.FLY_SPEED_SET_BY_OTHER.getMessage(),
                LocaleMessage.FLY_SPEED_SET_OTHER_MANY.getMessage());
    }


    @SuppressWarnings("SameReturnValue")
    private static int setSpeed(List<Player> targets, CommandSender sender, float speed, String otherMessage,
                                String byOtherMessage, String manyOtherMessage) throws CommandSyntaxException {
        Component senderName;
        if (sender instanceof Player playerSender) {
            senderName = playerSender.displayName();
        } else {
            senderName = miniMessage.deserialize(LocaleMessage.SERVER_NAME.getMessage());
        }
        float flySpeed = speed / 10f;
        if (targets.isEmpty()) throw Exceptions.NO_USERS_FOUND.create();
        if (targets.size() == 1) {
            Player player = targets.getFirst();
            player.setFlySpeed(flySpeed);
            player.sendRichMessage(
                    byOtherMessage,
                    Placeholder.component("initiator", senderName),
                    Placeholder.parsed("value", String.valueOf(speed))
            );
            sender.sendRichMessage(
                    otherMessage,
                    Placeholder.component("target", player.displayName()),
                    Placeholder.parsed("value", String.valueOf(speed))
            );
            return Command.SINGLE_SUCCESS;
        }
        int modified = 0;
        for (Player player : targets) {
            player.setFlySpeed(flySpeed);
            player.sendRichMessage(
                    byOtherMessage,
                    Placeholder.component("initiator", senderName),
                    Placeholder.parsed("value", String.valueOf(speed)));
            modified++;
        }
        sender.sendRichMessage(manyOtherMessage,
                Placeholder.parsed("count", String.valueOf(modified)),
                Placeholder.parsed("value", String.valueOf(speed)));
        return Command.SINGLE_SUCCESS;
    }
}

