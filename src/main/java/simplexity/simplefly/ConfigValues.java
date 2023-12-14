package simplexity.simplefly;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.logging.Logger;

public class ConfigValues {
    private static final Logger logger =  SimpleFly.getFlyServer().getLogger();
    public static String papiFlightEnabled, papiFlightDisabled, prefix, enabled, disabled, flyOther,
            flyOwn, flySetByOther, flySpeedOther, flySpeedOwn, flySpeedSet, flySpeedReset,
            flySpeedSetOther, flySpeedResetOther, flySpeedSetByOther, flySpeedResetByOther,
            notAPlayer, noPermission, mustBePlayer, notEnoughArguments, notANumber, notInRange;
    
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
        papiFlightEnabled = config.getString("papi-flight-enabled");
        papiFlightDisabled = config.getString("papi-flight-disabled");
        prefix = config.getString("prefix");
        enabled = config.getString("enabled");
        disabled = config.getString("disabled");
        flyOther = config.getString("fly-other");
        flyOwn = config.getString("fly-own");
        flySetByOther = config.getString("fly-set-by-other");
        flySpeedOther = config.getString("fly-speed-other");
        flySpeedOwn = config.getString("fly-speed-own");
        flySpeedSet = config.getString("fly-speed-set");
        flySpeedReset = config.getString("fly-speed-reset");
        flySpeedSetOther = config.getString("fly-speed-set-other");
        flySpeedResetOther = config.getString("fly-speed-reset-other");
        flySpeedSetByOther = config.getString("fly-speed-set-by-other");
        flySpeedResetByOther = config.getString("fly-speed-reset-by-other");
        notAPlayer = config.getString("not-a-player");
        noPermission = config.getString("no-permission");
        mustBePlayer = config.getString("must-be-player");
        notEnoughArguments = config.getString("not-enough-arguments");
        notANumber = config.getString("not-a-number");
        notInRange = config.getString("not-in-range");
    }

}
