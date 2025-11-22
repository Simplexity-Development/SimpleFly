package simplexity.simplefly.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public class SuggestionUtils {
    public static CompletableFuture<Suggestions> suggestPlayers(CommandContext<?> context, SuggestionsBuilder builder) {
        CommandSourceStack css = (CommandSourceStack) context.getSource();
        Player playerSender = null;
        if (css.getSender() instanceof Player) playerSender = (Player) css.getSender();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (playerSender == null) {
                builder.suggest(player.getName());
                continue;
            }
            if (playerSender.canSee(player) && !player.equals(playerSender)) {
                builder.suggest(player.getName());
            }
        }
        return builder.buildFuture();
    }
}
