package com.github.pk7r.simplexevents.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import de.themoep.minedown.MineDown;
import org.bukkit.entity.Player;

public class SECommand extends BaseCommand {

    @CommandAlias("simplexevents|se")
    public void simplexEventsCommand(Player p) {

        p.spigot().sendMessage(MineDown.parse(""));
        p.spigot().sendMessage(MineDown.parse("&e&lSimplexEvents - Ajuda"));
        p.spigot().sendMessage(MineDown.parse(""));
        p.spigot().sendMessage(MineDown.parse("&b/evento  &f- Comandos para eventos comuns"));
        p.spigot().sendMessage(MineDown.parse("&b/evchat  &f- Comandos para eventos de chat"));
        p.spigot().sendMessage(MineDown.parse("&b/evquiz  &f- Comandos para quiz"));
        p.spigot().sendMessage(MineDown.parse("&b/enquete &f- Comandos para enquete"));
        p.spigot().sendMessage(MineDown.parse(""));

    }

}
