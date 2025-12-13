package simplexity.simplefly.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.slf4j.Logger;
import simplexity.simplefly.SimpleFly;

public class ConfigHandler {

    private static ConfigHandler instance;

    private ConfigHandler() {
    }

    public static ConfigHandler getInstance() {
        if (instance == null) instance = new ConfigHandler();
        return instance;
    }

    private static final Logger logger = SimpleFly.getInstance().getSLF4JLogger();

    private float maxFlySpeed, minFlySpeed;
    private boolean flyPersistsSessions, flyPersistsWorldChange, flyPersistsRespawn, flyPersistsGameMode;

    public void reloadConfigValues() {
        LocaleHandler.getInstance().reloadLocale();
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
        flyPersistsSessions = config.getBoolean("session-persistent-flight");
        flyPersistsWorldChange = config.getBoolean("world-change-persistent-flight");
        flyPersistsRespawn = config.getBoolean("respawn-persistent-flight");
        flyPersistsGameMode = config.getBoolean("gamemode-change-persistent-flight");
    }

    public float getMinFlySpeed() {
        return minFlySpeed;
    }

    public float getMaxFlySpeed() {
        return maxFlySpeed;
    }


    public boolean isFlyPersistsSessions() {
        return flyPersistsSessions;
    }

    public boolean isFlyPersistsWorldChange() {
        return flyPersistsWorldChange;
    }

    public boolean isFlyPersistsGameMode() {
        return flyPersistsGameMode;
    }

    public boolean isFlyPersistsRespawn() {
        return flyPersistsRespawn;
    }
}
