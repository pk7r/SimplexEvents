package com.github.pk7r.simplexevents.commands.chat;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CatchUnknown;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import com.github.pk7r.simplexevents.Main;
import de.themoep.minedown.MineDown;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("evchat")
public class ChatEventCommand extends BaseCommand {

    @Default @CatchUnknown
    public void defaultCommand(CommandSender s) {
        Main.getChatEvent().defaultPage(s);
    }

    @Subcommand("bolao")
    public void poolCommand(CommandSender s, String[] args) {
        Player p = (Player) s;
        if (p.hasPermission("simplexevents.chat.admin")) {
            Main.getChatEvent().onPool(s, args);
        } else {
            p.spigot().sendMessage(MineDown.parse("&cSem permissão."));
        }
    }

    @Subcommand("loteria")
    public void loterryCommand(CommandSender s, String[] args) {
        Player p = (Player) s;
        if (p.hasPermission("simplexevents.chat.admin")) {
            Main.getChatEvent().onLoterry(s, args);
        } else {
            p.spigot().sendMessage(MineDown.parse("&cSem permissão."));
        }
    }

    @Subcommand("speedchat")
    public void speedChatCommand(CommandSender s, String[] args) {
        Player p = (Player) s;
        if (p.hasPermission("simplexevents.chat.admin")) {
            Main.getChatEvent().onSpeedchat(s, args);
        } else {
            p.spigot().sendMessage(MineDown.parse("&cSem permissão."));
        }
    }

    @Subcommand("sorteio")
    public void giveAwayCommand(CommandSender s) {
        Player p = (Player) s;
        if (p.hasPermission("simplexevents.chat.admin")) {
            Main.getChatEvent().onSorteioEvent(s);
        } else {
            p.spigot().sendMessage(MineDown.parse("&cSem permissão."));
        }
    }

    @Subcommand("random")
    public void randomCommand(CommandSender s) {
        Player p = (Player) s;
        if (p.hasPermission("simplexevents.chat.admin")) {
            Main.getChatEvent().onRandomEvent(s);
        } else {
            p.spigot().sendMessage(MineDown.parse("&cSem permissão."));
        }
    }
}
