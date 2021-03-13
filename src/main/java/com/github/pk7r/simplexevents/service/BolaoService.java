package com.github.pk7r.simplexevents.service;

import com.github.pk7r.simplexevents.Main;
import com.github.pk7r.simplexevents.utils.ChatEventPattern;
import de.themoep.minedown.MineDown;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Random;

public class BolaoService {

    public static void onPool(CommandSender s) {
        Player p = (Player) s;
            if (!Main.getChatEventModel().isBolaoIniciado()) {
                p.spigot().sendMessage(MineDown.parse("&cNão tem nenhum" +
                        " bolão acontecendo no momento."));
                return;
            }
            if (Main.getChatEventModel().getBolaoParticipantes().contains(p)) {
                p.spigot().sendMessage(MineDown.parse("&cVocê já está participando do bolão."));
                return;
            }
            if ( Main.getEcon().getBalance(p) < Main.getChatEventModel().getBolaoApostaIndividual()) {
                p.spigot().sendMessage(MineDown.parse("&cVocê não tem" +
                        " dinheiro para apostar no bolão."));
                return;
            }
            Main.getChatEventModel().setBolaoAcumulado(Main.getChatEventModel()
                    .getBolaoAcumulado() + Main.getChatEventModel().getBolaoApostaIndividual());
            Main.getEcon().withdrawPlayer(p, Main.getChatEventModel().getBolaoApostaIndividual());
            Main.getChatEventModel().getBolaoParticipantes().add(p);
            p.spigot().sendMessage(MineDown.parse("&aVocê apostou no bolão com sucesso!"));
        }

    public static void run(int aposta) {
        Main.getChatEventModel().setBolaoApostaIndividual(aposta);
        if (Main.getChatEventModel().isBolaoIniciado()) {
            return;
        }
        Main.getChatEventModel().setBolaoIniciado(true);
        BukkitTask task = new BukkitRunnable() {
            int ann = 0;
            @Override
            public void run() {
                    Bukkit.spigot().broadcast(MineDown.parse(""));
                    Bukkit.spigot().broadcast(MineDown.parse("&e&lBolão aberto!"));
                    Bukkit.spigot().broadcast(MineDown.parse(""));
                    Bukkit.spigot().broadcast(MineDown.parse("&dAposta: &f" +
                            Main.getChatEventModel().getBolaoApostaIndividual()));
                    Bukkit.spigot().broadcast(MineDown.parse("&dAcumulado: &f" +
                            Main.getChatEventModel().getBolaoAcumulado()));
                    Bukkit.spigot().broadcast(MineDown.parse(""));
                    Bukkit.spigot().broadcast(MineDown.parse("&b[[Apostar]](/bolao)"));
                    Bukkit.spigot().broadcast(MineDown.parse(""));
                ann++;
                if (ann == 4) {
                    Player ganhador = Main.getChatEventModel().getBolaoParticipantes()
                            .get(new Random().nextInt(Main.getChatEventModel().getBolaoParticipantes().size()));
                    Main.getEcon().depositPlayer(ganhador, Main.getChatEventModel().getBolaoAcumulado());
                    Bukkit.spigot().broadcast(MineDown.parse(""));
                    Bukkit.spigot().broadcast(MineDown.parse("&e&lBolão finalizado!"));
                    Bukkit.spigot().broadcast(MineDown.parse(""));
                    Bukkit.spigot().broadcast(MineDown.parse("&dVencedor: &f" + ganhador.getName()));
                    Bukkit.spigot().broadcast(MineDown.parse("&dPrêmio: &f" +
                            Main.getChatEventModel().getBolaoAcumulado()));
                    Bukkit.spigot().broadcast(MineDown.parse(""));
                    ChatEventPattern.setBolaoDefault();
                    cancel();
                }
            }
        }.runTaskTimer(Main.getMain(), 0L, 30 * 20);
    }
}

