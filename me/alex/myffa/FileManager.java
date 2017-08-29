package me.alex.myffa;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager {

    private File customFile;
    private FileConfiguration customConfig;

    public FileManager(String nameConfig) {
        customFile = new File(Main.getInstance().getDataFolder() + "/" + nameConfig + ".yml");
        customConfig = YamlConfiguration.loadConfiguration(customFile);
    }

    public void set(String path, Object value) {
        try {
            customConfig.set(path, value);
            customConfig.save(customFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getString(String path) {
        return customConfig.getString(path);
    }
    public boolean getBoolean(String path) {
    	return customConfig.getBoolean(path);
    }
    public int getInt(String path) {
    	return customConfig.getInt(path);
    }
    public double getDouble(String path) {
    	return customConfig.getDouble(path);
    }
    public ConfigurationSection getConfigurationSection(String path) {
    	return customConfig.getConfigurationSection(path);
    }
    public boolean contains(String arg0) {
    	return customConfig.contains(arg0);
    }
    public Object get(String path) {
    	return customConfig.get(path);
    }
}
