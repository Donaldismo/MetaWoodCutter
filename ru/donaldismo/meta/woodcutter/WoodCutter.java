package ru.donaldismo.meta.woodcutter;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class WoodCutter extends JavaPlugin {

    public void onEnable() {
        this.loadPlugin(); 
    }

    public void onDisable() {
        this.disablePlugin();
    }

    public void disablePlugin() {
        this.getLogger().info("MetaWoodCutter by Donaldismo Disabled!");
        this.getPluginLoader().disablePlugin((Plugin)this);
    }

    void loadPlugin() {
        this.getServer().getPluginManager().registerEvents((Listener)new EventListener(this), (Plugin)this);
        this.getLogger().info("MetaWoodCutter by Donaldismo Enabled!");
    }
}

