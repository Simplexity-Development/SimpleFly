package simplexity.simplefly;

import io.papermc.paper.plugin.lifecycle.event.LifecycleEvent;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import simplexity.simplefly.commands.Fly;
import simplexity.simplefly.commands.FlyReload;
import simplexity.simplefly.commands.FlySpeed;
import simplexity.simplefly.config.ConfigValues;

public final class SimpleFly extends JavaPlugin {
    
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();
    private static SimpleFly instance;
    private static Server flyServer;
    
    public static SimpleFly getInstance() {
        return instance;
    }
    
    public static MiniMessage getMiniMessage() {
        return miniMessage;
    }
    
    public static Server getFlyServer() {
        return flyServer;
    }
    
    
    @Override
    public void onEnable() {
        instance = this;
        flyServer = this.getServer();
        this.saveDefaultConfig();
        ConfigValues.reloadConfigValues();
        this.getServer().getPluginManager().registerEvents(new simplexity.simplefly.FlyListeners(), this);
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commands.registrar().register(Fly.createCommand().build());
        });
        this.getCommand("flyreload").setExecutor(new FlyReload());
    }
    
}
