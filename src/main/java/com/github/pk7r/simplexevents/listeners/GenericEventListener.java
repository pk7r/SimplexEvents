package com.github.pk7r.simplexevents.listeners;

import com.github.pk7r.simplexevents.Main;
import com.github.pk7r.simplexevents.model.GenericEventModel;
import de.themoep.minedown.MineDown;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class GenericEventListener implements Listener {

    private final GenericEventModel ev = Main.getGenericEventModel();

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (ev.getParticipantes().contains(e.getEntity())) {
            if (!ev.isInv()) {
                e.getEntity().getInventory().clear();
            }
            e.getEntity().spigot().sendMessage(MineDown.parse("&cVocê foi desclassificado do evento."));
            e.setKeepLevel(true);
            ev.getParticipantes().remove(e.getEntity());
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        if (ev.getParticipantes().contains(p)) {
            if (p.hasPermission("simplexevents.admin")) {
                e.setCancelled(false);
                return;
            }
            if (e.getMessage().startsWith("/c") || e.getMessage().startsWith("/g")) {
                e.setCancelled(false);
                return;
            }
            p.spigot().sendMessage(MineDown.parse("&cVocê não pode usar comandos no evento!"));
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onCombat(EntityDamageByEntityEvent e) {
        if ((e.getEntity() instanceof Player) && (e.getDamager() instanceof Player)) {
            if (ev.getParticipantes().contains(e.getEntity()) && ev.getParticipantes().contains(e.getDamager())) {
                if (!ev.isPvP()) {
                    e.setCancelled(true);
                    e.getDamager().spigot().sendMessage(MineDown.parse("&cO PvP está desativado no evento!"));
                    return;
                }
                if (ev.isTeam()) {
                    if (!ev.isTeamPvP()) {
                        if (ev.getVerde().contains(e.getEntity()) && ev.getVerde().contains(e.getDamager())) {
                            e.setCancelled(true);
                            e.getDamager().spigot().sendMessage(MineDown.parse("&cJogadores do mesmo time não podem se bater!"));
                        }
                        if (ev.getAmarelo().contains(e.getEntity()) && ev.getAmarelo().contains(e.getDamager())) {
                            e.setCancelled(true);
                            e.getDamager().spigot().sendMessage(MineDown.parse("&cJogadores do mesmo time não podem se bater!"));
                        }
                    }
                }
            }
        }
    }
}
