package com.github.pk7r.simplexevents.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CatchUnknown;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import com.github.pk7r.simplexevents.Main;
import org.bukkit.command.CommandSender;

@CommandAlias("enquete|enq")
public class EnqEventCommand extends BaseCommand {

    @Default @CatchUnknown
    public void defaultPage(CommandSender s) {
        try {
            Main.getEnqEvent().onDefault(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("criar")
    public void createCommand(CommandSender s, String[] args) {
        try {
            if (s.hasPermission("simplexevents.enquete.admin")) {
                Main.getEnqEvent().onCreate(s, args);
            } else {
                s.sendMessage("§cSem permissão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("encerrar")
    public void stopCommand(CommandSender s) {
        try {
            if (s.hasPermission("simplexevents.enquete.admin")) {
                Main.getEnqEvent().onStop(s);
            } else {
                s.sendMessage("§cSem permissão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("sim")
    public void yesCommand(CommandSender s) {
        try {
            Main.getEnqEvent().onYes(s);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("nao")
    public void noCommand(CommandSender s) {
        try {
            Main.getEnqEvent().onNo(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
