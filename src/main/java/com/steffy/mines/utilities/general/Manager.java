package com.steffy.mines.utilities.general;

import com.steffy.mines.utilities.storage.JSON;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Type;
import java.util.*;

public abstract class Manager<T> {

    private final JSON<T> json;
    private List<T> tList;

    public Manager(String string, Plugin plugin) {
        this.json = new JSON<>(string, plugin);
        this.tList = new ArrayList<>();
    }

    public void deserialize(Type type) {
        this.tList = json.read(type);
    }

    public void serialize() {
        json.write(tList);
    }

    public void add(T t) {
        tList.add(t);
    }

    public void remove(T t) {
        tList.remove(t);
    }

    public T getType(String string) {
        for(T t : tList) {
            if(t.toString().equalsIgnoreCase(string))return t;
        }
        return null;
    }
    public List<T> getTs() {
        return Collections.unmodifiableList(tList);
    }
}

