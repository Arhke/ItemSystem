package net.waterraid.ItemSystem;

import net.waterraid.ItemSystem.FileIO.GeneralFileIO;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {
    private static JavaPlugin instance = null;
    private static File dataFolder = null;

    @Override
    public void onEnable() {
        Main.instance = this;
        Main.dataFolder = getDataFolder();

        getCommand("ItemSystem").setExecutor(new Commands());
        GeneralFileIO.load();
        Bukkit.getServer().getPluginManager().registerEvents(new Listen(), this);

    }

    @Override
    public void onDisable() {

    }

    /**
     * Returns instance of Plugin
     *
     * @return Plugin instance
     */
    public static JavaPlugin getInstance() {
        return instance;
    }

    public static File getFolder() {
        return dataFolder;
    }

    public static void log(String s) {
        instance.getLogger().warning(s);
    }
}

