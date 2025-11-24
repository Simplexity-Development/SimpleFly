package simplexity.simplefly.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.slf4j.Logger;
import simplexity.simplefly.SimpleFly;

public class ConfigHandler {
    private static final Logger logger =  SimpleFly.getInstance().getSLF4JLogger();
    
    public static float maxFlySpeed, minFlySpeed;
    public static boolean sessionPersistentFlight, worldChangePersistentFlight,
            respawnPersistentFlight, gamemodeChangePersistentFlight;
    
    public static void reloadConfigValues(){
        FileConfiguration config = SimpleFly.getInstance().getConfig();
        maxFlySpeed = config.getInt("max-fly-speed");
        if (maxFlySpeed > 10) {
            logger.warn("Max fly speed cannot be greater than 10. Setting to 10");
            maxFlySpeed = 10;
        }
        minFlySpeed = config.getInt("min-fly-speed");
        if (minFlySpeed < -10) {
            logger.warn("Min fly speed cannot be less than -10. Setting to -10");
            minFlySpeed = -10;
        }
        sessionPersistentFlight = config.getBoolean("session-persistent-flight");
        worldChangePersistentFlight = config.getBoolean("world-change-persistent-flight");
        respawnPersistentFlight = config.getBoolean("respawn-persistent-flight");
        gamemodeChangePersistentFlight = config.getBoolean("gamemode-change-persistent-flight");
    }

}
