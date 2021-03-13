package com.github.pk7r.simplexevents.events;

import com.github.pk7r.simplexevents.Main;
import com.github.pk7r.simplexevents.utils.QuizEventPattern;
import de.themoep.minedown.MineDown;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class QuizEvent {

    public void onDefault(CommandSender s) {
        Player p = (Player) s;
        if (p.hasPermission("simplexevents.quiz.admin")) {
            p.spigot().sendMessage(MineDown.parse(""));
            p.spigot().sendMessage(MineDown.parse("&eSimplexEvents - Quiz"));
            p.spigot().sendMessage(MineDown.parse(""));
            p.spigot().sendMessage(MineDown.parse("&b/evquiz setresposta <resposta>"));
            p.spigot().sendMessage(MineDown.parse("&b/evquiz setpremio money <valor>"));
            p.spigot().sendMessage(MineDown.parse("&b/evquiz setpremio item"));
            p.spigot().sendMessage(MineDown.parse("&b/evquiz iniciar <pergunta>"));
            p.spigot().sendMessage(MineDown.parse(""));
        } else {
            p.spigot().sendMessage(MineDown.parse(""));
            p.spigot().sendMessage(MineDown.parse("&eSimplexEvents - Quiz"));
            p.spigot().sendMessage(MineDown.parse(""));
            p.spigot().sendMessage(MineDown.parse("&b/resposta <resposta>"));
            p.spigot().sendMessage(MineDown.parse(""));
        }
    }

    public void onStart(CommandSender s, String[] args) {
        Player p = (Player) s;
        if (args.length < 1) {
            p.spigot().sendMessage(MineDown.parse("&cUso correto: /quiz iniciar <pergunta>"));
            return;
        }
        if (Main.getQuizEventModel().getResposta().equalsIgnoreCase("")) {
            p.spigot().sendMessage(MineDown.parse("&cDefina uma resposta antes de iniciar!"));
            return;
        }
        if (!Main.getQuizEventModel().getResposta().equalsIgnoreCase("")) {
            Main.getQuizEventModel().setAberto(true);
            StringBuilder builder = new StringBuilder();
            for (String arg : args) {
                builder.append(arg).append(" ");
            }
            Main.getQuizEventModel().setPergunta(builder.toString().trim());
            if (Main.getQuizEventModel().isAberto()) {
                BukkitTask task = new BukkitRunnable() {
                    @Override
                    public void run() {
                        Bukkit.spigot().broadcast(MineDown.parse(""));
                        Bukkit.spigot().broadcast(MineDown.parse("&e&lNovo Quiz!"));
                        Bukkit.spigot().broadcast(MineDown.parse(""));
                        Bukkit.spigot().broadcast(MineDown.parse("&d" + Main.getQuizEventModel().getPergunta()));
                        Bukkit.spigot().broadcast(MineDown.parse(""));
                        if (Main.getQuizEventModel().getPremioMoney() != 0 && !Main.getQuizEventModel().getPremioItem().getType().equals(Material.AIR)) {
                            Bukkit.spigot().broadcast(MineDown.parse("&bPrêmios: &f" + Main.getQuizEventModel().getPremioMoney() + " money &be &f "
                                    + Main.getQuizEventModel().getPremioItem().getItemMeta().getDisplayName()));
                        }
                        if (Main.getQuizEventModel().getPremioMoney() != 0 && Main.getQuizEventModel().getPremioItem().getType().equals(Material.AIR)) {
                            Bukkit.spigot().broadcast(MineDown.parse("&bPrêmio: &f" + Main.getQuizEventModel().getPremioMoney() + " money"));
                        }
                        if (Main.getQuizEventModel().getPremioMoney() == 0 && !Main.getQuizEventModel().getPremioItem().getType().equals(Material.AIR)) {
                            Bukkit.spigot().broadcast(MineDown.parse("&bPrêmio: &f" + Main.getQuizEventModel().getPremioItem().getItemMeta().getLocalizedName()));
                        }
                        if (Main.getQuizEventModel().getPremioMoney() == 0 && Main.getQuizEventModel().getPremioItem().getType().equals(Material.AIR)) {
                            Bukkit.spigot().broadcast(MineDown.parse("&bPrêmio: &fNenhum!"));
                        }
                        Bukkit.broadcastMessage("");
                        Bukkit.spigot().broadcast(MineDown.parse("&b[[Responder]](suggest_command=/resposta )"));
                        Bukkit.broadcastMessage("");
                    }
                }.runTaskTimer(Main.getMain(), 1L, 60 * 20);
                Main.getQuizEventModel().setTask(task);
            } else {
                p.spigot().sendMessage(MineDown.parse("&cJá existe um quiz acontecendo no momento."));
            }
        }
    }

    public void setAnswer(CommandSender s, String[] args) {
        Player p = (Player) s;
        if (args.length < 1) {
            p.spigot().sendMessage(MineDown.parse("&cUso correto: /quiz setresposta <resposta>"));
            return;
        }
        if (!Main.getQuizEventModel().isAberto()) {
            StringBuilder builder = new StringBuilder();
            for (String arg : args) {
                builder.append(arg).append(" ");
            }
            Main.getQuizEventModel().setResposta(builder.toString().trim());
                p.spigot().sendMessage(MineDown.parse("&aResposta definida com sucesso!"));
                p.spigot().sendMessage(MineDown.parse("&eResposta Atual: &f" + Main.getQuizEventModel().getResposta()));
        } else {
            p.spigot().sendMessage(MineDown.parse("&cJá existe um quiz acontecendo no momento."));
        }
    }

    public void onAwardPage(CommandSender s) {
        Player p = (Player) s;
        p.spigot().sendMessage(MineDown.parse(""));
        p.spigot().sendMessage(MineDown.parse("&b/evquiz setpremio money <valor>"));
        p.spigot().sendMessage(MineDown.parse("&b/evquiz setpremio item"));
        p.spigot().sendMessage(MineDown.parse(""));
        p.spigot().sendMessage(MineDown.parse("&cSegure o item na mão para"));
        p.spigot().sendMessage(MineDown.parse("&csetar como prêmio."));
        p.spigot().sendMessage(MineDown.parse(""));
    }

    public void setMoneyAward(CommandSender s, int valor) {
        Player p = (Player) s;
        if (Main.getQuizEventModel().isAberto()) {
            p.spigot().sendMessage(MineDown.parse("&cJá existe um quiz acontecendo no momento!"));
            return;
        }
        try {
            if (Main.getQuizEventModel().getPremioMoney() < 0.0) {
                p.sendMessage("§cO valor deve ser maior que 0!");
                return;
            }
            Main.getQuizEventModel().setPremioMoney(valor);
        } catch (NumberFormatException e) {
            p.spigot().sendMessage(MineDown.parse("&cUso correto: /quiz setpremio <valor>"));
            return;
        }
            p.spigot().sendMessage(MineDown.parse("&aPremio em dinheiro do quiz setado" +
                    " para &f" + Main.getQuizEventModel().getPremioMoney() + " money &acom sucesso!"));
    }

    public void setItemAward(CommandSender s) {
        Player p = (Player) s;
        if (Main.getQuizEventModel().isAberto()) {
            p.spigot().sendMessage(MineDown.parse("&cJá existe um quiz acontecendo no momento!"));
            return;
        }
        try {
            if (Main.getQuizEventModel().getPremioItem().getType().equals(Material.AIR)) {
                p.sendMessage("§cSegure um item na mão para adicionar como prêmio!");
                return;
            }
            Main.getQuizEventModel().setPremioItem(p.getInventory().getItemInMainHand());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        p.spigot().sendMessage(MineDown.parse("&aPremio em item do quiz setado" +
                " para &f" + Main.getQuizEventModel().getPremioItem().getItemMeta().getDisplayName() + " &acom sucesso!"));
    }

    public void onAnswer(CommandSender s, String[] args) {
        Player p = (Player) s;
        if (args.length < 1) {
            p.spigot().sendMessage(MineDown.parse("&cUso correto: /resposta <resposta>"));
            return;
        }
        if (!Main.getQuizEventModel().getPergunta().equalsIgnoreCase("")) {
            final StringBuilder sb = new StringBuilder("");
            sb.append(args[0]);
            for (int i = 1; i < args.length; ++i) {
                sb.append(" ");
                sb.append(args[i]);
            }
            if (Main.getQuizEventModel().getResposta().equalsIgnoreCase(sb.toString())) {
                Main.getQuizEventModel().setAberto(false);
                Bukkit.spigot().broadcast(MineDown.parse(""));
                Bukkit.spigot().broadcast(MineDown.parse("&e&lQuiz Finalizado!"));
                Bukkit.spigot().broadcast(MineDown.parse(""));
                Bukkit.spigot().broadcast(MineDown.parse("&dPergunta: &f" + Main.getQuizEventModel().getPergunta()));
                Bukkit.spigot().broadcast(MineDown.parse(""));
                Bukkit.spigot().broadcast(MineDown.parse("&dResposta: &f" + Main.getQuizEventModel().getResposta()));
                Bukkit.spigot().broadcast(MineDown.parse(""));
                Bukkit.spigot().broadcast(MineDown.parse("&bVencedor: &f" + p.getName()));
                if (Main.getQuizEventModel().getPremioMoney() != 0 && !Main.getQuizEventModel().getPremioItem().getType().equals(Material.AIR)) {
                    Bukkit.spigot().broadcast(MineDown.parse("&bPrêmios: &f" + Main.getQuizEventModel().getPremioMoney() + " money &be &f "
                            + Main.getQuizEventModel().getPremioItem().getItemMeta().getDisplayName()));
                }
                if (Main.getQuizEventModel().getPremioMoney() != 0 && Main.getQuizEventModel().getPremioItem().getType().equals(Material.AIR)) {
                    Bukkit.spigot().broadcast(MineDown.parse("&bPrêmio: &f" + Main.getQuizEventModel().getPremioMoney() + " money"));
                }
                if (Main.getQuizEventModel().getPremioMoney() == 0 && !Main.getQuizEventModel().getPremioItem().getType().equals(Material.AIR)) {
                    Bukkit.spigot().broadcast(MineDown.parse("&bPrêmio: &f" + Main.getQuizEventModel().getPremioItem().getItemMeta().getLocalizedName()));
                }
                if (Main.getQuizEventModel().getPremioMoney() == 0 && Main.getQuizEventModel().getPremioItem().getType().equals(Material.AIR)) {
                    Bukkit.spigot().broadcast(MineDown.parse("&bPrêmio: &fNenhum!"));
                }
                if (!Main.getQuizEventModel().getPremioItem().getType().equals(Material.AIR)) {
                    p.getInventory().addItem(Main.getQuizEventModel().getPremioItem());
                }
                if (Main.getQuizEventModel().getPremioMoney() != 0) {
                    Main.getEcon().depositPlayer(p, Main.getQuizEventModel().getPremioMoney());
                }
                Bukkit.spigot().broadcast(MineDown.parse(""));
                QuizEventPattern.setDefault();
                Bukkit.getScheduler().cancelTasks(Main.getMain());
            } else {
                p.spigot().sendMessage(MineDown.parse("&cResposta inválida!"));
            }
        } else {
            p.spigot().sendMessage(MineDown.parse("&cNão existe um quiz acontecendo no momento."));
        }
    }

}