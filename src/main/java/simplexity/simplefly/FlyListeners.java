package simplexity.simplefly;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import simplexity.simplefly.config.ConfigHandler;
import simplexity.simplefly.config.LocaleMessage;

public class FlyListeners implements Listener {

    private static final NamespacedKey flyStatus = Constants.FLY_STATUS;

    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent joinEvent) {
        if (!ConfigHandler.sessionPersistentFlight) {
            return;
        }
        Player player = joinEvent.getPlayer();
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();
        Bukkit.getScheduler().runTaskLater(SimpleFly.getInstance(), () -> {
            boolean flyEnabled = playerPDC.getOrDefault(flyStatus, PersistentDataType.BOOLEAN, false);
            if (flyEnabled && player.hasPermission(Constants.FLY_PERMISSION)) {
                FlyLogic.flyEnable(player);
                player.sendRichMessage(LocaleMessage.FLY_SET_OWN.getMessage(),
                        Placeholder.parsed("value", LocaleMessage.ENABLED.getMessage()));
                return;
            }
            if (flyEnabled && !player.hasPermission(Constants.FLY_PERMISSION)) {
                FlyLogic.flyDisable(player);
            }
        }, 10);
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent worldEvent) {
        if (!ConfigHandler.worldChangePersistentFlight) return;
        Player player = worldEvent.getPlayer();
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();
        Boolean flyEnabled = playerPDC.getOrDefault(flyStatus, PersistentDataType.BOOLEAN, false);
        if (flyEnabled) {
            FlyLogic.flyEnable(player);
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent respawnEvent) {
        if (!ConfigHandler.respawnPersistentFlight) return;
        Player player = respawnEvent.getPlayer();
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();
        Boolean flyEnabled = playerPDC.getOrDefault(flyStatus, PersistentDataType.BOOLEAN, false);
        if (flyEnabled) {
            FlyLogic.flyEnable(player);
        }
    }

    @EventHandler
    public void onGamemodeChange(PlayerGameModeChangeEvent gameModeChangeEvent) {
        if (!ConfigHandler.gamemodeChangePersistentFlight) return;
        Player player = gameModeChangeEvent.getPlayer();
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();
        Bukkit.getScheduler().runTaskLater(SimpleFly.getInstance(), () -> {
            Boolean flyEnabled = playerPDC.getOrDefault(flyStatus, PersistentDataType.BOOLEAN, false);
            if (flyEnabled) {
                FlyLogic.flyEnable(player);
            }
        }, 10);
    }

}
