package simplexity.simplefly;

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

public class FlyListeners implements Listener {
    private static final NamespacedKey flyStatus = Util.flyStatus;
    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent joinEvent) {
        if (!ConfigValues.sessionPersistentFlight) {
            return;
        }
        Player player = joinEvent.getPlayer();
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();
        Bukkit.getScheduler().runTaskLater(SimpleFly.getInstance(), () -> {
            boolean flyEnabled = playerPDC.getOrDefault(flyStatus, PersistentDataType.BOOLEAN, false);
            if (flyEnabled && player.hasPermission(Util.flyPermission)) {
                player.setAllowFlight(true);
                if (player.getFallDistance() > 0f) {
                    player.setFlying(true);
                }
                Util.sendUserMessage(player, ConfigValues.flyOwn, ConfigValues.enabled, null);
                return;
            }
            if (flyEnabled && !player.hasPermission(Util.flyPermission)) {
                playerPDC.set(flyStatus, PersistentDataType.BOOLEAN, false);
            }
        }, 10);
    }
    
    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent worldEvent) {
        if (!ConfigValues.worldChangePersistentFlight) return;
        Player player = worldEvent.getPlayer();
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();
        Boolean flyEnabled = playerPDC.getOrDefault(flyStatus, PersistentDataType.BOOLEAN, false);
        if (flyEnabled) {
            player.setAllowFlight(true);
            if (player.getFallDistance() > 0f) {
                player.setFlying(true);
            }
        }
    }
    
    @EventHandler
    public void onRespawn(PlayerRespawnEvent respawnEvent) {
        if (!ConfigValues.respawnPersistentFlight) return;
        Player player = respawnEvent.getPlayer();
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();
        Boolean flyEnabled = playerPDC.getOrDefault(flyStatus, PersistentDataType.BOOLEAN, false);
        if (flyEnabled) {
            player.setAllowFlight(true);
        }
    }
    
    @EventHandler
    public void onGamemodeChange(PlayerGameModeChangeEvent gameModeChangeEvent) {
        if (!ConfigValues.gamemodeChangePersistentFlight) return;
        Player player = gameModeChangeEvent.getPlayer();
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();
        Bukkit.getScheduler().runTaskLater(SimpleFly.getInstance(), () -> {
            Boolean flyEnabled = playerPDC.getOrDefault(flyStatus, PersistentDataType.BOOLEAN, false);
            if (flyEnabled) {
                player.setAllowFlight(true);
                if (player.getFallDistance() > 0f) {
                    player.setFlying(true);
                }
            }
        }, 10);
    }


}
