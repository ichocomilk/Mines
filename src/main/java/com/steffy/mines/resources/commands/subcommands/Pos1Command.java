package com.steffy.mines.resources.commands.subcommands;

import com.steffy.mines.managers.MineManager;
import com.steffy.mines.resources.mines.Mine;
import com.steffy.mines.resources.mines.MinePosition;
import com.steffy.mines.utilities.general.Chat;
import com.steffy.mines.utilities.general.Command;
import com.steffy.mines.utilities.general.Message;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Pos1Command extends Command {

    private final MineManager mineManager;

    public Pos1Command(MineManager mineManager) {
        super("pos1", "[name]");
        this.mineManager = mineManager;
    }

    @Override
    public boolean run(Player player, String[] strings) {
        if(strings.length == 2) {
            String string = strings[1];
            Mine mine = mineManager.getMine(string);
            if(mine == null) {
                player.sendMessage(Chat.format(Message.MINE_NULL.toString().replace("{0}", string)));
            } else {
                Block block = player.getLocation().getBlock();
                MinePosition minePosition = new MinePosition(block.getX(), block.getY(), block.getZ());

                mine.setPositionOne(minePosition);
                player.sendMessage(Chat.format(Message.MINE_POSITION_ONE.toString().replace("{0}", string)));
            }
        } else {
            player.sendMessage(Chat.format(getUsage()));
        }
        return true;
    }
}
