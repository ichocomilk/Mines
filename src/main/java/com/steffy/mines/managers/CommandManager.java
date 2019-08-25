package com.steffy.mines.managers;

import com.steffy.mines.utilities.general.Command;
import com.steffy.mines.utilities.general.Manager;
import org.bukkit.plugin.Plugin;

public class CommandManager extends Manager<Command> {

    public CommandManager(Plugin plugin) {
        super("Commands", plugin);
    }
}
