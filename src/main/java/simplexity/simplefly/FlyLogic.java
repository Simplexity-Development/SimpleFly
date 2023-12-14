package simplexity.simplefly;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class FlyLogic {
    
    private static final NamespacedKey flyStatus = Util.flyStatus;
    
    public static boolean flyEnabled(Player player) {
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();
        Boolean flyState = playerPDC.get(flyStatus, PersistentDataType.BOOLEAN);
        //If they have no set flystate, or it's off, set true, set flying
        if (flyState == null || !flyState) {
            playerPDC.set(flyStatus, PersistentDataType.BOOLEAN, true);
            player.setAllowFlight(true);
            player.setFlying(true);
            return true;
        }
        //If their current flystate is on, set false
        playerPDC.set(flyStatus, PersistentDataType.BOOLEAN, false);
        player.setAllowFlight(false);
        player.setFlying(false);
        return false;
    }
}
