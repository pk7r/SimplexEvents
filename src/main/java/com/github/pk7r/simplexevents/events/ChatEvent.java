package com.github.pk7r.simplexevents.events;

import com.github.pk7r.simplexevents.Main;
import com.github.pk7r.simplexevents.service.BolaoService;
import com.github.pk7r.simplexevents.service.LoteriaService;
import com.github.pk7r.simplexevents.service.SorteioService;
import com.github.pk7r.simplexevents.service.SpeedchatService;
import com.github.pk7r.simplexevents.utils.RandomChatEvent;
import de.themoep.minedown.MineDown;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatEvent {


    public void defaultPage(CommandSender s) {
        Player p = (Player) s;
        if (p.hasPermission("simplexevents.chat.staff")) {
            p.spigot().sendMessage(MineDown.parse(""));
            p.spigot().sendMessage(MineDown.parse("&eSimplexEvents - Chat"));
            p.spigot().sendMessage(MineDown.parse(""));
            p.spigot().sendMessage(MineDown.parse("&b/evchat random"));
            p.spigot().sendMessage(MineDown.parse("&b/evchat sorteio"));
            p.spigot().sendMessage(MineDown.parse("&b/evchat bolao <aposta>"));
            p.spigot().sendMessage(MineDown.parse("&b/evchat loteria <premio>"));
            p.spigot().sendMessage(MineDown.parse("&b/evchat speedchat <premio>"));
            p.spigot().sendMessage(MineDown.parse(""));
        } else {
            p.spigot().sendMessage(MineDown.parse(""));
            p.spigot().sendMessage(MineDown.parse("&eSimplexEvents - Chat"));
            p.spigot().sendMessage(MineDown.parse(""));
            p.spigot().sendMessage(MineDown.parse("&b/bolao"));
            p.spigot().sendMessage(MineDown.parse("&b/loteria <número>"));
            p.spigot().sendMessage(MineDown.parse(""));
        }
    }

    public void onPool(CommandSender s, String[] args) {
        Player p = (Player) s;
        if (args.length == 1) {
            try {
                BolaoService.iniciar(Integer.parseInt(args[0]));
            } catch (NumberFormatException e) {
                p.spigot().sendMessage(MineDown.parse("&cUso correto: /evchat bolao <aposta>"));
                p.spigot().sendMessage(MineDown.parse("&cExemplo: /evchat bolao 1000"));
            }
        } else {
            p.spigot().sendMessage(MineDown.parse("&cUso correto: /evchat bolao <aposta>"));
            p.spigot().sendMessage(MineDown.parse("&cExemplo: /evchat bolao 1000"));
        }
    }

    public void onLoterry(CommandSender s, String[] args) {
        Player p = (Player) s;
        if (args.length == 1) {
            try {
                LoteriaService.iniciar(Integer.parseInt(args[0]));
                p.spigot().sendMessage(MineDown.parse("&aNúmero correto: &f" + Main.getChatEventModel().getLoteriaNumeroCorreto()));
            } catch (NumberFormatException e) {
                p.spigot().sendMessage(MineDown.parse("&cUso correto: /evchat loteria <premio>"));
                p.spigot().sendMessage(MineDown.parse("&cExemplo: /evchat loteria 1000"));
            }
        } else {
            p.spigot().sendMessage(MineDown.parse("&cUso correto: /evchat loteria <premio>"));
            p.spigot().sendMessage(MineDown.parse("&cExemplo: /evchat loteria 1000"));
        }
    }

    public void onSpeedchat(CommandSender s, String[] args) {
        Player p = (Player) s;
        if (args.length == 1) {
            try {
                SpeedchatService.iniciar(Integer.parseInt(args[0]));
            } catch (NumberFormatException e) {
                p.spigot().sendMessage(MineDown.parse("&cUso correto: /evchat speedchat <premio>"));
                p.spigot().sendMessage(MineDown.parse("&cExemplo: /evchat speedchat 1000"));
            }
        } else {
            p.spigot().sendMessage(MineDown.parse("&cUso correto: /evchat speedchat <premio>"));
            p.spigot().sendMessage(MineDown.parse("&cExemplo: /evchat speedchat 1000"));

        }
    }

    public void onRandomEvent(CommandSender s) {
        RandomChatEvent.randomEvent();
    }

    public void onSorteioEvent(CommandSender s) {
        SorteioService.iniciar();
    }
}
