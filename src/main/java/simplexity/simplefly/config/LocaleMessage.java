package simplexity.simplefly.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum LocaleMessage {

    PAPI_FLIGHT_ENABLED("papi-flight.enabled", "[F]"),
    PAPI_FLIGHT_DISABLED("papi-flight.disabled", ""),
    ENABLED("insertion.enabled", "<green>enabled</green>"),
    DISABLED("insertion.disabled", "<red>disabled</red>"),
    SERVER_NAME("insertion.server-name", "<white>[Server]</white>"),
    FLY_SET_OWN("fly-set.own", "<white>Your fly has been <value></white>"),
    FLY_SET_OTHER("fly-set.other", "<white><target>'s fly has been <value></white>"),
    FLY_SET_OTHER_MANY("fly-set.other-many", "<white>You have altered <yellow><count></yellow> player's fly states.</white>"),
    FLY_SET_OTHER_MANY_ARG("fly-set.other-many-with-arg", "<white>You have <value> <yellow><count></yellow> players' fly states</white>"),
    FLY_SET_BY_OTHER("fly-set.by-other", "<white>Your fly has been <value> by <yellow><initiator></yellow></white>"),
    FLY_SPEED_GET_OWN("fly-speed-get.own", "<white>Your flyspeed is currently set to <gray><value></gray></white>"),
    FLY_SPEED_GET_OTHER("fly-speed-get.other", "<white><yellow><target>'s</yellow> current flyspeed is <gray><value></gray></white>"),
    FLY_SPEED_SET_OWN("fly-speed-set.own", "<white>Your flyspeed has been set to <gray><value></gray></white>"),
    FLY_SPEED_SET_OTHER("fly-speed-set.other", "<white>You set <yellow><target>'s</yellow> flyspeed to <value></white>"),
    FLY_SPEED_SET_OTHER_MANY("fly-speed-set.other-many", "<white>You set <yellow><count></yellow> players' flyspeed to <gray><value></gray></white>"),
    FLY_SPEED_SET_BY_OTHER("fly-speed-set.by-other", "<white>Your flyspeed has been set to <gray><value></gray> by <yellow><initiator></yellow></white>"),
    FLY_SPEED_RESET_OWN("fly-speed-reset.own", "<white>Your flyspeed has been <gray>reset</gray></white>"),
    FLY_SPEED_RESET_OTHER("fly-speed-reset.other", "<white><yellow><target>'s</yellow> flyspeed has been reset</white>"),
    FLY_SPEED_RESET_OTHER_MANY("fly-speed-reset.other-many", "<white>Reset <gray><count></gray> players' flyspeed</white>"),
    FLY_SPEED_RESET_BY_OTHER("fly-speed-reset.by-other", "<white>Your flyspeed has been reset by <yellow><initiator></yellow></white>"),
    ERROR_INVALID_PLAYER("error.invalid-player",  "<red>That is not a valid player. Please check your spelling and try again</red>"),
    ERROR_MUST_BE_PLAYER("error.must-be-player", "<red>You must be a player to run this command</red>"),
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
