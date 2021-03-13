package com.github.pk7r.simplexevents.commands.chat;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CatchUnknown;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import com.github.pk7r.simplexevents.Main;
import org.bukkit.command.CommandSender;

@CommandAlias("evchat")
public class ChatEventCommand extends BaseCommand {

    @Default @CatchUnknown
    public void defaultCommand(CommandSender s) {
        Main.getChatEvent().defaultPage(s);
    }

    @Subcommand("bolao")
    public void poolCommand(CommandSender s, String[] args) {
            if (s.hasPermission("simplexevents.chat.admin")) {
                Main.getChatEvent().onPool(s, args);
            } else {
                s.sendMessage("§cSem permissão.");
            }
    }

    @Subcommand("loteria")
    public void loterryCommand(CommandSender s, String[] args) {
            if (s.hasPermission("simplexevents.chat.admin")) {
                Main.getChatEvent().onLoterry(s, args);
            } else {
                s.sendMessage("§cSem permissão.");
            }
    }

    @Subcommand("speedchat")
    public void speedChatCommand(CommandSender s, String[] args) {
            if (s.hasPermission("simplexevents.chat.admin")) {
                Main.getChatEvent().onSpeedchat(s, args);
            } else {
                s.sendMessage("§cSem permissão.");
            }
    }

    @Subcommand("sorteio")
    public void sorteioCommand(CommandSender s) {
            if (s.hasPermission("simplexevents.chat.admin")) {
                Main.getChatEvent().onSorteioEvent(s);
            } else {
                s.sendMessage("§cSem permissão.");
            }
    }

    @Subcommand("random")
    public void randomCommand(CommandSender s) {
            if (s.hasPermission("simplexevents.chat.admin")) {
                Main.getChatEvent().onRandomEvent(s);
            } else {
                s.sendMessage("§cSem permissão.");
            }
    }
}
