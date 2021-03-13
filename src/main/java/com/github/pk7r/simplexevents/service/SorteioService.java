package com.github.pk7r.simplexevents.service;

import com.github.pk7r.simplexevents.Main;
import com.github.pk7r.simplexevents.utils.ChatEventPattern;
import de.themoep.minedown.MineDown;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;

public class SorteioService {

    public static void run() {
        if (Main.getChatEventModel().isSorteioIniciado()) {
            return;
        }
        Main.getChatEventModel().setSorteioIniciado(true);
        ArrayList<Player> online = new ArrayList<>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            online.add(p);
            p.spigot().sendMessage(MineDown.parse(""));
            p.spigot().sendMessage(MineDown.parse("&e&lSorteio iniciado!"));
            p.spigot().sendMessage(MineDown.parse(""));
            p.spigot().sendMessage(MineDown.parse("&bSorteando jogador..."));
            p.spigot().sendMessage(MineDown.parse("&f&kasdqawe123"));
            p.spigot().sendMessage(MineDown.parse(""));
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMain(), new Runnable() {
            @Override
            public void run() {
                Player ganhador = Bukkit.getPlayer(online.get(new Random().nextInt(online.size())).getName());
                Bukkit.spigot().broadcast(MineDown.parse(""));
                Bukkit.spigot().broadcast(MineDown.parse("&e&lSorteio finalizado!"));
                Bukkit.spigot().broadcast(MineDown.parse(""));
                Bukkit.spigot().broadcast(MineDown.parse("&bGanhador: &f" + ganhador.getName()));
                Bukkit.spigot().broadcast(MineDown.parse(""));
                online.clear();
                ChatEventPattern.setSorteioDefault();
            }
        }, 20 * 20L);
    }
}