package com.github.pk7r.simplexevents.events;

import com.github.pk7r.simplexevents.Main;
import com.github.pk7r.simplexevents.utils.EnqEventPattern;
import de.themoep.minedown.MineDown;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class EnqEvent {

    public void onDefault(CommandSender s) {
        Player p = (Player) s;
        if (p.hasPermission("simplexevents.enquete.admin")) {
            p.spigot().sendMessage(MineDown.parse(""));
            p.spigot().sendMessage(MineDown.parse("&eSimplexEvents - Enquete"));
            p.spigot().sendMessage(MineDown.parse(""));
            p.spigot().sendMessage(MineDown.parse("&b/enquete criar <enquete>"));
            p.spigot().sendMessage(MineDown.parse("&b/enquete encerrar"));
            p.spigot().sendMessage(MineDown.parse("&b/enquete sim"));
            p.spigot().sendMessage(MineDown.parse("&b/enquete nao"));
            p.spigot().sendMessage(MineDown.parse(""));
        } else {
            p.spigot().sendMessage(MineDown.parse(""));
            p.spigot().sendMessage(MineDown.parse("&eSimplexEvents - Enquete"));
            p.spigot().sendMessage(MineDown.parse(""));
            p.spigot().sendMessage(MineDown.parse("&b/enquete sim &f- Votar para SIM"));
            p.spigot().sendMessage(MineDown.parse("&b/enquete nao &f- Votar para NÃO"));
            p.spigot().sendMessage(MineDown.parse(""));
        }
    }

    public void onCreate(CommandSender s, String[] args) {
        Player p = (Player) s;
        if (args.length < 1) {
            p.spigot().sendMessage(MineDown.parse("&cUso correto: /enquete criar <enquete>"));
            return;
        }
        if (!Main.getEnqEventModel().isAberto()) {
            Main.getEnqEventModel().setAberto(true);
            StringBuilder builder = new StringBuilder();
            for (String arg : args) {
                builder.append(arg).append(" ");
            }
            Main.getEnqEventModel().setEnquete(builder.toString().trim());
            BukkitTask task = new BukkitRunnable() {

                @Override
                public void run() {
                    Bukkit.spigot().broadcast(MineDown.parse(""));
                    Bukkit.spigot().broadcast(MineDown.parse("&e&lNova Enquete!"));
                    Bukkit.spigot().broadcast(MineDown.parse(""));
                    Bukkit.spigot().broadcast(MineDown.parse("&b" + Main.getEnqEventModel().getEnquete()));
                    Bukkit.spigot().broadcast(MineDown.parse(""));
                    Bukkit.spigot().broadcast(MineDown.parse("&a[[Sim]](/enq sim) &c[[Não]](/enq nao)"));
                    Bukkit.broadcastMessage("");
                }
            }.runTaskTimer( Main.getMain(), 1L, 60 * 20);
            Main.getEnqEventModel().setTask(task);
        } else {
            p.spigot().sendMessage(MineDown.parse("&cJá existe nenhuma enquete acontecendo no momento."));
        }
    }

    public void onStop(CommandSender s) {
        Player p = (Player) s;
        if (Main.getEnqEventModel().isAberto()) {
            Bukkit.spigot().broadcast(MineDown.parse(""));
            Bukkit.spigot().broadcast(MineDown.parse("&e&lEnquete Finalizada!"));
            Bukkit.spigot().broadcast(MineDown.parse(""));
            Bukkit.spigot().broadcast(MineDown.parse("&bResultados:"));
            Bukkit.spigot().broadcast(MineDown.parse("&aSIM: &f" + Main.getEnqEventModel().getYes().size()
                    + " &cNÃO: &f" + Main.getEnqEventModel().getNo().size()));
            Bukkit.spigot().broadcast(MineDown.parse(""));
            EnqEventPattern.setDefault();
            Bukkit.getScheduler().cancelTasks(Main.getMain());
        } else {
            p.spigot().sendMessage(MineDown.parse("&cNão existe nenhuma enquete acontecendo no momento."));
        }
    }

    public void onYes(CommandSender s) {
        Player p = (Player) s;
        if (Main.getEnqEventModel().isAberto()) {
            if (!Main.getEnqEventModel().getYes().contains(p) && !Main.getEnqEventModel().getNo().contains(p)) {
                p.spigot().sendMessage(MineDown.parse("&eVocê votou &aSIM"));
                Main.getEnqEventModel().getYes().add(p);
            } else {
                p.spigot().sendMessage(MineDown.parse("&cVocê já votou nessa enquete."));
            }
        } else {
            p.spigot().sendMessage(MineDown.parse("&cNão existe nenhuma enquete acontecendo no momento."));
        }
    }

    public void onNo(CommandSender s) {
        Player p = (Player) s;
        if (Main.getEnqEventModel().isAberto()) {
            if (!Main.getEnqEventModel().getYes().contains(p) && !Main.getEnqEventModel().getNo().contains(p)) {
                p.spigot().sendMessage(MineDown.parse("&eVocê votou &cNÃO"));
                Main.getEnqEventModel().getNo().add(p);
            } else {
                p.spigot().sendMessage(MineDown.parse("&cVocê já votou nessa enquete."));
            }
        } else {
            p.spigot().sendMessage(MineDown.parse("&cNão existe nenhuma enquete acontecendo no momento."));
        }
    }

}