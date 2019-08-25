package com.steffy.mines.resources.commands.subcommands;

import com.steffy.mines.managers.MineManager;
import com.steffy.mines.resources.mines.Mine;
import com.steffy.mines.utilities.general.Chat;
import com.steffy.mines.utilities.general.Command;
import com.steffy.mines.utilities.general.Message;
import org.bukkit.entity.Player;

public class ResetCommand extends Command {

    private final MineManager mineManager;

    public ResetCommand(MineManager mineManager) {
        super("reset", "[name]");
        this.mineManager = mineManager;
    }

    @Override
    public boolean run(Player player, String[] strings) {
        if (strings.length == 2) {
            String string = strings[1];
            Mine mine = mineManager.getMine(string);
            if (mine == null) {
                player.sendMessage(Chat.format(Message.MINE_RESET.toString().replace("{0}", string)));
            } else {
                mineManager.reset(mine, player.getWorld());
            }
        } else {
            player.sendMessage(Chat.format(getUsage()));
        }
        return true;
    }
}
