package com.github.pk7r.simplexevents.utils;

import com.github.pk7r.simplexevents.Main;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GenericEventPattern {

    public static void setDefault() {
        Main.getGenericEventModel().setEntrada(null);
        Main.getGenericEventModel().setSaida(null);
        Main.getGenericEventModel().setInv(false);
        Main.getGenericEventModel().setPvP(false);
        Main.getGenericEventModel().setFly(false);
        Main.getGenericEventModel().setTeam(false);
        Main.getGenericEventModel().setTeamPvP(false);
        Main.getGenericEventModel().setCamarote(null);
        Main.getGenericEventModel().setPremioMoney(0);
        Main.getGenericEventModel().setPremioItem(new ItemStack(Material.AIR));
        Main.getGenericEventModel().setTrancado(true);
        Main.getGenericEventModel().setIniciado(false);
        Main.getGenericEventModel().setCriado(false);
        Main.getGenericEventModel().getParticipantes().clear();
        Main.getGenericEventModel().getAmarelo().clear();
        Main.getGenericEventModel().getVerde().clear();
        Main.getGenericEventModel().setEventName("");
    }
}