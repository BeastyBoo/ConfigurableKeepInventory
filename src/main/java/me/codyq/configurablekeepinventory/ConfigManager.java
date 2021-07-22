package me.codyq.configurablekeepinventory;

import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.entity.EntityDamageEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ConfigManager {

    private final ConfigurableKeepInventory plugin;

    private File configFile;
    private YamlConfiguration config;

    public void loadConfig() {
        // Populating the config file if it does not exist
        if (!getConfigFile().exists())
            writeDefaultConfig(false);

        // Load the config from the config file
        try {
            config = new YamlConfiguration();
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public boolean shouldKeepInventory(EntityDamageEvent.DamageCause cause) {
        if (config == null) loadConfig();
        String causeName = cause.name().toUpperCase();
        return config.getBoolean(causeName);
    }

    public void writeDefaultConfig(boolean override) {
        // Getting the config file
        File configFile = getConfigFile();

        // Getting all the keys & generating a config
        List<String> keys = Arrays.stream(EntityDamageEvent.DamageCause.values())
                .map(Enum::toString).map(String::toUpperCase).collect(Collectors.toList());
        String config = keys.stream().map(s -> s.concat(": false\n")).collect(Collectors.joining());

        // Writing to the config file if it doesn't exist
        // or override is set to true.
        if (!configFile.exists() || override) {
            try {
                // Making parent directories
                configFile.getParentFile().mkdirs();

                // Writing the file's contents
                FileWriter writer = new FileWriter(configFile);
                writer.write(config);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private File getConfigFile() {
        File dataFolder = plugin.getDataFolder();
        if (this.configFile == null)
            this.configFile = new File(dataFolder, "config.yml");
        return this.configFile;
    }

}
