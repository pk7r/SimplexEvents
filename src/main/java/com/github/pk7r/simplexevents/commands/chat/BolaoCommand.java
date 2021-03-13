package com.github.pk7r.simplexevents.commands.chat;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CatchUnknown;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import com.github.pk7r.simplexevents.service.BolaoService;
import org.bukkit.command.CommandSender;

@CommandAlias("bolao")
public class BolaoCommand extends BaseCommand {

    @Default @CatchUnknown
    public void betOnPool(CommandSender s) {
        try {
            BolaoService.onPool(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
