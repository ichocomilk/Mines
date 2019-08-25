package com.steffy.mines;

import com.google.gson.reflect.TypeToken;
import com.steffy.mines.managers.MineManager;
import com.steffy.mines.resources.commands.MinesCommand;
import com.steffy.mines.resources.commands.subcommands.*;
import com.steffy.mines.resources.listeners.CommandListener;
import com.steffy.mines.managers.CommandManager;
import com.steffy.mines.resources.listeners.MineListener;
import com.steffy.mines.resources.mines.Mine;
import com.steffy.mines.utilities.general.Message;
import com.steffy.mines.utilities.storage.YAML;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class Mines extends JavaPlugin {

    private CommandManager commandManager;
    private MineManager mineManager;

    public void onEnable() {
        mineManager = new MineManager(this);
        commandManager = new CommandManager(this);

        mineManager = new MineManager(this);
        mineManager.deserialize(new TypeToken<List<Mine>>(){}.getType());

        MinesCommand minesCommand = new MinesCommand();
        minesCommand.initialise(
                new AddCommand(mineManager),
                new CreateCommand(mineManager),
                new DeleteCommand(mineManager),
                new HelpCommand(minesCommand),
                new LocationCommand(mineManager),
                new Pos1Command(mineManager),
                new Pos2Command(mineManager),
                new RemoveCommand(mineManager),
                new ResetCommand(mineManager)
        );

        registerMessages(new YAML(this, "messages").setup());
        registerListeners(
                new CommandListener(commandManager),
                new MineListener(this)
        );

        commandManager.add(minesCommand);
    }

    public void onDisable() {
        mineManager.serialize();
    }

    private void registerListeners(Listener... listeners) {
        Arrays.asList(listeners).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    private void registerMessages(YAML yaml) {
        Message.setConfiguration(yaml.getFileConfiguration());
        for(Message message: Message.values()) {
            if (yaml.getFileConfiguration().getString(message.getPath()) == null) {
                if(message.getList() != null) {
                    yaml.getFileConfiguration().set(message.getPath(), message.getList());
                } else {
                    yaml.getFileConfiguration().set(message.getPath(), message.getDef());
                }
            }
        }
        yaml.save();
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public MineManager getMineManager() {
        return mineManager;
    }
}
