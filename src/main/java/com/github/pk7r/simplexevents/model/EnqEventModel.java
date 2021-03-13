package com.github.pk7r.simplexevents.model;

import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

@Data
public class EnqEventModel {

    private String enquete = "";
    private List<Player> no = new ArrayList<>();
    private List<Player> yes = new ArrayList<>();
    private boolean aberto = false;
    private BukkitTask task = null;

}
