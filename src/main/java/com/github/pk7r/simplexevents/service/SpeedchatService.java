package com.github.pk7r.simplexevents.service;

import com.github.pk7r.simplexevents.Main;
import de.themoep.minedown.MineDown;
import org.bukkit.Bukkit;

import java.util.Random;

public class SpeedchatService {

    public static void run(int premio) {
        Main.getChatEventModel().setSpeedChatPremio(premio);
        if (Main.getChatEventModel().isSpeedChatIniciado()) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        char[] letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        for (int i = 0; i != 14; i++) {
            int ch = new Random().nextInt(letras.length);
            sb.append(letras[ch]);
        }
        Main.getChatEventModel().setSpeedChatIniciado(true);
        Main.getChatEventModel().setSpeedChatChars(sb.toString());
        Bukkit.spigot().broadcast(MineDown.parse(""));
        Bukkit.spigot().broadcast(MineDown.parse("&e&lSpeedchat aberto!"));
        Bukkit.spigot().broadcast(MineDown.parse(""));
        Bukkit.spigot().broadcast(MineDown.parse("&dDigite: &f" + Main.getChatEventModel().getSpeedChatChars()));
        Bukkit.spigot().broadcast(MineDown.parse("&dPrêmio: &f" + Main.getChatEventModel().getSpeedChatPremio()));
        Bukkit.spigot().broadcast(MineDown.parse(""));
    }
}