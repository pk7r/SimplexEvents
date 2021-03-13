package com.github.pk7r.simplexevents.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.github.pk7r.simplexevents.Main;
import com.github.pk7r.simplexevents.events.GenericEvent;
import com.github.pk7r.simplexevents.model.GenericEventModel;
import de.themoep.minedown.MineDown;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("evento|ev")
public class GenericEventCommand extends BaseCommand {

    private final GenericEvent e = Main.getGenericEvent();
    private final GenericEventModel em = Main.getGenericEventModel();

    @Default @CatchUnknown
    public void defaultCommand(CommandSender s) {
        Player p = (Player) s;
        if (p.hasPermission("simplexevents.evento.admin")) {
            p.spigot().sendMessage(MineDown.parse(""));
            p.spigot().sendMessage(MineDown.parse("&eSimplexEvents - Evento"));
            p.spigot().sendMessage(MineDown.parse(""));
            p.spigot().sendMessage(MineDown.parse("&b/evento criar <nome>"));
            p.spigot().sendMessage(MineDown.parse("&b/evento iniciar"));
            p.spigot().sendMessage(MineDown.parse("&b/evento cancelar"));
            p.spigot().sendMessage(MineDown.parse("&b/evento trancar"));
            p.spigot().sendMessage(MineDown.parse("&b/evento kick <player>"));
            p.spigot().sendMessage(MineDown.parse(""));
            p.spigot().sendMessage(MineDown.parse("&a[[Próxima Página]](/evento 2)"));
        } else {
            p.spigot().sendMessage(MineDown.parse(""));
            p.spigot().sendMessage(MineDown.parse("&eSimplexEvents - Evento"));
            p.spigot().sendMessage(MineDown.parse(""));
            p.spigot().sendMessage(MineDown.parse("&b/evento entrar"));
            p.spigot().sendMessage(MineDown.parse("&b/evento sair"));
            p.spigot().sendMessage(MineDown.parse("&b/evento camarote"));
            p.spigot().sendMessage(MineDown.parse(""));
        }
    }

    @Subcommand("2")
    public void secondPage(CommandSender s) {
        Player p = (Player) s;
        if (p.hasPermission("simplexevents.evento.admin")) {
            p.spigot().sendMessage(MineDown.parse(""));
            p.spigot().sendMessage(MineDown.parse("&eSimplexEvents - Evento"));
            p.spigot().sendMessage(MineDown.parse(""));
            p.spigot().sendMessage(MineDown.parse("&b/evento set &f| &b/evento give"));
            p.spigot().sendMessage(MineDown.parse("&b/evento toggle &f| &b/evento tp"));
            p.spigot().sendMessage(MineDown.parse("&b/evento premio"));
            p.spigot().sendMessage(MineDown.parse("&b/evento bc <mensagem>"));
            p.spigot().sendMessage(MineDown.parse("&b/evento ganhador (<jogador>/<time>)"));
            p.spigot().sendMessage(MineDown.parse(""));
            p.spigot().sendMessage(MineDown.parse("&c[[Página Anterior]](/evento)"));
        } else {
            p.spigot().sendMessage(MineDown.parse("&cSem permissão."));
        }
    }

    @Subcommand("criar")
    public void createCommand(CommandSender s, String[] args) {
        try {
            if (s.hasPermission("simplexevents.evento.admin")) {
                e.onCreateEvent(s, args);
            } else {
                s.sendMessage("§cSem permissão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("iniciar")
    public void startCommand(CommandSender s) {
        try {
            if (s.hasPermission("simplexevents.evento.admin")) {
                e.onStartEvent(s);
            } else {
                s.sendMessage("§cSem permissão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("info")
    public void infoCommand(CommandSender s) {
        try {
            e.onEventInfo(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("trancar")
    public void lockCommand(CommandSender s) {
        try {
            if (s.hasPermission("simplexevents.evento.admin")) {
                e.onLockEvent(s);
            } else {
                s.sendMessage("§cSem permissão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("cancelar")
    public void cancelCommand(CommandSender s) {
        try {
            if (s.hasPermission("simplexevents.evento.admin")) {
                e.onCanceLEvent(s);
            } else {
                s.sendMessage("§cSem permissão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("premio")
    public void awardCommand(CommandSender s) {
        try {
            if (s.hasPermission("simplexevents.evento.admin")) {
                e.onAwardPage(s);
            } else {
                s.sendMessage("§cSem permissão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("set")
    public void setCommand(CommandSender s) {
        try {
            if (s.hasPermission("simplexevents.evento.admin")) {
                e.onSetPage(s);
            } else {
                s.sendMessage("§cSem permissão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("toggle")
    public void toggleCommand(CommandSender s) {
        try {
            if (s.hasPermission("simplexevents.evento.admin")) {
                e.onTogglePage(s);
            } else {
                s.sendMessage("§cSem permissão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @CommandCompletion("@players")
    @Subcommand("ganhador")
    public void winnerCommand(CommandSender s, String arg) {
        try {
            if (s.hasPermission("simplexevents.evento.admin")) {
                e.onChooseWinner(s, arg);
            } else {
                s.sendMessage("§cSem permissão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("ganhador verde")
    public void winnerGreenTeamCommand(CommandSender s) {
        try {
            if (s.hasPermission("simplexevents.evento.admin")) {
                if (em.isTeam()) {
                    e.onGreenTeamWinner(s);
                } else {
                    s.sendMessage("§cVocê só pode usar esse comando em um evento entre times.");
                }
            } else {
                s.sendMessage("§cSem permissão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("ganhador amarelo")
    public void winnerYellowTeamCommand(CommandSender s) {
        try {
            if (s.hasPermission("simplexevents.evento.admin")) {
                if (em.isTeam()) {
                    e.onYellowTeamWinner(s);
                } else {
                    s.sendMessage("§cVocê só pode usar esse comando em um evento entre times.");
                }
            } else {
                s.sendMessage("§cSem permissão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @CommandCompletion("@players")
    @Subcommand("kick")
    public void kickCommand(CommandSender s, String arg) {
        try {
            if (s.hasPermission("simplexevents.evento.admin") || s.hasPermission("simplexevents.kick")) {
                e.onKick(s, arg);
            } else {
                s.sendMessage("§cSem permissão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("bc")
    public void bcCommand(CommandSender s, String[] args) {
        try {
            if (s.hasPermission("simplexevents.evento.admin")) {
                e.onBroadcast(s, args);
            } else {
                s.sendMessage("§cSem permissão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("tp")
    public void tpCommand(CommandSender s) {
        try {
            if (s.hasPermission("simplexevents.evento.admin")) {
                e.onTeleport(s);
            } else {
                s.sendMessage("§cSem permissão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("tp verde")
    public void tpGreenTeamCommand(CommandSender s) {
        try {
            if (s.hasPermission("simplexevents.evento.admin")) {
                if (em.isTeam()) {
                    e.onGreenTeamTeleport(s);
                } else {
                    s.sendMessage("§cVocê só pode usar esse comando em um evento entre times.");
                }
            } else {
                s.sendMessage("§cSem permissão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("tp amarelo")
    public void tpYellowTeamCommand(CommandSender s) {
        try {
            if (s.hasPermission("simplexevents.evento.admin")) {
                if (em.isTeam()) {
                    e.onYellowTeamTeleport(s);
                } else {
                    s.sendMessage("§cVocê só pode usar esse comando em um evento entre times.");
                }
            } else {
                s.sendMessage("§cSem permissão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("give")
    public void giveCommand(CommandSender s) {
        try {
            if (s.hasPermission("simplexevents.evento.admin")) {
                e.onGiveItem(s);
            } else {
                s.sendMessage("§cSem permissão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("give verde")
    public void giveToGreenTeamCommand(CommandSender s) {
        try {
            if (s.hasPermission("simplexevents.evento.admin")) {
                if (em.isTeam()) {
                    e.giveItemToGreenTeam(s);
                } else {
                    s.sendMessage("§cVocê só pode usar esse comando em um evento entre times.");
                }
            } else {
                s.sendMessage("§cSem permissão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("give amarelo")
    public void giveToYellowTeamCommand(CommandSender s) {
        try {
            if (s.hasPermission("simplexevents.evento.admin")) {
                if (em.isTeam()) {
                    e.giveItemToYellowTeam(s);
                } else {
                    s.sendMessage("§cVocê só pode usar esse comando em um evento entre times.");
                }
            } else {
                s.sendMessage("§cSem permissão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("premio money")
    public void moneyAwardCommand(CommandSender s, int valor) {
        try {
            if (s.hasPermission("simplexevents.evento.admin")) {
                e.setMoneyAward(s, valor);
            } else {
                s.sendMessage("§cSem permissão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("premio item")
    public void itemAwardCommand(CommandSender s) {
        try {
            if (s.hasPermission("simplexevents.evento.admin")) {
                e.setItemAward(s);
            } else {
                s.sendMessage("§cSem permissão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("set entrada")
    public void enterLocCommand(CommandSender s) {
        try {
            if (s.hasPermission("simplexevents.evento.admin")) {
                e.onSetEnterLoc(s);
            } else {
                s.sendMessage("§cSem permissão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("set saida")
    public void exitLocCommand(CommandSender s) {
        try {
            if (s.hasPermission("simplexevents.evento.admin")) {
                e.onSetExitLoc(s);
            } else {
                s.sendMessage("§cSem permissão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("set camarote")
    public void cabinLocCommand(CommandSender s) {
        try {
            if (s.hasPermission("simplexevents.evento.admin")) {
                e.onSetCabinLoc(s);
            } else {
                s.sendMessage("§cSem permissão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("toggle inv")
    public void toggleInvCommand(CommandSender s) {
        try {
            if (s.hasPermission("simplexevents.evento.admin")) {
                e.onToggleInv(s);
            } else {
                s.sendMessage("§cSem permissão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("toggle team")
    public void toggleTeamCommand(CommandSender s) {
        try {
            if (s.hasPermission("simplexevents.evento.admin")) {
                e.onTeamEvent(s);
            } else {
                s.sendMessage("§cSem permissão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("toggle teampvp")
    public void toggleTeamPvPCommand(CommandSender s) {
        try {
            if (s.hasPermission("simplexevents.evento.admin")) {
                if (em.isTeam()) {
                    e.onTogglePvPinTeam(s);
                } else {
                    s.sendMessage("§cVocê só pode usar esse comando em um evento entre times.");
                }
            } else {
                s.sendMessage("§cSem permissão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("toggle pvp")
    public void togglePvPCommand(CommandSender s) {
        try {
            if (s.hasPermission("simplexevents.evento.admin")) {
                e.onTogglePvP(s);
            } else {
                s.sendMessage("§cSem permissão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("toggle fly")
    public void toggleFlyCommand(CommandSender s) {
        try {
            if (s.hasPermission("simplexevents.evento.admin")) {
                e.onToggleFly(s);
            } else {
                s.sendMessage("§cSem permissão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("entrar")
    public void enterCommand(CommandSender s) {
        try {
            e.onEnter(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("sair")
    public void exitCommand(CommandSender s) {
        try {
            e.onExit(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subcommand("camarote")
    public void cabinCommand(CommandSender s) {
        try {
            if (s.hasPermission("simplexevents.evento.camarote")) {
                e.onCabin(s);
            } else {
                s.sendMessage("§cSem permissão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}