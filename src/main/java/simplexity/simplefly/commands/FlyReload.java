package simplexity.simplefly.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;

import simplexity.simplefly.Constants;
import simplexity.simplefly.config.ConfigHandler;
import simplexity.simplefly.config.LocaleMessage;

public class FlyReload {

    public static LiteralArgumentBuilder<CommandSourceStack> createCommand() {
        return Commands.literal("flyreload").requires(ctx -> ctx.getSender().hasPermission(Constants.FLY_RELOAD))
                .executes(ctx -> {
                    ConfigHandler.reloadConfigValues();
                    ctx.getSource().getSender().sendRichMessage(LocaleMessage.FEEDBACK_CONFIG_RELOADED.getMessage());
                    return Command.SINGLE_SUCCESS;
                });
    }
    

}
