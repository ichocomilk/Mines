package com.steffy.mines.utilities.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JSON<T> {

    private final Gson gson;
    private final File file;

    public JSON(String file, Plugin plugin) {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.file =  new File(plugin.getDataFolder().getPath() + File.separator + file + ".json");

        if (!this.file.exists()) this.file.getParentFile().mkdirs();
    }

    public void write(List<T> ts) {
        if(!file.exists()) file.getParentFile().mkdirs();

        try (FileWriter fileWriter = new FileWriter(file)) {

            fileWriter.write(gson.toJson(ts));
            fileWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<T> read(Type type) {
        JsonReader reader = null;

        if(!file.exists()) {
            file.getParentFile().mkdirs();
        } else {
            try {
                reader = new JsonReader(new FileReader(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        if(reader == null) return new ArrayList<>();
        return gson.fromJson(reader, type);
    }
}