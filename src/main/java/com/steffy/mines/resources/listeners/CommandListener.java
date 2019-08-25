package com.steffy.mines.resources.listeners;

import com.steffy.mines.managers.CommandManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener {

    private final CommandManager commandManager;

    public CommandListener(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String[] split = event.getMessage().substring(1).split(" ");

        String string = split[0];
        String[] strings = new String[split.length-1];
        System.arraycopy(split, 1, strings, 0, split.length - 1);

        Player player = event.getPlayer();

        commandManager.getTs().forEach(command -> {
            if(command.toString().equalsIgnoreCase(string)) {
                command.run(player, strings);
                event.setCancelled(true);
                return;
            }
            command.getAliases().forEach(s -> {
                if(s.equalsIgnoreCase(string)) {
                    command.run(player, strings);
                    event.setCancelled(true);
                }
            });
        });
    }
}
