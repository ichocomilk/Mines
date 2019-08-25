package com.steffy.mines.utilities.general;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public enum  Message {
    MINE_ADD("mine-add", "You have added a new composition &b{0} of {1}% &7to the &b{2}&7 mine."),
    MINE_BROADCAST("mine-broadcast", "The &a{0} &7mine has been reset."),
    MINE_COMPOSITION_FULL("mine-composition-full", "You can't add more to the composition of the &b{0} &7mine as it is at 100%."),
    MINE_CONTAINS_COMPOSITION("mine-contains-composition", "The &b{0} &7mine already contains &b{1} &7as part of it's composition."),
    MINE_CONTAINS_COMPOSITION_NULL("mine-contains-composition-null", "The &b{0} &7mine does not contain &b{1}&7."),
    MINE_CREATE("mine-create", "Successfully created mine: &b{0}"),
    MINE_DELETE("mine-delete", "Successfully deleted mine: &b{0}"),
    MINE_LOCATION("mine-location", "You have set the spawn location for the &b{0} &7mine."),
    MINE_NOT_NULL("mine-not-null", "The &b{0} &7mine already exists."),
    MINE_NULL("mine-null", "The &b{0} &7mine does not seem to exist."),
    MINE_POSITION_ONE("mine-position-one", "You have successfully set the first position for the &b{0} &7mine."),
    MINE_POSITION_TWO("mine-position-twp", "You have successfully set the second position for the &b{0} &7mine."),
    MINE_REMOVE("mine-remove", "You have removed &b{0} composition from the &b{1} &7mine."),
    MINE_RESET("mine-reset", "You have reset the &b{0} &7mine."),
    MINE_UNVALID_MATERIAL("mine-unvalid-material", "&b{0} &7is not a valid material."),
    PAGE_FORMAT("page-format", "&b{0}. &7{1}"),
    PAGE_HELP("page-help", "&e&lHelp: &7[{0}/{1}]"),
    PAGE_LIMIT("page-limit", "There are only &b{0} &7help pages."),
    PAGE_NEXT("page-next", "Type &b/mines help {0} &7for the next page."),
    PAGE_PREVIOUS("page-previous", "Type &b/mines help {0} &7for the previous page."),
    PREFIX("prefix", "&e&lMines: &7");


    private String path, def;
    private List<String> list;
    private static FileConfiguration configuration;

    Message(String path, String def) {
        this.path = path;
        this.def = def;
    }

    Message(String path, List<String> list) {
        this.path = path;
        this.list = list;
    }

    @Override
    public String toString() {
        return Chat.color(configuration.getString(path, def));
    }

    public List<String> toList() {
        return configuration.getStringList(path);
    }

    public static void setConfiguration(FileConfiguration configuration) {
        Message.configuration = configuration;
    }

    public String getPath() {
        return path;
    }

    public String getDef() {
        return def;
    }

    public List<String> getList() {
        return list;
    }
}
