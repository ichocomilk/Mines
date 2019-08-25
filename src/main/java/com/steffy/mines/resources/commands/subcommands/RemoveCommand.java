package com.steffy.mines.resources.commands.subcommands;

import com.steffy.mines.managers.MineManager;
import com.steffy.mines.resources.mines.Mine;
import com.steffy.mines.resources.mines.MineComposition;
import com.steffy.mines.utilities.general.Chat;
import com.steffy.mines.utilities.general.Command;
import com.steffy.mines.utilities.general.Message;
import org.bukkit.entity.Player;

public class RemoveCommand extends Command {

    private final MineManager mineManager;

    public RemoveCommand(MineManager mineManager) {
        super("remove", "[name] [material]");
        this.mineManager = mineManager;
    }

    @Override
    public boolean run(Player player, String[] strings) {
        if (strings.length == 3) {
            String string = strings[1];
            Mine mine = mineManager.getMine(string);
            if (mine == null) {
                player.sendMessage(Chat.format(Message.MINE_NULL.toString().replace("{0}", string)));
            } else {
                String material = strings[2];
                MineComposition mineComposition = mine.getComposition(material);
                if (mineComposition == null) {
                    player.sendMessage(Chat.format(Message.MINE_CONTAINS_COMPOSITION_NULL.toString()
                            .replace("{0}", mine.toString())
                            .replace("{1}", material)
                    ));
                } else {
                    mine.remove(mineComposition);
                    player.sendMessage(Chat.format(Message.MINE_REMOVE.toString()
                            .replace("{0}", material)
                            .replace("{1}", mine.toString())
                    ));
                }
            }
        } else {
            player.sendMessage(Chat.format(getUsage()));
        }
        return true;
    }
}