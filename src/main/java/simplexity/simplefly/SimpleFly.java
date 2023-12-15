package simplexity.simplefly;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

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
        this.getCommand("fly").setExecutor(new simplexity.simplefly.Fly());
        this.getCommand("flyspeed").setExecutor(new simplexity.simplefly.FlySpeed());
        this.getCommand("flyreload").setExecutor(new simplexity.simplefly.FlyReload());
    }
    
}
