package com.github.pk7r.simplexevents.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CatchUnknown;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import com.github.pk7r.simplexevents.Main;
import org.bukkit.command.CommandSender;

@CommandAlias("quiz|evquiz")
public class QuizEventCommand extends BaseCommand {

    @Default @CatchUnknown
    public void defaultPage(CommandSender s) {
        try {
            Main.getQuizEvent().onDefault(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("iniciar")
    public void startCommand(CommandSender s, String[] args) {
        try {
            if (s.hasPermission("simplexevents.quiz.admin")) {
                Main.getQuizEvent().onStart(s, args);
            } else {
                s.sendMessage("§cSem permissão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("setresposta")
    public void setAnswerCommand(CommandSender s, String[] args) {
        try {
            if (s.hasPermission("simplexevents.quiz.admin")) {
                Main.getQuizEvent().setAnswer(s, args);
            } else {
                s.sendMessage("§cSem permissão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("setpremio")
    public void awardPage(CommandSender s) {
        try {
            if (s.hasPermission("simplexevents.quiz.admin")) {
                Main.getQuizEvent().onAwardPage(s);
            } else {
                s.sendMessage("§cSem permissão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("setpremio item")
    public void itemAwardCommand(CommandSender s) {
        try {
            if (s.hasPermission("simplexevents.quiz.admin")) {
                Main.getQuizEvent().setItemAward(s);
            } else {
                s.sendMessage("§cSem permissão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("setpremio money")
    public void moneyAwardCommand(CommandSender s, int valor) {
        try {
            if (s.hasPermission("simplexevents.quiz.admin")) {
                Main.getQuizEvent().setMoneyAward(s, valor);
            } else {
                s.sendMessage("§cSem permissão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @CommandAlias("resposta")
    public void answerCommand(CommandSender s, String[] args) {
        try {
            Main.getQuizEvent().onAnswer(s, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
