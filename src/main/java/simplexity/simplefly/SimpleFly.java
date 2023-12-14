package simplexity.simplefly;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimpleFly extends JavaPlugin {
    
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();
    private static SimpleFly instance;
    private static Server flyServer;
    private boolean papiEnabled = false;
    
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
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this).verboseOutput(true));
    }
    
    @Override
    public void onEnable() {
        instance = this;
        papiEnabled = this.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI");
        flyServer = this.getServer();
        CommandAPI.onEnable();
        this.saveDefaultConfig();
        ConfigValues.reloadConfigValues();
        this.getServer().getPluginManager().registerEvents(new simplexity.simplefly.FlyListeners(), this);
        this.getCommand("fly").setExecutor(new simplexity.simplefly.Fly());
        this.getCommand("flyspeed").setExecutor(new simplexity.simplefly.FlySpeed());
        
        // Plugin startup logic
        
    }
    
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
