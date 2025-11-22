package simplexity.simplefly.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum LocaleMessage {

    PAPI_FLIGHT_ENABLED("papi-flight.enabled", "[F]"),
    PAPI_FLIGHT_DISABLED("papi-flight.disabled", ""),
    PREFIX("insertion.prefix", "<green>[<yellow>SF</yellow>]</green> "),
    ENABLED("insertion.enabled", "<green>enabled</green>"),
    DISABLED("insertion.disabled", "<red>disabled</red>"),
    SERVER_NAME("insertion.server-name", "<white>[Server]</white>"),
    FLY_SET_OWN("fly-set.own", "Your fly has been <value>"),
    FLY_SET_OTHER("fly-set.other", "<target>'s fly has been <value>"),
    FLY_SET_OTHER_MANY("fly-set.many-other", "You have altered <count> player's fly states."),
    FLY_SET_MANY_OTHER_ARG("fly-set.many-other-with-arg", "You have <value> <count> players' fly states"),
    FLY_SET_BY_OTHER("fly-set.by-other", "<green>Your fly has been <value> by <initiator></green>"),
    FLY_SPEED_GET_OWN("fly-speed-get.own", "<grey>Your flyspeed is currently set to <value></grey>"),
    FLY_SPEED_GET_OTHER("fly-speed-get.other", "<grey><target>'s current flyspeed is <value></grey>"),
    FLY_SPEED_SET_OWN("fly-speed-set.own", "<green>Your flyspeed has been set to <value></green>"),
    FLY_SPEED_SET_OTHER("fly-speed-set.other", "<green>You set <target>'s flyspeed to <value></green>"),
    FLY_SPEED_SET_BY_OTHER("fly-speed-set.by-other", "<green>Your flyspeed has been set to <value> by <initiator></green>"),
    FLY_SPEED_RESET_OWN("fly-speed-reset.own", "<green>Your flyspeed has been reset</green>"),
    FLY_SPEED_RESET_OTHER("fly-speed-reset.other", "<green><target>'s flyspeed has been reset</green>"),
    FLY_SPEED_RESET_BY_OTHER("fly-speed-reset.by-other", "<green>Your flyspeed has been reset by <initiator></green>"),
    ERROR_INVALID_PLAYER("error.invalid-player",  "<red>That is not a valid player. Please check your spelling and try again</red>"),
    ERROR_NO_PERMISSION("error.no-permission", "<red>You do not have permission to run this command</red>"),
    ERROR_MUST_BE_PLAYER("error.must-be-player", "<red>You must be a player to run this command</red>"),
    ERROR_NOT_ENOUGH_ARGUMENTS("error.not-enough-arguments", "<red>You did not provide enough arguments. Please check your syntax and try again</red>"),
    ERROR_INVALID_NUMBER("error.invalid-number", "<red>Sorry, you did not enter a valid flyspeed, please try again</red>"),
    ERROR_NOT_IN_RANGE("error.not-in-range", "<red>Sorry, you must provide a number between <min> and <max></red>"),
    ERROR_INVALID_COMMAND("error.invalid-command", "<red>Sorry, that subcommand is invalid. Please check your syntax and try again</red>"),
    ERROR_MUST_PROVIDE_STATE("error.must-provide-state", "<red>In order to toggle fly state for multiple players simultaneously, you will need to specify a fly state</red>"),
    FEEDBACK_CONFIG_RELOADED("plugin-messages.config-reloaded", "<gold>Config Reloaded</gold>");
    private final String path;
    private String message;

    LocaleMessage(String path, String message) {
        this.path = path;
        this.message = message;
    }

    @NotNull
    public String getPath() {
        return path;
    }

    @NotNull
    public String getMessage() {
        if (message == null) return "";
        return message;
    }

    public void setMessage(@Nullable String message) {
        if (message == null) message = "";
        this.message = message;
    }
}
