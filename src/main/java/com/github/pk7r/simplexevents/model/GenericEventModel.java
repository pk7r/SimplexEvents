package com.github.pk7r.simplexevents.model;

import lombok.Data;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Data
public class GenericEventModel {

    public String eventName = "";
    public double premioMoney = 0.0;
    public ItemStack premioItem = new ItemStack(Material.AIR);
    public List<Player> participantes = new ArrayList<>();
    public List<Player> verde = new ArrayList<>();
    public List<Player> amarelo = new ArrayList<>();
    public Location entrada = null;
    public Location saida = null;
    public Location camarote = null;
    public boolean criado = false;
    public boolean iniciado = false;
    public boolean trancado = true;
    public boolean inv = false;
    public boolean pvP = false;
    public boolean teamPvP = false;
    public boolean fly = false;
    public boolean team = false;

}