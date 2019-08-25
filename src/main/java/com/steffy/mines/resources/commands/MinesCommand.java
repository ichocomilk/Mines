package com.steffy.mines.resources.commands;

import com.steffy.mines.utilities.general.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.*;

public class MinesCommand extends Command implements TabCompleter {

    private final Set<Command> commands;

    public MinesCommand() {
        super("mines", "");
        this.commands = new HashSet<>();
    }

    public boolean run(Player player, String[] args) {
        for (Command command : commands) {
            if (command.toString().equalsIgnoreCase(args[0])) {
                if (player.hasPermission(toString() + "." + command.toString())) {
                    return command.run(player, args);
                }
            }
        }
        return true;
    }

    public void initialise(Command... commands) {
        this.commands.addAll(Arrays.asList(commands));
    }

    public Set<Command> getCommands() {
        return Collections.unmodifiableSet(commands);
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
        List<String> list = new ArrayList<>();
        if(strings.length == 1) {
            commands.forEach(subCommand -> list.add(subCommand.toString()));
        }
        return list;
    }
}
