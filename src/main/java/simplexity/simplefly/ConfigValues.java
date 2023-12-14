package simplexity.simplefly;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.logging.Logger;

public class ConfigValues {
    private static final Logger logger =  SimpleFly.getFlyServer().getLogger();
    public static String papiFlightEnabled, papiFlightDisabled, prefix, enabled, disabled, flySetOther,
            flySetOwn, flySetByOther, flySpeedGetOther, flySpeedGetOwn, flySpeedSetOwn, flySpeedResetOwn,
            flySpeedSetOther, flySpeedResetOther, flySpeedSetByOther, flySpeedResetByOther,
            invalidPlayer, noPermission, mustBePlayer, notEnoughArguments, invalidNumber, notInRange;
    
    public static float maxFlySpeed, minFlySpeed;
    public static boolean sessionPersistentFlight, worldChangePersistentFlight,
            respawnPersistentFlight, gamemodeChangePersistentFlight;
    
    public static void reloadConfigValues(){
        FileConfiguration config = SimpleFly.getInstance().getConfig();
        maxFlySpeed = config.getInt("max-fly-speed");
        if (maxFlySpeed > 10) {
            logger.warning("Max fly speed cannot be greater than 10. Setting to 10");
            maxFlySpeed = 10;
        }
        minFlySpeed = config.getInt("min-fly-speed");
        if (minFlySpeed < -10) {
            logger.warning("Min fly speed cannot be less than -10. Setting to -10");
            minFlySpeed = -10;
        }
        sessionPersistentFlight = config.getBoolean("session-persistent-flight");
        worldChangePersistentFlight = config.getBoolean("world-change-persistent-flight");
        respawnPersistentFlight = config.getBoolean("respawn-persistent-flight");
        gamemodeChangePersistentFlight = config.getBoolean("gamemode-change-persistent-flight");
        reloadLangValues();
    }
    public static void reloadLangValues(){
        FileConfiguration config = SimpleFly.getInstance().getConfig();
        papiFlightEnabled = config.getString("papi-flight.enabled");
        papiFlightDisabled = config.getString("papi-flight.disabled");
        prefix = config.getString("insertion.prefix");
        enabled = config.getString("insertion.enabled");
        disabled = config.getString("insertion.disabled");
        flySetOther = config.getString("fly-set.other");
        flySetOwn = config.getString("fly-set.own");
        flySetByOther = config.getString("fly-set.by-other");
        flySpeedGetOther = config.getString("fly-speed-get.other");
        flySpeedGetOwn = config.getString("fly-speed-get.own");
        flySpeedSetOwn = config.getString("fly-speed-set.own");
        flySpeedResetOwn = config.getString("fly-speed-reset.own");
        flySpeedSetOther = config.getString("fly-speed-set.other");
        flySpeedResetOther = config.getString("fly-speed-reset.other");
        flySpeedSetByOther = config.getString("fly-speed-set.by-other");
        flySpeedResetByOther = config.getString("fly-speed-reset.by-other");
        invalidPlayer = config.getString("error.invalid-player");
        noPermission = config.getString("error.no-permission");
        mustBePlayer = config.getString("error.must-be-player");
        notEnoughArguments = config.getString("error.not-enough-arguments");
        invalidNumber = config.getString("error.invalid-number");
        notInRange = config.getString("error.not-in-range");
    }

}
