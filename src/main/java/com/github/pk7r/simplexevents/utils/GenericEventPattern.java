package com.github.pk7r.simplexevents.utils;

import com.github.pk7r.simplexevents.Main;
import com.github.pk7r.simplexevents.model.GenericEventModel;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GenericEventPattern {

    private static final GenericEventModel ev = Main.getGenericEventModel();

    public static void setDefault() {
        ev.setEntrada(null);
        ev.setSaida(null);
        ev.setInv(false);
        ev.setPvP(false);
        ev.setFly(false);
        ev.setTeam(false);
        ev.setTeamPvP(false);
        ev.setCamarote(null);
        ev.setPremioMoney(0);
        ev.setPremioItem(new ItemStack(Material.AIR));
        ev.setTrancado(true);
        ev.setIniciado(false);
        ev.setCriado(false);
        ev.getParticipantes().clear();
        ev.getAmarelo().clear();
        ev.getVerde().clear();
        ev.setEventName("");
    }
}