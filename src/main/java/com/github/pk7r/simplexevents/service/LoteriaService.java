package com.github.pk7r.simplexevents.service;

import com.github.pk7r.simplexevents.Main;
import com.github.pk7r.simplexevents.model.ChatEventModel;
import com.github.pk7r.simplexevents.utils.ChatEventPattern;
import de.themoep.minedown.MineDown;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Random;

public class LoteriaService {

    private static final ChatEventModel ev = Main.getChatEventModel();
    
    public static void onLoterry(CommandSender s, String[] args) {
        Player p = (Player) s;
            if (!ev.isLoteriaIniciado()) {
                p.spigot().sendMessage(
                        MineDown.parse("&cNão tem nenhuma loteria acontecendo no momento."));
                return;
            }
            if (args.length != 1) {
                p.spigot().sendMessage(MineDown.parse("&cUso correto: /loteria <número>"));
                return;
            }
            try {
                int numero = Integer.parseInt(args[0]);
                if (numero != ev.getLoteriaNumeroCorreto()) {
                    p.spigot().sendMessage(MineDown.parse("&cNúmero incorreto!"));
                    return;
                }
                Main.getEcon().depositPlayer(p, ev.getLoteriaPremio());
                ev.setLoteriaIniciado(false);
                Bukkit.spigot().broadcast(MineDown.parse(""));
                Bukkit.spigot().broadcast(MineDown.parse("&e&lLoteria finalizada!"));
                Bukkit.spigot().broadcast(MineDown.parse(""));
                Bukkit.spigot().broadcast(MineDown.parse("&dGanhador: &f" + p.getName()));
                Bukkit.spigot().broadcast(MineDown.parse("&dNúmero correto: &f"
                        + ev.getLoteriaNumeroCorreto()));
                Bukkit.spigot().broadcast(MineDown.parse("&dPrêmio: &f" + ev.getLoteriaPremio()));
                Bukkit.spigot().broadcast(MineDown.parse(""));
            } catch (NumberFormatException e) {
                p.spigot().sendMessage(MineDown.parse("&cDigite um número inteiro válido!"));
            }
        }

    public static void run(int bet) {
        if (ev.isLoteriaIniciado()) {
            return;
        }
        ev.setLoteriaPremio(bet);
        ev.setLoteriaIniciado(true);
        int nmaximo = 50;
        ev.setLoteriaNumeroCorreto(new Random().nextInt(nmaximo));

        BukkitTask task = new BukkitRunnable() {
            int ann = 0;

            @Override
            public void run() {
                Bukkit.spigot().broadcast(MineDown.parse(""));
                Bukkit.spigot().broadcast(MineDown.parse("&e&lLoteria aberta!"));
                Bukkit.spigot().broadcast(MineDown.parse(""));
                Bukkit.spigot().broadcast(MineDown.parse("&dNúmero máximo: &f" + nmaximo));
                Bukkit.spigot().broadcast(MineDown.parse("&dPrêmio: &f" + ev.getLoteriaPremio()));
                Bukkit.spigot().broadcast(MineDown.parse(""));
                Bukkit.spigot().broadcast(MineDown.parse("&b[[Participar]](suggest_command=/loteria )"));
                Bukkit.spigot().broadcast(MineDown.parse(""));
                ann++;
                if (ann == 4) {
                    Bukkit.spigot().broadcast(MineDown.parse(""));
                    Bukkit.spigot().broadcast(MineDown.parse("&e&lLoteria finalizada!"));
                    Bukkit.spigot().broadcast(MineDown.parse(""));
                    Bukkit.spigot().broadcast(MineDown.parse("&dVencedor: &fNinguém!"));
                    Bukkit.spigot().broadcast(MineDown.parse("&dNúmero correto: &f"
                            + ev.getLoteriaNumeroCorreto()));
                    Bukkit.spigot().broadcast(MineDown.parse("&dPrêmio: &f" + ev.getLoteriaPremio()));
                    Bukkit.spigot().broadcast(MineDown.parse(""));
                    ChatEventPattern.setLoteriaDefault();
                    cancel();
                }
            }
        }.runTaskTimer(Main.getMain(), 0L, 30 * 20);
    }
}
