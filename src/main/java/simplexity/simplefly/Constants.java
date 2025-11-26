package simplexity.simplefly;

import org.bukkit.NamespacedKey;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public class Constants {
    

    
    public static final NamespacedKey FLY_STATUS_KEY = new NamespacedKey(SimpleFly.getInstance(), "flystatus");
    public static final Permission FLY_PERMISSION = new Permission("simplefly.fly", "Allows a player to toggle their own flight", PermissionDefault.OP);
    public static final Permission FLY_SPEED_PERMISSION = new Permission("simplefly.flyspeed", "Allows a player to set their own flight speed", PermissionDefault.OP);
    public static final Permission FLY_OTHERS_PERMISSION = new Permission("simplefly.others.fly", "Allows a player to toggle other players' flight", PermissionDefault.OP);
    public static final Permission FLY_SPEED_OTHERS_PERMISSION = new Permission("simplefly.others.flyspeed", "Allows a player to set other players' flight speed", PermissionDefault.OP);
    public static final Permission FLY_RELOAD_PERMISSION = new Permission("simplefly.reload", "Allows a player to reload the SimpleFly configuration", PermissionDefault.OP);

}
