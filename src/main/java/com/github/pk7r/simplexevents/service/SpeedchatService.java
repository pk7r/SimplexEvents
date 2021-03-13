package com.github.pk7r.simplexevents.service;

import com.github.pk7r.simplexevents.Main;
import com.github.pk7r.simplexevents.model.ChatEventModel;
import de.themoep.minedown.MineDown;
import org.bukkit.Bukkit;

import java.util.Random;

public class SpeedchatService {

    private static final ChatEventModel ev = Main.getChatEventModel();

    public static void run(int premio) {
        ev.setSpeedChatPremio(premio);
        if (ev.isSpeedChatIniciado()) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        char[] letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        for (int i = 0; i != 14; i++) {
            int ch = new Random().nextInt(letras.length);
            sb.append(letras[ch]);
        }
        ev.setSpeedChatIniciado(true);
        ev.setSpeedChatChars(sb.toString());
        Bukkit.spigot().broadcast(MineDown.parse(""));
        Bukkit.spigot().broadcast(MineDown.parse("&e&lSpeedchat aberto!"));
        Bukkit.spigot().broadcast(MineDown.parse(""));
        Bukkit.spigot().broadcast(MineDown.parse("&dDigite: &f" + ev.getSpeedChatChars()));
        Bukkit.spigot().broadcast(MineDown.parse("&dPrêmio: &f" + ev.getSpeedChatPremio()));
        Bukkit.spigot().broadcast(MineDown.parse(""));
    }
}