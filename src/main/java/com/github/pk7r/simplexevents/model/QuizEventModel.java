package com.github.pk7r.simplexevents.model;

import lombok.Data;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

@Data
public class QuizEventModel {

    public String pergunta = "";
    public String resposta = "";
    public boolean aberto = false;
    public double premioMoney = 0.0;
    public ItemStack premioItem = new ItemStack(Material.AIR);
    private BukkitTask task = null;

}