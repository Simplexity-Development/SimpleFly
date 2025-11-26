package simplexity.simplefly.commands;

import com.mojang.brigadier.Command;
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
import simplexity.simplefly.FlyLogic;
import simplexity.simplefly.SimpleFly;
import simplexity.simplefly.config.LocaleMessage;

import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class Fly {

    private static final MiniMessage miniMessage = SimpleFly.getMiniMessage();

    public static LiteralArgumentBuilder<CommandSourceStack> createCommand() {
        return Commands.literal("fly")
                .requires(css -> css.getSender().hasPermission(Constants.FLY_PERMISSION))
                .executes(Fly::execute)
                .then(Commands.literal("enable").executes(
                        ctx -> executeWithArg(ctx, true))
                        .then(Commands.argument("player", ArgumentTypes.players())
                                .requires(ctx -> ctx.getSender().hasPermission(Constants.FLY_OTHERS_PERMISSION))
                                .suggests(SuggestionUtils::suggestPlayers)
                                .executes(ctx-> executeOnOtherWithArg(ctx, true)))
                )
                .then(Commands.literal("disable").executes(
                        ctx -> executeWithArg(ctx, false))
                        .then(Commands.argument("player", ArgumentTypes.players())
                                .requires(ctx -> ctx.getSender().hasPermission(Constants.FLY_OTHERS_PERMISSION))
                                .suggests(SuggestionUtils::suggestPlayers)
                                .executes(ctx-> executeOnOtherWithArg(ctx, false))));
    }

    private static int execute(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        CommandSender sender = ctx.getSource().getSender();
        if (!(sender instanceof Player player)) throw Exceptions.ERROR_MUST_BE_PLAYER.create();
        boolean flyEnabled = FlyLogic.flyToggle(player);
        sendOwnMessage(flyEnabled, player);
        return Command.SINGLE_SUCCESS;
    }

    private static int executeWithArg(CommandContext<CommandSourceStack> ctx, boolean enableFlight) throws CommandSyntaxException {
        CommandSender sender = ctx.getSource().getSender();
        if (!(sender instanceof Player player)) throw Exceptions.ERROR_MUST_BE_PLAYER.create();
        FlyLogic.setFlyStatus(player, enableFlight);
        sendOwnMessage(enableFlight, player);
        return Command.SINGLE_SUCCESS;
    }

    @SuppressWarnings("SameReturnValue")
    private static int executeOnOtherWithArg(CommandContext<CommandSourceStack> ctx, boolean shouldEnable) throws CommandSyntaxException {
        CommandSender sender = ctx.getSource().getSender();
        PlayerSelectorArgumentResolver playerArg = ctx.getArgument("player", PlayerSelectorArgumentResolver.class);
        List<Player> targets = playerArg.resolve(ctx.getSource());
        if (targets.isEmpty()) throw Exceptions.NO_USERS_FOUND.create();
        if (targets.size() == 1) {
            Player player = targets.getFirst();
            FlyLogic.setFlyStatus(player, shouldEnable);
            player.sendMessage(getParsedComponent(shouldEnable, player, sender, LocaleMessage.FLY_SET_BY_OTHER.getMessage()));
            sender.sendMessage(getParsedComponent(shouldEnable, player, sender, LocaleMessage.FLY_SET_OTHER.getMessage()));
            return Command.SINGLE_SUCCESS;
        }
        for (Player player : targets) {
            FlyLogic.setFlyStatus(player, shouldEnable);
            player.sendMessage(getParsedComponent(shouldEnable, player, sender, LocaleMessage.FLY_SET_BY_OTHER.getMessage()));
        }
        String enabledString = shouldEnable ? LocaleMessage.ENABLED.getMessage() : LocaleMessage.DISABLED.getMessage();
        sender.sendRichMessage(LocaleMessage.FLY_SET_OTHER_MANY_ARG.getMessage(),
                Placeholder.parsed("value", enabledString),
                Placeholder.parsed("count", String.valueOf(targets.size())));
        return Command.SINGLE_SUCCESS;
    }

    private static void sendOwnMessage(boolean flyEnabled, Player player) {
        String enabledString = flyEnabled ? LocaleMessage.ENABLED.getMessage() : LocaleMessage.DISABLED.getMessage();
        player.sendRichMessage(LocaleMessage.FLY_SET_OWN.getMessage(),
                Placeholder.parsed("value", enabledString));
    }

    private static Component getParsedComponent(boolean flyEnabled, Player target, CommandSender source, String message) {
        String enabledString = flyEnabled ? LocaleMessage.ENABLED.getMessage() : LocaleMessage.DISABLED.getMessage();
        Component targetName = target.displayName();
        Component senderName;
        if (source instanceof Player player) {
            senderName = player.displayName();
        } else {
            senderName = miniMessage.deserialize(LocaleMessage.SERVER_NAME.getMessage());
        }
        return miniMessage.deserialize(message,
                Placeholder.parsed("value", enabledString),
                Placeholder.component("initiator", senderName),
                Placeholder.component("target", targetName));
    }


}

