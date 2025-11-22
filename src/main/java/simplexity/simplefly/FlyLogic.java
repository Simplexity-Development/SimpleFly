package simplexity.simplefly;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class FlyLogic {
    
    private static final NamespacedKey flyStatus = Constants.FLY_STATUS;
    
    public static boolean flyToggle(Player player) {
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();
        Boolean flyState = playerPDC.get(flyStatus, PersistentDataType.BOOLEAN);
        if (flyState == null || !flyState) {
            flyEnable(player);
            return true;
        }
        flyDisable(player);
        return false;
    }

    public static void setFlyStatus(Player player, boolean enable){
        if (enable && willFall(player)) player.setFlying(true);
        player.getPersistentDataContainer().set(flyStatus, PersistentDataType.BOOLEAN, enable);
        player.setAllowFlight(enable);
    }
    public static void flyEnable(Player player) {
        player.getPersistentDataContainer().set(flyStatus, PersistentDataType.BOOLEAN, true);
        player.setAllowFlight(true);
        if (willFall(player)) {
            player.setFlying(true);
        }
    }

    public static void flyDisable(Player player){
        player.getPersistentDataContainer().set(flyStatus, PersistentDataType.BOOLEAN, false);
        player.setAllowFlight(false);
        player.setFlying(false);
    }

    private static boolean willFall(Player player){
        Location location = player.getLocation();
        Block blockBelow = location.clone().add(0, -1, 0).getBlock();
        Block blockTwoBelow = location.clone().add(0, -2, 0).getBlock();
        return blockBelow.isPassable() && blockTwoBelow.isPassable();
    }
}
