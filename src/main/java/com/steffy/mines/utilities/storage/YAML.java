package com.steffy.mines.utilities.storage;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class YAML {

    private Plugin plugin;
    private String name;

    private FileConfiguration fileConfiguration;
    private File file;

    public YAML(Plugin plugin, String name) {
        this.plugin = plugin;
        this.name = name;
    }

    public YAML setup() {
        if(file == null) {
            file = new File(plugin.getDataFolder(), name + ".yml");
        }

        if(!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource(name + ".yml", false);
        }

        reload();
        fileConfiguration.options().copyDefaults(true);
        save();
        return this;
    }

    private void reload(){
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
        InputStream inputStream = plugin.getResource(name + ".yml");
        if(inputStream != null) fileConfiguration.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(inputStream)));
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }
}