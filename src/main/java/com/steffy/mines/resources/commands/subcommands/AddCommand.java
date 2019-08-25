package com.steffy.mines.resources.commands.subcommands;

import com.steffy.mines.managers.MineManager;
import com.steffy.mines.resources.mines.Mine;
import com.steffy.mines.resources.mines.MineComposition;
import com.steffy.mines.utilities.general.Chat;
import com.steffy.mines.utilities.general.Command;
import com.steffy.mines.utilities.general.Message;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class AddCommand extends Command {

    private final MineManager mineManager;

    public AddCommand(MineManager mineManager) {
        super("add", "[name] [material]:[percentage]");
        this.mineManager = mineManager;
    }

    @Override
    public boolean run(Player player, String[] strings) {
        if(strings.length == 3) {
            String string = strings[1];
            Mine mine = mineManager.getMine(string);
            if(mine == null) {
                player.sendMessage(Chat.format(Message.MINE_NULL.toString().replace("{0}", string)));
            } else {
                string = strings[2];
                String[] splitString = string.split(":");

                String material = splitString[0];
                int percentage = Integer.parseInt(splitString[1]);

                if(Material.matchMaterial(material.toUpperCase()) == null) {
                    player.sendMessage(Chat.format(Message.MINE_UNVALID_MATERIAL.toString().replace("{0}", material)));
                    return true;
                }

                if(mineManager.canComposite(mine, percentage)) {
                    MineComposition mineComposition = mine.getComposition(string);
                    if(mineComposition == null) {
                        mine.add(new MineComposition(material, percentage));
                        player.sendMessage(Chat.format(Message.MINE_ADD.toString()
                                .replace("{0}", material)
                                .replace("{1}", String.valueOf(percentage))
                                .replace("{2}", mine.toString())
                        ));
                    } else {
                        player.sendMessage(Chat.format(Message.MINE_CONTAINS_COMPOSITION.toString()
                                .replace("{1}", material)
                                .replace("{0}", mine.toString())
                        ));
                    }
                } else {
                    float composition = mineManager.availableComposition(mine);
                    player.sendMessage(Chat.format(Message.MINE_COMPOSITION_FULL.toString().replace("{0}", mine.toString())));
                }
            }
        } else {
            player.sendMessage(Chat.format(getUsage()));
        }
        return true;
    }
}
