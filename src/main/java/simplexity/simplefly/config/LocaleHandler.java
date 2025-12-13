package simplexity.simplefly.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.slf4j.Logger;
import simplexity.simplefly.SimpleFly;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class LocaleHandler {


    private static final Logger logger = SimpleFly.getInstance().getSLF4JLogger();
    private static LocaleHandler instance;
    private final String fileName = "locale.yml";
    private final File dataFile = new File(SimpleFly.getInstance().getDataFolder(), fileName);
    private FileConfiguration locale = new YamlConfiguration();

    private LocaleHandler() {
        Path dataFilePath = dataFile.toPath();
        Path parentDir = dataFilePath.getParent();
        try {
            if (parentDir != null) {
                Files.createDirectories(parentDir);
            }

            if (Files.notExists(dataFilePath)) {
                Files.createFile(dataFilePath);
            }

            reloadLocale();
        } catch (IOException e) {
            logger.warn("Unable to initialize locale.yml! Error: {}", e.getMessage(), e);
        }
    }

    public static LocaleHandler getInstance() {
        if (instance == null) {
            instance = new LocaleHandler();
        }
        return instance;
    }

    public void reloadLocale() {
        try {
            locale.load(dataFile);
            populateLocale();
            sortLocale();
            saveLocale();
        } catch (IOException | InvalidConfigurationException e) {
            logger.warn("Unable to reload Locale! Error: {}", e.getMessage(), e);
        }
    }


    private void populateLocale() {
        Set<LocaleMessage> missing = new HashSet<>(Arrays.asList(LocaleMessage.values()));
        for (LocaleMessage localeMessage : LocaleMessage.values()) {
            if (locale.contains(localeMessage.getPath())) {
                localeMessage.setMessage(locale.getString(localeMessage.getPath()));
                missing.remove(localeMessage);
            }
        }

        for (LocaleMessage localeMessage : missing) {
            locale.set(localeMessage.getPath(), localeMessage.getMessage());
        }


    }

    private void sortLocale() {
        FileConfiguration newLocale = new YamlConfiguration();
        List<String> keys = new ArrayList<>(locale.getKeys(true));
        Collections.sort(keys);
        for (String key : keys) {
            newLocale.set(key, locale.getString(key));
        }
        locale = newLocale;
    }

    private void saveLocale() {
        try {
            locale.save(dataFile);
        } catch (IOException e) {
            logger.warn("Unable to save Locale! Error: {}", e.getMessage(), e);
        }
    }
}
