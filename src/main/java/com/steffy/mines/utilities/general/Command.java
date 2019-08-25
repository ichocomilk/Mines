package com.steffy.mines.utilities.general;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public abstract class Command {

    private final String string, usage;
    private final Set<String> aliases;

    public Command(String string, String usage) {
        this.string = string;
        this.usage = string + " " + usage;
        this.aliases = new HashSet<>();
    }

    public abstract boolean run(Player player, String[] strings);

    public String getUsage() {
        return usage;
    }

    public Command add(String string) {
        this.aliases.add(string);
        return this;
    }

    public Set<String> getAliases() {
        return aliases;
    }

    @Override
    public String toString() {
        return string;
    }
}
