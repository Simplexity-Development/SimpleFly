package simplexity.simplefly;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.java.JavaPlugin;
import simplexity.simplefly.commands.Fly;
import simplexity.simplefly.commands.FlyReload;
import simplexity.simplefly.commands.FlySpeed;
import simplexity.simplefly.config.ConfigHandler;

@SuppressWarnings("UnstableApiUsage")
public final class SimpleFly extends JavaPlugin {

    private static final MiniMessage miniMessage = MiniMessage.miniMessage();
    private static SimpleFly instance;

    public static SimpleFly getInstance() {
        return instance;
    }

    public static MiniMessage getMiniMessage() {
        return miniMessage;
    }

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        ConfigHandler.getInstance().reloadConfigValues();
        this.getServer().getPluginManager().registerEvents(new simplexity.simplefly.FlyListeners(), this);
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commands.registrar().register(Fly.createCommand().build());
            commands.registrar().register(FlyReload.createCommand().build());
            commands.registrar().register(FlySpeed.createCommand().build());
        });
        this.getServer().getPluginManager().addPermission(Constants.FLY_PERMISSION);
        this.getServer().getPluginManager().addPermission(Constants.FLY_OTHERS_PERMISSION);
        this.getServer().getPluginManager().addPermission(Constants.FLY_SPEED_PERMISSION);
        this.getServer().getPluginManager().addPermission(Constants.FLY_SPEED_OTHERS_PERMISSION);
        this.getServer().getPluginManager().addPermission(Constants.FLY_RELOAD_PERMISSION);
    }

}
