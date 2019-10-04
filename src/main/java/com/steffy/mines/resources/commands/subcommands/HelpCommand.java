package com.steffy.mines.resources.commands.subcommands;

import com.steffy.mines.resources.commands.MinesCommand;
import com.steffy.mines.utilities.general.Chat;
import com.steffy.mines.utilities.general.Command;
import com.steffy.mines.utilities.general.Message;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HelpCommand extends Command {

    private final MinesCommand minesCommand;

    public HelpCommand(MinesCommand minesCommand) {
        super("help", "[page]");

        this.minesCommand = minesCommand;
    }

    @Override
    public boolean run(Player player, String[] strings) {
        if(strings.length == 2) {
            if(!StringUtils.isNumeric(strings[1])) {
                player.sendMessage(Chat.format(Message.PAGE_NUMBER.toString()));
                return true;
            }

            int index = Integer.parseInt(strings[1]);
            int page = (int) Math.ceil((double) minesCommand.getCommands().size() / 6);
            if(index == 0) index = 1;

            if (index > page) {
                player.sendMessage(Chat.format(Message.PAGE_LIMIT.toString().replace("{0}", String.valueOf(page))));
            } else {
                player.sendMessage(Message.PAGE_HELP.toString()
                        .replace("{0}", String.valueOf(index))
                        .replace("{1}", String.valueOf(page))
                );
                player.sendMessage("");

                List<String> stringList = new ArrayList<>();

                minesCommand.getCommands().forEach(command -> stringList.add(command.getUsage()));
                Collections.sort(stringList);

                for (int i = 0; i < stringList.size(); i++) {
                    if (i >= 6) break;
                    int number = i + ((index - 1) * 6);
                    if (number >= stringList.size()) {
                        player.sendMessage("");
                    } else {
                        String string = stringList.get(number);
                        player.sendMessage(Chat.color(Message.PAGE_FORMAT.toString()
                                .replace("{0}", String.valueOf(number + 1))
                                .replace("{1}", "/mines " + string)
                        ));
                    }
                }

                player.sendMessage("");
                if ((index + 1) > page) {
                    player.sendMessage(Chat.format(Message.PAGE_PREVIOUS.toString().replace("{0}", String.valueOf(index - 1))
                    ));
                } else {
                    player.sendMessage(Chat.format(Message.PAGE_NEXT.toString().replace("{0}", String.valueOf(index + 1))));
                }
            }
        } else {
            player.sendMessage(Chat.format(getUsage()));
        }
        return true;
    }
}