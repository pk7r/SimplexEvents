package com.github.pk7r.simplexevents.events;

import com.github.pk7r.simplexevents.Main;
import com.github.pk7r.simplexevents.model.GenericEventModel;
import com.github.pk7r.simplexevents.utils.GenericEventPattern;
import de.themoep.minedown.MineDown;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class GenericEvent {
    
    private final Main plugin = Main.getMain();
    private final GenericEventModel ev = Main.getGenericEventModel();

    public void onCreateEvent(CommandSender s, String[] args) {
        Player p = (Player) s;
        if (ev.isCriado()) {
            p.spigot().sendMessage(MineDown.parse("&cJá existe um evento criado no momento."));
            return;
        }
        if (args.length < 1) {
            p.spigot().sendMessage(MineDown.parse("&cUso correto: /evento criar <nome>"));
            return;
        }
        StringBuilder builder = new StringBuilder();
        for (String arg : args) {
            builder.append(arg).append(" ");
        }
        ev.setEventName(builder.toString().trim());
        ev.setCriado(true);
        p.spigot().sendMessage(MineDown.parse(""));
        p.spigot().sendMessage(MineDown.parse("&eO Evento é com itens próprios?" +
                " &c[[Definir]](/evento toggle inv) &7(Padrão: NÃO)"));
        p.spigot().sendMessage(MineDown.parse("&eEste é um evento de times?" +
                " &c[[Definir]](/evento toggle team) &7(Padrão: NÃO)"));
        p.spigot().sendMessage(MineDown.parse("&eO PvP é habilitado no time?" +
                " &c[[Definir]](/evento toggle teampvp) &7(Padrão: NÃO)"));
        p.spigot().sendMessage(MineDown.parse("&eO PvP é habilitado no evento?" +
                " &c[[Definir]](/evento toggle pvp) &7(Padrão: NÃO)"));
        p.spigot().sendMessage(MineDown.parse("&eO Fly é habilitado no evento? " +
                "&c[[Definir]](/evento toggle fly) &7(Padrão: NÃO)"));
        p.spigot().sendMessage(MineDown.parse(""));
        p.spigot().sendMessage(MineDown.parse("&bDefinir: &d[[Prêmio]](suggest_command=/evento premio)" +
                " [[Entrada]](/evento set entrada) [[Saída]](/evento set saida)" +
                " [[Camarote]](/evento set camarote)"));
        p.spigot().sendMessage(MineDown.parse(""));
        p.spigot().sendMessage(MineDown.parse("&a[[Iniciar Evento]](/evento iniciar) " +
                "&6[[Ajuda]](/evento)"));
    }

    public void onStartEvent(CommandSender s) {
        Player p = (Player) s;

        if (ev.isIniciado()) {
            p.spigot().sendMessage(MineDown.parse("&cJá existe um evento iniciado no momento."));
            return;
        }
        if (ev.isCriado()) {
            if (ev.getCamarote() != null && ev.getEntrada() != null && ev.getSaida() != null) {
                ev.setIniciado(true);
                ev.setTrancado(false);
                p.spigot().sendMessage(MineDown.parse("&aEvento &f"
                        + ev.getEventName() + " &ainiciado com sucesso!"));
                BukkitTask annouce = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
                    Bukkit.spigot().broadcast(MineDown.parse(""));
                    Bukkit.spigot().broadcast(MineDown.parse("&e&lEvento "
                            + ev.getEventName() + " Aberto!"));
                    Bukkit.spigot().broadcast(MineDown.parse(""));
                    if (ev.getPremioItem().getType().equals(Material.AIR)
                            && ev.getPremioMoney() == 0) {
                        Bukkit.spigot().broadcast(MineDown.parse("&bPrêmio: &fNenhum!"));
                    }
                    if (!ev.getPremioItem().getType().equals(Material.AIR) && ev.getPremioMoney() == 0) {
                        Bukkit.spigot().broadcast(MineDown.parse("&bPrêmio: &f"
                                + ev.getPremioItem().getItemMeta().getDisplayName()));
                    }
                    if (ev.getPremioItem().getType().equals(Material.AIR) && ev.getPremioMoney() != 0) {
                        Bukkit.spigot().broadcast(MineDown.parse("&bPrêmio: &f"
                                + ev.getPremioMoney() + " money"));
                    }
                    if (!ev.getPremioItem().getType().equals(Material.AIR) && ev.getPremioMoney() != 0) {
                        Bukkit.spigot().broadcast(MineDown.parse("&bPrêmios: &f"
                                + ev.getPremioMoney() + " money + " +
                                ev.getPremioItem().getItemMeta().getDisplayName()));
                    }
                    Bukkit.spigot().broadcast(MineDown.parse(""));
                    Bukkit.spigot().broadcast(
                            MineDown.parse("&b[[Participar]](/evento entrar) " +
                                    "&b[[Assistir]](/evento camarote) &b[[Info]](/evento info)"));

                }, 1L, 30 * 20);
            } else {
                p.spigot().sendMessage(MineDown.parse("&cPor favor, defina a entrada, saída e o camarote."));
            }
        } else {
            p.spigot().sendMessage(MineDown.parse("&cNão existe nenhum evento criado no momento."));
        }
    }

    public void onLockEvent(CommandSender s) {
        Player p = (Player) s;
        if (ev.isTrancado()) {
            p.spigot().sendMessage(MineDown.parse("&cJá existe nenhum evento trancado no momento."));
            return;
        }
        if (ev.isIniciado()) {
            if (ev.isTeam()) {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (ev.getParticipantes().contains(all)) {
                        ev.getVerde().addAll(ev.getParticipantes().subList(0,
                                (int) (ev.getParticipantes().size() * 0.5)));
                        if (!ev.getVerde().contains(all) && ev.getParticipantes().contains(all))
                            ev.getAmarelo().add(all);
                        all.spigot().sendMessage(MineDown.parse(""));
                        all.spigot().sendMessage(MineDown.parse("&6Os jogadores do evento"));
                        all.spigot().sendMessage(MineDown.parse("&6foram dividos em dois"));
                        all.spigot().sendMessage(MineDown.parse("&6times: &aVerde &6e&e Amarelo"));
                        all.spigot().sendMessage(MineDown.parse(""));
                        if (ev.getVerde().contains(all)) {
                            all.spigot().sendMessage(MineDown.parse("&6Você está no time &a&lVERDE"));
                        }
                        if (ev.getAmarelo().contains(all)) {
                            all.spigot().sendMessage(MineDown.parse("&6Você está no time &e&lAmarelo"));
                        }
                        all.spigot().sendMessage(MineDown.parse("&b[[Informações do" +
                                " Evento]](/evento info)"));
                        all.spigot().sendMessage(MineDown.parse(""));
                    }
                }
            }
            ev.setTrancado(true);
            Bukkit.getScheduler().cancelTasks(plugin);
            Bukkit.spigot().broadcast(
                    MineDown.parse("&aEvento iniciado e trancado com sucesso!" +
                            " &e[[Assistir]](/camarote) &e[[Info]](/evento info)"));
        } else {
            p.spigot().sendMessage(MineDown.parse("&cNão existe nenhum evento iniciado no momento."));
        }
    }

    public void onCanceLEvent(CommandSender s) {
        Player p = (Player) s;
        if (ev.isIniciado()) {
            for (Player participantes : Bukkit.getOnlinePlayers()) {
                if (ev.getParticipantes().contains(participantes)) {
                    participantes.teleport(ev.getSaida());
                    if (!ev.isInv()) {
                        participantes.getInventory().clear();
                    }
                }
            }
            Bukkit.getScheduler().cancelTasks(plugin);
            Bukkit.spigot().broadcast(MineDown.parse(""));
            Bukkit.spigot().broadcast(MineDown.parse("&c&lEvento Cancelado!"));
            Bukkit.spigot().broadcast(MineDown.parse(""));
            Bukkit.spigot().broadcast(MineDown.parse("&cTodos os jogadores foram"));
            Bukkit.spigot().broadcast(MineDown.parse("&cremovidos do evento."));
            Bukkit.spigot().broadcast(MineDown.parse(""));
            GenericEventPattern.setDefault();
        } else {
            p.spigot().sendMessage(MineDown.parse("&cNão existe nenhum evento iniciado no momento."));
        }
    }

    public void onAwardPage(CommandSender s) {
        Player p = (Player) s;
        p.spigot().sendMessage(MineDown.parse(""));
        p.spigot().sendMessage(MineDown.parse("&b/evento premio money <valor>"));
        p.spigot().sendMessage(MineDown.parse("&b/evento premio item"));
        p.spigot().sendMessage(MineDown.parse(""));
        p.spigot().sendMessage(MineDown.parse("&cSegure o item na mão para"));
        p.spigot().sendMessage(MineDown.parse("&csetar como prêmio."));
        p.spigot().sendMessage(MineDown.parse(""));
    }

    public void onSetPage(CommandSender s) {
        Player p = (Player) s;
        p.spigot().sendMessage(MineDown.parse(""));
        p.spigot().sendMessage(MineDown.parse("&b/evento set entrada"));
        p.spigot().sendMessage(MineDown.parse("&b/evento set saida"));
        p.spigot().sendMessage(MineDown.parse("&b/evento set camarote"));
        p.spigot().sendMessage(MineDown.parse(""));
    }

    public void onTogglePage(CommandSender s) {
        Player p = (Player) s;
        p.spigot().sendMessage(MineDown.parse(""));
        p.spigot().sendMessage(MineDown.parse("&b/evento toggle team"));
        p.spigot().sendMessage(MineDown.parse("&b/evento toggle inv"));
        p.spigot().sendMessage(MineDown.parse("&b/evento toggle fly"));
        p.spigot().sendMessage(MineDown.parse("&b/evento toggle pvp"));
        p.spigot().sendMessage(MineDown.parse("&b/evento toggle teampvp"));
        p.spigot().sendMessage(MineDown.parse(""));
    }

    public void onChooseWinner(CommandSender s, String arg) {
        Player p = (Player) s;
        Player ganhador = Bukkit.getPlayerExact(arg);
        if (ev.isTrancado()) {
            if (ev.getParticipantes().contains(ganhador)) {
                for (Player participantes : Bukkit.getOnlinePlayers()) {
                    if (ev.getParticipantes().contains(participantes)) {
                        if (!ev.isInv()) {
                            participantes.getInventory().clear();
                        }
                        participantes.teleport(ev.getSaida());
                        participantes.setFlying(false);
                        participantes.setAllowFlight(false);
                    }
                }
                Bukkit.spigot().broadcast(MineDown.parse(""));
                Bukkit.spigot().broadcast(MineDown.parse("&e&lEvento finalizado!"));
                Bukkit.spigot().broadcast(MineDown.parse(""));
                Bukkit.spigot().broadcast(MineDown.parse("&bVencedor: &f" + ganhador.getName()));
                if (ev.getPremioItem().getType().equals(Material.AIR) && ev.getPremioMoney() == 0) {
                    Bukkit.spigot().broadcast(MineDown.parse("&bPrêmio: &fNenhum!"));
                }
                if (!ev.getPremioItem().getType().equals(Material.AIR) && ev.getPremioMoney() == 0) {
                    Bukkit.spigot().broadcast(MineDown.parse("&bPrêmio: &f"
                            + ev.getPremioItem().getItemMeta().getDisplayName()));
                }
                if (ev.getPremioItem().getType().equals(Material.AIR) && ev.getPremioMoney() != 0) {
                    Bukkit.spigot().broadcast(MineDown.parse("&bPrêmio: &f"
                            + ev.getPremioMoney() + " money"));
                }
                if (!ev.getPremioItem().getType().equals(Material.AIR) && ev.getPremioMoney() != 0) {
                    Bukkit.spigot().broadcast(MineDown.parse("&bPrêmios: &f"
                            + ev.getPremioMoney() + " money + " +
                            ev.getPremioItem().getItemMeta().getDisplayName()));
                }
                Bukkit.spigot().broadcast(MineDown.parse(""));

                if (!ev.getPremioItem().getType().equals(Material.AIR)) {
                    ganhador.getInventory().addItem(ev.getPremioItem());
                }
                if (ev.getPremioMoney() != 0) {
                    Main.getEcon().depositPlayer(ganhador, ev.getPremioMoney());
                }
                GenericEventPattern.setDefault();
            } else {
                p.spigot().sendMessage(MineDown.parse("&cEsse jogador não está no evento."));
            }
        } else {
            p.spigot().sendMessage(MineDown.parse("&cNão existe nenhum evento acontecendo no momento."));
        }
    }

    public void onGreenTeamWinner(CommandSender s) {
        Player p = (Player) s;
        if (ev.isTrancado()) {
            for (Player participantes : Bukkit.getOnlinePlayers()) {
                if (ev.getParticipantes().contains(participantes)) {
                    if (!ev.isInv()) {
                        participantes.getInventory().clear();
                    }
                    participantes.teleport(ev.getSaida());
                }
            }
            Bukkit.spigot().broadcast(MineDown.parse(""));
            Bukkit.spigot().broadcast(MineDown.parse("&e&lEvento finalizado!"));
            Bukkit.spigot().broadcast(MineDown.parse(""));
            Bukkit.spigot().broadcast(MineDown.parse("&bVencedor: &aTime Verde"));
            if (ev.getPremioItem().getType().equals(Material.AIR) && ev.getPremioMoney() == 0) {
                Bukkit.spigot().broadcast(MineDown.parse("&bPrêmio: &fNenhum!"));
            }
            if (!ev.getPremioItem().getType().equals(Material.AIR) && ev.getPremioMoney() == 0) {
                Bukkit.spigot().broadcast(MineDown.parse("&bPrêmio: &f"
                        + ev.getPremioItem().getItemMeta().getDisplayName()));
            }
            if (ev.getPremioItem().getType().equals(Material.AIR) && ev.getPremioMoney() != 0) {
                Bukkit.spigot().broadcast(MineDown.parse("&bPrêmio: &f"
                        + ev.getPremioMoney() + " money"));
            }
            if (!ev.getPremioItem().getType().equals(Material.AIR) && ev.getPremioMoney() != 0) {
                Bukkit.spigot().broadcast(MineDown.parse("&bPrêmios: &f"
                        + ev.getPremioMoney() + " money + " +
                        ev.getPremioItem().getItemMeta().getDisplayName()));
            }
            Bukkit.spigot().broadcast(MineDown.parse(""));

            for (Player all : Bukkit.getOnlinePlayers()) {
                all.setFlying(false);
                all.setAllowFlight(false);
                if (ev.getParticipantes().contains(all) && ev.getVerde().contains(all)) {
                    if (!ev.getPremioItem().getType().equals(Material.AIR)) {
                        all.getInventory().addItem(ev.getPremioItem());
                    }
                    if (ev.getPremioMoney() != 0) {
                        Main.getEcon().depositPlayer(all, ev.getPremioMoney());
                    }
                }
            }
            GenericEventPattern.setDefault();
        } else {
            p.spigot().sendMessage(MineDown.parse("&cNão existe nenhum evento acontecendo no momento."));
        }
    }

    public void onYellowTeamWinner(CommandSender s) {
        Player p = (Player) s;
        if (ev.isTrancado()) {
            for (Player participantes : Bukkit.getOnlinePlayers()) {
                if (ev.getParticipantes().contains(participantes)) {
                    if (!ev.isInv()) {
                        participantes.getInventory().clear();
                    }
                    participantes.teleport(ev.getSaida());
                }
            }
            Bukkit.spigot().broadcast(MineDown.parse(""));
            Bukkit.spigot().broadcast(MineDown.parse("&e&lEvento finalizado!"));
            Bukkit.spigot().broadcast(MineDown.parse(""));
            Bukkit.spigot().broadcast(MineDown.parse("&bVencedor: &eTime Amarelo"));
            if (ev.getPremioItem().getType().equals(Material.AIR) && ev.getPremioMoney() == 0) {
                Bukkit.spigot().broadcast(MineDown.parse("&bPrêmio: &fNenhum!"));
            }
            if (!ev.getPremioItem().getType().equals(Material.AIR) && ev.getPremioMoney() == 0) {
                Bukkit.spigot().broadcast(MineDown.parse("&bPrêmio: &f"
                        + ev.getPremioItem().getItemMeta().getDisplayName()));
            }
            if (ev.getPremioItem().getType().equals(Material.AIR) && ev.getPremioMoney() != 0) {
                Bukkit.spigot().broadcast(MineDown.parse("&bPrêmio: &f"
                        + ev.getPremioMoney() + " money"));
            }
            if (!ev.getPremioItem().getType().equals(Material.AIR) && ev.getPremioMoney() != 0) {
                Bukkit.spigot().broadcast(MineDown.parse("&bPrêmios: &f"
                        + ev.getPremioMoney() + " money + " +
                        ev.getPremioItem().getItemMeta().getDisplayName()));
            }
            Bukkit.spigot().broadcast(MineDown.parse(""));

            for (Player all : Bukkit.getOnlinePlayers()) {
                all.setFlying(false);
                all.setAllowFlight(false);
                if (ev.getParticipantes().contains(all) && ev.getAmarelo().contains(all)) {
                    if (!ev.getPremioItem().getType().equals(Material.AIR)) {
                        all.getInventory().addItem(ev.getPremioItem());
                    }
                    if (ev.getPremioMoney() != 0) {
                        Main.getEcon().depositPlayer(all, ev.getPremioMoney());
                    }
                }
            }
            GenericEventPattern.setDefault();
        } else {
            p.spigot().sendMessage(MineDown.parse("&cNão existe nenhum evento acontecendo no momento."));
        }
    }

    public void onKick(CommandSender s, String arg) {
        Player p = (Player) s;
        Player kicked = Bukkit.getPlayerExact(arg);
        if (ev.isIniciado()) {
            if (ev.getParticipantes().contains(kicked)) {
                if (ev.getParticipantes().contains(kicked)) {
                    if (!ev.isInv()) {
                        kicked.getInventory().clear();
                    }
                    kicked.teleport(ev.getCamarote());
                    ev.getParticipantes().remove(kicked);
                    kicked.setFlying(false);
                    kicked.setAllowFlight(false);
                    kicked.spigot().sendMessage(MineDown.parse("&cVocê foi kikado do evento."));
                    p.spigot().sendMessage(MineDown.parse("&cVocê kikou &7" + kicked + " &cdo evento."));
                }
            } else {
                p.spigot().sendMessage(MineDown.parse("&cEsse jogador não está no evento."));
            }
        } else {
            p.spigot().sendMessage(MineDown.parse("&cNão existe nenhum evento acontecendo no momento."));
        }
    }

    public void onBroadcast(CommandSender s, String[] args) {
        Player p = (Player) s;
        if (args.length < 1) {
            p.spigot().sendMessage(MineDown.parse("&cUso correto: /evento bc <mensagem>"));
            return;
        }
        if (ev.isCriado()) {
            StringBuilder builder = new StringBuilder();
            for (String arg : args) {
                builder.append(arg).append(" ");
            }
            for (Player participantes : Bukkit.getOnlinePlayers()) {
                if (ev.getParticipantes().contains(participantes)) {
                    participantes.spigot().sendMessage(
                            MineDown.parse("&d[Evento] " + builder.toString().trim()));
                }
            }
            p.spigot().sendMessage(MineDown.parse("&d[Evento] " + builder.toString().trim()));
        } else {
            p.spigot().sendMessage(MineDown.parse("&cNão existe nenhum evento criado no momento."));
        }
    }

    public void onTeleport(CommandSender s) {
        Player p = (Player) s;
        Location l = p.getLocation();
        if (ev.isIniciado()) {
            for (Player participantes : Bukkit.getOnlinePlayers()) {
                if (ev.getParticipantes().contains(participantes)) {
                    participantes.teleport(l);
                    participantes.spigot().sendMessage(
                            MineDown.parse("&aVocê foi teleportado no evento."));
                }
            }
            p.spigot().sendMessage(MineDown.parse("&aVocê teleportou os jogadores no evento"));
        } else {
            p.spigot().sendMessage(MineDown.parse("&cNão existe nenhum evento iniciado no momento."));
        }
    }

    public void onGreenTeamTeleport(CommandSender s) {
        Player p = (Player) s;
        Location l = p.getLocation();
        if (ev.isIniciado()) {
            for (Player participantes : Bukkit.getOnlinePlayers()) {
                if (ev.getParticipantes().contains(participantes)) {
                    if (ev.getVerde().contains(participantes)) {
                        participantes.teleport(l);
                        participantes.spigot().sendMessage(
                                MineDown.parse("&aVocê foi teleportado no evento."));
                    }
                }
            }
            p.spigot().sendMessage(MineDown.parse("&aVocê teleportou os jogadores no evento"));
        } else {
            p.spigot().sendMessage(MineDown.parse("&cNão existe nenhum evento iniciado no momento."));
        }
    }

    public void onYellowTeamTeleport(CommandSender s) {
        Player p = (Player) s;
        Location l = p.getLocation();
        if (ev.isIniciado()) {
            for (Player participantes : Bukkit.getOnlinePlayers()) {
                if (ev.getParticipantes().contains(participantes)) {
                    if (ev.getAmarelo().contains(participantes)) {
                        participantes.teleport(l);
                        participantes.spigot().sendMessage(
                                MineDown.parse("&aVocê foi teleportado no evento."));
                    }
                }
            }
            p.spigot().sendMessage(MineDown.parse("&aVocê teleportou os jogadores no evento"));
        } else {
            p.spigot().sendMessage(MineDown.parse("&cNão existe nenhum evento iniciado no momento."));
        }
    }

    public void onGiveItem(CommandSender s) {
        Player p = (Player) s;
        if (ev.isIniciado()) {
            if (!ev.isInv()) {
                for (Player participantes : Bukkit.getOnlinePlayers()) {
                    if (ev.getParticipantes().contains(participantes)) {
                        if (!p.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                            participantes.getInventory().addItem(p.getInventory().getItemInMainHand());
                            participantes.spigot().sendMessage(
                                    MineDown.parse("&aVocê recebeu um item do evento: &f"
                                            + p.getInventory().getItemInMainHand()
                                            .getItemMeta().getDisplayName()));
                        } else {
                            p.spigot().sendMessage(
                                    MineDown.parse("&cColoque um item na sua mão."));
                        }
                    }
                }
                p.spigot().sendMessage(
                        MineDown.parse("&aVocê deu um item para os jogadores do evento: &f"
                                + p.getInventory().getItemInMainHand()
                                .getItemMeta().getDisplayName()));
            } else {
                p.spigot().sendMessage(
                        MineDown.parse("&cVocê não pode dar itens em um evento com itens próprios."));
            }
        } else {
            p.spigot().sendMessage(MineDown.parse("&cNão existe nenhum evento iniciado no momento."));
        }
    }

    public void giveItemToGreenTeam(CommandSender s) {
        Player p = (Player) s;
        if (ev.isIniciado()) {
            if (!ev.isInv()) {
                for (Player participantes : Bukkit.getOnlinePlayers()) {
                    if (ev.getParticipantes().contains(participantes)) {
                        if (ev.getVerde().contains(participantes)) {
                            if (!p.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                                participantes.getInventory().addItem(p.getInventory().getItemInMainHand());
                                participantes.spigot().sendMessage(
                                        MineDown.parse("&aVocê recebeu um item do evento: &f"
                                                + p.getInventory().getItemInMainHand()
                                                .getItemMeta().getDisplayName()));
                            } else {
                                p.spigot().sendMessage(
                                        MineDown.parse("&cColoque um item na sua mão."));
                            }
                        }
                    }
                }
                p.spigot().sendMessage(
                        MineDown.parse("&aVocê deu um item para os jogadores do evento: &f"
                                + p.getInventory().getItemInMainHand().getItemMeta().getDisplayName()));
            } else {
                p.spigot().sendMessage(
                        MineDown.parse("&cColoque um item na sua mão."));
            }
        } else {
            p.spigot().sendMessage(MineDown.parse("&cNão existe nenhum evento iniciado no momento."));
        }
    }

    public void giveItemToYellowTeam(CommandSender s) {
        Player p = (Player) s;
        if (ev.isIniciado()) {
            if (!ev.isInv()) {
                for (Player participantes : Bukkit.getOnlinePlayers()) {
                    if (ev.getParticipantes().contains(participantes)) {
                        if (ev.getAmarelo().contains(participantes)) {
                            if (!p.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                                participantes.getInventory().addItem(p.getInventory().getItemInMainHand());
                                participantes.spigot().sendMessage(
                                        MineDown.parse("&aVocê recebeu um item do evento: &f"
                                                + p.getInventory().getItemInMainHand()
                                                .getItemMeta().getDisplayName()));
                            } else {
                                p.spigot().sendMessage(
                                        MineDown.parse("&cColoque um item na sua mão."));
                            }
                        }
                    }
                }
                p.spigot().sendMessage(
                        MineDown.parse("&aVocê deu um item para os jogadores do evento: &f"
                                + p.getInventory().getItemInMainHand().getItemMeta().getDisplayName()));
            } else {
                p.spigot().sendMessage(
                        MineDown.parse("&cVocê não pode dar itens em um evento com itens próprios."));
            }
        } else {
            p.spigot().sendMessage(MineDown.parse("&cNão existe nenhum evento iniciado no momento."));
        }
    }

    public void setMoneyAward(CommandSender s, int value) {
        Player p = (Player) s;
        if (ev.isCriado()) {
            if (!ev.isIniciado()) {
                try {
                    if (ev.getPremioMoney() < 0.0) {
                        p.sendMessage("§cO valor deve ser maior que 0!");
                        return;
                    }
                    ev.setPremioMoney(value);
                    p.spigot().sendMessage(MineDown.parse("&aVocê adicionou "
                            + ev.getPremioMoney() + " money como prêmio."));
                } catch (NumberFormatException e) {
                    p.spigot().sendMessage(MineDown.parse("&cUso correto: /evento premio <valor>"));
                }
            } else {
                p.spigot().sendMessage(MineDown.parse("&cO evento já iniciou."));
            }
        } else {
            p.spigot().sendMessage(MineDown.parse("&cNão existe nenhum evento criado no momento."));
        }
    }

    public void setItemAward(CommandSender s) {
        Player p = (Player) s;
        if (ev.isCriado()) {
            if (!ev.isIniciado()) {
                if (!p.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                    ev.setPremioItem(p.getInventory().getItemInMainHand());
                    p.spigot().sendMessage(MineDown.parse("&aVocê adicionou um item como prêmio."));
                } else {
                    p.spigot().sendMessage(MineDown.parse("&cSegure um item na sua mão."));
                }
            } else {
                p.spigot().sendMessage(MineDown.parse("&cO evento já iniciou."));
            }
        } else {
            p.spigot().sendMessage(MineDown.parse("&cNão existe nenhum evento criado no momento."));
        }
    }

    public void onSetEnterLoc(CommandSender s) {
        Player p = (Player) s;
        if (!ev.isCriado()) {
            p.spigot().sendMessage(MineDown.parse("&cNão existe nenhum evento criado no momento."));
            return;
        }
        if (ev.isIniciado()) {
            p.spigot().sendMessage(MineDown.parse("&cO evento já se iniciou!"));
            return;
        }
        Location l = p.getLocation();
        ev.setEntrada(l);
        p.spigot().sendMessage(MineDown.parse("&aEntrada do evento definida com sucesso."));
    }

    public void onSetExitLoc(CommandSender s) {
        Player p = (Player) s;
        if (!ev.isCriado()) {
            p.spigot().sendMessage(MineDown.parse("&cNão existe nenhum evento criado no momento."));
            return;
        }
        if (ev.isIniciado()) {
            p.spigot().sendMessage(MineDown.parse("&cO evento já se iniciou!"));
            return;
        }
        Location l = p.getLocation();
        ev.setSaida(l);
        p.spigot().sendMessage(MineDown.parse("&aSaída do evento definida com sucesso."));
    }

    public void onSetCabinLoc(CommandSender s) {
        Player p = (Player) s;
        if (!ev.isCriado()) {
            p.spigot().sendMessage(MineDown.parse("&cNão existe nenhum evento criado no momento."));
            return;
        }
        if (ev.isIniciado()) {
            p.spigot().sendMessage(MineDown.parse("&cO evento já se iniciou!"));
            return;
        }
        Location l = p.getLocation();
        ev.setCamarote(l);
        p.spigot().sendMessage(MineDown.parse("&aCamarote do evento definido com sucesso."));
    }

    public void onEventInfo(CommandSender s) {
        Player p = (Player) s;
        if (ev.isIniciado()) {
            p.spigot().sendMessage(MineDown.parse(" "));
            p.spigot().sendMessage(MineDown.parse("&bNome do Evento: &f" + ev.getEventName()));
            if (!ev.isInv()) {
                p.spigot().sendMessage(MineDown.parse("&bItens próprios: &fNão"));
            } else {
                p.spigot().sendMessage(MineDown.parse("&bItens próprios: &fSim"));
            }
            if (ev.isPvP()) {
                p.spigot().sendMessage(MineDown.parse("&bPvP no evento: &fSim"));
            } else {
                p.spigot().sendMessage(MineDown.parse("&bPvP no evento: &fNão"));
            }
            if (ev.isFly()) {
                p.spigot().sendMessage(MineDown.parse("&bFly no evento: &fSim"));
            } else {
                p.spigot().sendMessage(MineDown.parse("&bFly no evento: &fNão"));
            }
            if (ev.getPremioItem().getType().equals(Material.AIR) && ev.getPremioMoney() == 0) {
                p.spigot().sendMessage(MineDown.parse("&bPrêmio: &fNenhum!"));
            }
            if (!ev.getPremioItem().getType().equals(Material.AIR) && ev.getPremioMoney() == 0) {
                p.spigot().sendMessage(MineDown.parse("&bPrêmio: &f"
                        + ev.getPremioItem().getItemMeta().getDisplayName()));
            }
            if (ev.getPremioItem().getType().equals(Material.AIR) && ev.getPremioMoney() != 0) {
                p.spigot().sendMessage(MineDown.parse("&bPrêmio: &f"
                        + ev.getPremioMoney() + " money"));
            }
            if (!ev.getPremioItem().getType().equals(Material.AIR) && ev.getPremioMoney() != 0) {
                p.spigot().sendMessage(MineDown.parse("&bPrêmios: &f"
                        + ev.getPremioMoney() + " money + " +
                        ev.getPremioItem().getItemMeta().getDisplayName()));
            }
            if (ev.isTeam()) {
                p.spigot().sendMessage(MineDown.parse("&aTime Verde"));
                ev.getVerde().forEach(pv ->
                        p.spigot().sendMessage(MineDown.parse("&f" + pv.getName())));
                p.spigot().sendMessage(MineDown.parse("&eTime Amarelo"));
                ev.getAmarelo().forEach(pv ->
                        p.spigot().sendMessage(MineDown.parse("&f" + pv.getName())));
            } else {
                p.spigot().sendMessage(MineDown.parse("&bParticipantes:"));
                ev.getParticipantes().forEach(pv ->
                        p.spigot().sendMessage(MineDown.parse("&f" + pv.getName())));
            }
            p.spigot().sendMessage(MineDown.parse(" "));
        } else {
            p.spigot().sendMessage(MineDown.parse("&cNão existe nenhum evento criado no momento."));
        }
    }

    public void onToggleInv(CommandSender s) {
        Player p = (Player) s;
        if (ev.isCriado()) {
            if (!ev.isIniciado()) {
                if (!ev.isInv()) {
                    ev.setInv(true);
                    p.spigot().sendMessage(
                            MineDown.parse("&cOs jogadores poderão entrar com itens próprios."));
                } else {
                    ev.setInv(false);
                    p.spigot().sendMessage(
                            MineDown.parse("&cOs jogadores não poderão entrar com itens próprios."));
                }
            } else {
                p.spigot().sendMessage(MineDown.parse("&cO evento já se iniciou!"));
            }
        } else {
            p.spigot().sendMessage(MineDown.parse("&cNão existe nenhum evento criado no momento."));
        }
    }

    public void onTeamEvent(CommandSender s) {
        Player p = (Player) s;
        if (ev.isCriado()) {
            if (!ev.isIniciado()) {
                if (!ev.isTeam()) {
                    ev.setTeam(true);
                    p.spigot().sendMessage(
                            MineDown.parse("&cVocê selecionou o evento como tipo TIME vs TIME"));
                } else {
                    ev.setTeam(false);
                    p.spigot().sendMessage(
                            MineDown.parse("&cVocê selecionou o evento como tipo individual"));
                }
            } else {
                p.spigot().sendMessage(MineDown.parse("&cO evento já se iniciou!"));
            }
        } else {
            p.spigot().sendMessage(MineDown.parse("&cNão existe nenhum evento criado no momento."));
        }
    }

    public void onTogglePvP(CommandSender s) {
        Player p = (Player) s;
        if (ev.isCriado()) {
            if (!ev.isPvP()) {
                ev.setPvP(true);
                p.spigot().sendMessage(MineDown.parse("&cO PvP foi ativado no evento."));
            } else {
                ev.setPvP(false);
                p.spigot().sendMessage(MineDown.parse("&cO PvP foi desativado no evento."));
            }
        } else {
            p.spigot().sendMessage(MineDown.parse("&cNão existe nenhum evento criado no momento."));
        }
    }

    public void onTogglePvPinTeam(CommandSender s) {
        Player p = (Player) s;
        if (ev.isCriado()) {
            if (!ev.isTeamPvP()) {
                ev.setTeamPvP(true);
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (ev.getParticipantes().contains(all)) {
                        all.spigot().sendMessage(
                                MineDown.parse("&cO PvP no time foi ativado no evento."));
                    }
                }
                p.spigot().sendMessage(MineDown.parse("&cO PvP no time foi ativado no evento."));
            } else {
                ev.setTeamPvP(false);
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (ev.getParticipantes().contains(all)) {
                        all.spigot().sendMessage(
                                MineDown.parse("&cO PvP no time foi desativado no evento."));
                    }
                }
                p.spigot().sendMessage(MineDown.parse("&cO PvP no time foi desativado no evento."));
            }
        } else {
            p.spigot().sendMessage(MineDown.parse("&cNão existe nenhum evento criado no momento."));
        }
    }

    public void onToggleFly(CommandSender s) {
        Player p = (Player) s;
        if (ev.isCriado()) {
            if (!ev.isFly()) {
                ev.setFly(true);
                p.spigot().sendMessage(MineDown.parse("&cO Fly foi ativado no evento."));
                ev.getParticipantes().forEach(pv -> {
                    pv.setAllowFlight(true);
                    pv.setFlying(true);
                    pv.spigot().sendMessage(MineDown.parse("&cO Fly foi ativado no evento."));
                });
            } else {
                ev.setFly(false);
                p.spigot().sendMessage(MineDown.parse("&cO Fly foi desativado no evento."));
                ev.getParticipantes().forEach(pv -> {
                    pv.setAllowFlight(true);
                    pv.setFlying(true);
                    pv.spigot().sendMessage(MineDown.parse("&cO Fly foi desativado no evento."));
                });
            }
        } else {
            p.spigot().sendMessage(MineDown.parse("&cNão existe nenhum evento criado no momento."));
        }
    }

    public void onCabin(CommandSender s) {
        Player p = (Player) s;
        if (ev.isIniciado()) {
            p.teleport(ev.getCamarote());
            p.spigot().sendMessage(MineDown.parse("&aVocê foi teleportado para o camarote."));
        } else {
            p.spigot().sendMessage(MineDown.parse("&cNão existe nenhum evento ocorrendo no momento."));
        }
    }

    public void onEnter(CommandSender s) {
        Player p = (Player) s;
        if (ev.getParticipantes().contains(p)) {
            p.spigot().sendMessage(MineDown.parse("&cVocê já está no evento."));
            return;
        }
        if (ev.isIniciado()) {
            if (!ev.isTrancado()) {
                if (!ev.isInv()) {
                    if (!p.getInventory().isEmpty()) {
                        p.spigot().sendMessage(
                                MineDown.parse("&cLimpe o seu inventário para entrar nesse evento."));
                    } else {
                        ev.getParticipantes().add(p);
                        p.teleport(ev.getEntrada());
                        p.spigot().sendMessage(MineDown.parse("&aVocê foi teleportado para o evento."));
                        p.setFlying(false);
                        p.setAllowFlight(false);
                    }
                } else {
                    ev.getParticipantes().add(p);
                    p.teleport(ev.getEntrada());
                    p.spigot().sendMessage(MineDown.parse("&aVocê foi teleportado para o evento."));
                    p.setFlying(false);
                    p.setAllowFlight(false);
                }
            } else {
                p.spigot().sendMessage(MineDown.parse("&cO evento já iniciou."));
            }
        } else {
            p.spigot().sendMessage(MineDown.parse("&cNão existe nenhum evento ocorrendo no momento."));
        }
    }

    public void onExit(CommandSender s) {
        Player p = (Player) s;
        if (!ev.getParticipantes().contains(p)) {
            p.spigot().sendMessage(MineDown.parse("&cVocê não está no evento."));
            return;
        }
        if (ev.isIniciado()) {
            if (!ev.isInv()) {
                p.getInventory().clear();
                p.teleport(ev.getSaida());
                p.spigot().sendMessage(MineDown.parse("&cVocê saiu do evento."));
                ev.getParticipantes().remove(p);
                p.setFlying(false);
                p.setAllowFlight(false);
            }
            if (ev.isInv()) {
                p.teleport(ev.getSaida());
                p.spigot().sendMessage(MineDown.parse("&cVocê saiu do evento."));
                ev.getParticipantes().remove(p);
                p.setFlying(false);
                p.setAllowFlight(false);
            }
        } else {
            p.spigot().sendMessage(MineDown.parse("&cNão existe nenhum evento ocorrendo no momento."));
        }
    }
}