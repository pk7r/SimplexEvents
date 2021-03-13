package com.github.pk7r.simplexevents.commands.chat;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import com.github.pk7r.simplexevents.service.LoteriaService;
import org.bukkit.command.CommandSender;

@CommandAlias("loteria")
public class LoteriaCommand extends BaseCommand {

    @Default
    public void loterryCommand(CommandSender s, String[] args) {
        try {
            LoteriaService.onLoterry(s, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
