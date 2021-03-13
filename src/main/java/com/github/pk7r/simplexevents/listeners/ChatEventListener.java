package com.github.pk7r.simplexevents.listeners;

import com.github.pk7r.simplexevents.Main;
import com.github.pk7r.simplexevents.utils.ChatEventPattern;
import de.themoep.minedown.MineDown;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEventListener implements Listener {

    @EventHandler
    private static void onSpeak(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (Main.getChatEventModel().isSpeedChatIniciado()) {
            if (e.getMessage().equals(Main.getChatEventModel().getSpeedChatChars())) {
                Bukkit.spigot().broadcast(MineDown.parse(""));
                Bukkit.spigot().broadcast(MineDown.parse("&e&lSpeedchat finalizado!"));
                Bukkit.spigot().broadcast(MineDown.parse(""));
                Bukkit.spigot().broadcast(MineDown.parse("&dVencedor: &f" + p.getName()));
                Bukkit.spigot().broadcast(MineDown.parse("&dPrêmio: &f" + Main.getChatEventModel().getSpeedChatPremio()));
                Bukkit.spigot().broadcast(MineDown.parse(""));
                Main.getEcon().depositPlayer(p, Main.getChatEventModel().getSpeedChatPremio());
                ChatEventPattern.setSpeedChatDefault();
                e.setCancelled(true);
            }
        }
    }
}