package me.codyq.configurablekeepinventory;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigurableKeepInventory extends JavaPlugin {

    @Override
    public void onEnable() {
        // Setting up bStats (metrics)
        Metrics metrics = new Metrics(this, 12153);

        // Loading the config manager, and writing the default config
        ConfigManager configManager = new ConfigManager(this);
        configManager.writeDefaultConfig(false);

        // Registering the events
        Bukkit.getPluginManager().registerEvents(new DeathListener(configManager), this);
    }

}
