package com.github.pk7r.simplexevents.utils;

import com.github.pk7r.simplexevents.Main;
import com.github.pk7r.simplexevents.model.QuizEventModel;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class QuizEventPattern {

    private static final QuizEventModel ev = Main.getQuizEventModel();

    public static void setDefault() {
        ev.setAberto(false);
        ev.setResposta("");
        ev.setPergunta("");
        ev.setPremioItem(new ItemStack(Material.AIR));
        ev.setPremioMoney(0);
    }
}